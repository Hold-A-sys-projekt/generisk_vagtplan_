package dat.controller;

import dat.dao.DAO;
import dat.dao.EmployeeDAO;
import dat.dao.ShiftDAO;
import dat.dto.ShiftDTO;
import dat.model.Employee;
import dat.model.Shift;
import dat.model.Status;
import io.javalin.http.Context;

import java.time.LocalDateTime;

public class ShiftController extends Controller<Shift, ShiftDTO>{

    private final ShiftDAO shiftDAO;

    private final EmployeeDAO employeeDAO = EmployeeDAO.getInstance();


    public ShiftController(ShiftDAO dao) {
        super(dao);
        this.shiftDAO = dao;
    }

    public void punchIn(Context context) {
        int shiftId = Integer.parseInt(context.pathParam("id"));

        Shift shift = shiftDAO.readById(shiftId).orElse(null);

        if (shift == null) {
            context.status(404);
            return;
        }

        shift.setPunchIn(LocalDateTime.now());

        shiftDAO.update(shift);

        context.json(shift.toDTO());
    }

    public void punchOut(Context context) {

        int shiftId = Integer.parseInt(context.pathParam("id"));

        Shift shift = shiftDAO.readById(shiftId).orElse(null);

        if (shift == null) {
            context.status(404);
            return;
        }

        shift.setPunchOut(LocalDateTime.now());

        shiftDAO.update(shift);

        context.json(shift.toDTO());
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
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400);
        }




    }

    public void getShiftStatus(Context context) {
        int shiftId = Integer.parseInt(context.pathParam("id"));

        Shift shift = shiftDAO.getShiftStatus(shiftId);

        context.json(shift.toDTO());
    }

    public void updateShiftStatus(Context context) {
        int shiftId = Integer.parseInt(context.pathParam("id"));
        Status status = Status.valueOf(context.queryParam("status"));

        Shift shift = shiftDAO.updateShiftStatus(shiftId, status);

        context.json(shift.toDTO());
    }

    public void getShiftsByEmployeeId(Context context) {
        int employeeId = Integer.parseInt(context.pathParam("id"));
        context.json(shiftDAO.getShiftsByEmployeeId(employeeId));
    }

    public void updateShiftDateAndTime(Context context) {
        int shiftId = Integer.parseInt(context.pathParam("id"));
        ShiftDTO shiftDTO = context.bodyAsClass(ShiftDTO.class);

        Shift shift = shiftDAO.readById(shiftId).orElse(null);

        if (shift == null) {
            context.status(404);
            return;
        }

        shift.setShiftStart(shiftDTO.getShiftStart());
        shift.setShiftEnd(shiftDTO.getShiftEnd());

        shiftDAO.update(shift);

        context.json(shift.toDTO());
    }
}
