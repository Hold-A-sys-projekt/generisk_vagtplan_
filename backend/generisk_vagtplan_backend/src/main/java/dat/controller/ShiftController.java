package dat.controller;

import dat.dao.DAO;
import dat.dao.UserDAO;
import dat.dao.ShiftDAO;
import dat.dto.ShiftDTO;
import dat.model.User;
import dat.model.Shift;
import io.javalin.http.Context;

import java.time.LocalDateTime;

public class ShiftController extends Controller<Shift, ShiftDTO>{

    private final ShiftDAO shiftDAO;

    private final UserDAO employeeDAO = UserDAO.getInstance();


    public ShiftController(ShiftDAO dao) {
        super(dao);
        this.shiftDAO = dao;
    }


    @Override
    public void post(Context ctx){
        /*
        {
    "shiftStart": "2024-07-01T08:00:00",
    "shiftEnd": "2026-07-01T08:00:00"
}
         */
        try {
            int id = Integer.parseInt(ctx.queryParam("employee_id"));
            ShiftDTO shiftDTO = ctx.bodyAsClass(ShiftDTO.class);
            Shift shift = new Shift(shiftDTO.getShiftStart(), shiftDTO.getShiftEnd());
            Shift res = shiftDAO.create(shift, id);
            ctx.json(res.toDTO());
            ctx.status(201);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400);
        }
    }
}