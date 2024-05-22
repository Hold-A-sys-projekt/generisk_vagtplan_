package dat.controller;

import dat.config.HibernateConfig;
import dat.dao.DAO;
import dat.dao.ShiftDAO;
import dat.dao.UserDAO;
import dat.dto.BuyRequestDTO;
import dat.exception.ApiException;
import dat.model.BuyRequest;
import dat.model.Shift;
import dat.model.User;
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

        BuyRequest buyRequest = new BuyRequest(user, shift);

        dao.create(buyRequest);

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
}
