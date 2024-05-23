package dat.controller;

import dat.dao.BuyRequestDAO;
import dat.dao.DAO;
import dat.dao.ShiftDAO;
import dat.dao.UserDAO;
import dat.dto.BuyRequestDTO;
import dat.exception.ApiException;
import dat.model.BuyRequest;
import dat.model.Shift;
import dat.model.Status;
import dat.model.User;
import dat.util.EmailSender;
import io.javalin.http.Context;

import java.util.List;

public class BuyRequestController extends Controller<BuyRequest, BuyRequestDTO>
{
    private UserDAO userDAO;
    private ShiftDAO shiftDAO;

    public BuyRequestController(DAO<BuyRequest> dao) {
        super(dao);
        this.userDAO = UserDAO.getInstance();
        this.shiftDAO = ShiftDAO.getInstance();
    }

    public void createBuyRequest(Context ctx) throws ApiException {

        BuyRequestDTO buyRequestDTO = ctx.bodyAsClass(BuyRequestDTO.class);
        User user = userDAO.readById(buyRequestDTO.getUserId()).orElse(null);
        if (user == null) {
            throw new ApiException(404, "User with id " + buyRequestDTO.getUserId() + " not found");
        }
        Shift shift = shiftDAO.readById(buyRequestDTO.getShiftId()).orElse(null);
        if (shift == null) {
            throw new ApiException(404, "Shift with id " + buyRequestDTO.getShiftId() + " not found");
        }

        if (shift.getStatus() != Status.FOR_SALE) {
            throw new ApiException(400, "Buy request wasn't created. Shift is not for sale!");
        }

        BuyRequest buyRequest = new BuyRequest(user, shift);

        BuyRequestDAO bdao = (BuyRequestDAO) this.dao;
        if (bdao.exists(buyRequest)) {
            throw new ApiException(400, "Buy request already exists");
        }

        dao.create(buyRequest);

        List<User> managers = user.getDepartment().getUsers()
                .stream()
                .filter(u -> u.getRole().getName().equals("manager"))
                .toList();

        if (!managers.isEmpty()) {
            for (User manager : managers) {
                EmailSender.sendEmail(manager.getEmail(), "Buy request",
                        List.of("User " + user.getEmail() +
                                " has requested to buy shift " + shift.getId()
                                + ".\nPlease approve or deny the request in the system."), false);
            }
        }

        ctx.status(201);
        ctx.json(buyRequest.toDTO());
    }

    public void getBuyRequests(Context context)
    {
        int shiftId = Integer.parseInt(context.pathParam("id"));
        List<BuyRequest> buyRequests = shiftDAO.getBuyRequestsByShiftId(shiftId);
        context.status(200);
        context.json(createFromEntities(buyRequests));
    }

    // To be used by manger to accept a buy request
    public void acceptBuyRequest(Context context) throws ApiException
    {
        int buyRequestId = Integer.parseInt(context.pathParam("reqId"));
        BuyRequest buyRequest = dao.readById(buyRequestId).orElse(null);
        if (buyRequest == null) {
            throw new ApiException(404, "Buy request with id " + buyRequestId + " not found");
        }

        Shift shift = buyRequest.getShift();

        if (shift == null) {
            throw new ApiException(404, "Shift associated with this buyRequest not found");
        }
        if (shift.getStatus() != Status.FOR_SALE) {
            throw new ApiException(400, "Shift is not for sale!");
        }

        User reqUser = buyRequest.getUser();
        User ogUser = shift.getUser();

        shift.setUser(reqUser);
        shift.setStatus(Status.COVERED);
        shiftDAO.update(shift);
        dao.delete(buyRequest);

        EmailSender.sendEmail(reqUser.getEmail(), "Buy request",
                List.of("Your buy request for shift " + shift.getId() + " has been accepted."), false);
        if (ogUser != null) {
            EmailSender.sendEmail(ogUser.getEmail(), "Buy request",
                    List.of("User " + reqUser.getEmail() + " has bought shift " + shift.getId()), false);
        }
        context.status(200);
    }

    public void deleteBuyRequest(Context context)
    {
        int buyRequestId = Integer.parseInt(context.pathParam("reqId"));
        BuyRequest buyRequest = dao.readById(buyRequestId).orElse(null);

        if (buyRequest == null) {
            context.status(404);
            return;
        }

        EmailSender.sendEmail(buyRequest.getUser().getEmail(),
                "Buy request",
                List.of("Your buy request for shift " +
                        buyRequest.getShift().getId() +
                        " has been denied."),
                false);

        dao.delete(buyRequest);
        context.status(204);
    }
}
