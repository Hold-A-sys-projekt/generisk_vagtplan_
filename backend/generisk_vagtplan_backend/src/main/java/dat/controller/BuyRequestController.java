package dat.controller;

import dat.dao.DAO;
import dat.dao.ShiftDAO;
import dat.dao.UserDAO;
import dat.dto.BuyRequestDTO;
import dat.exception.ApiException;
import dat.model.BuyRequest;
import dat.model.Shift;
import dat.model.User;
import dat.util.EmailSender;
import io.javalin.http.Context;

import java.util.List;

public class BuyRequestController extends Controller<BuyRequest, BuyRequestDTO> {
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

        BuyRequest buyRequest = new BuyRequest(user, shift);

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
}