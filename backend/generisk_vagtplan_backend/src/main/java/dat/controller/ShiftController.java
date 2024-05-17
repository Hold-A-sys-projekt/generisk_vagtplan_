package dat.controller;

import dat.dao.DAO;
import dat.dao.EmployeeDAO;
import dat.dao.ShiftDAO;
import dat.dto.ShiftDTO;
import dat.model.Shift;
import dat.model.Status;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.List;

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



            ShiftDTO shiftDTO = ctx.bodyAsClass(ShiftDTO.class);

            int id = shiftDTO.getUserId();

            Shift shift = new Shift(shiftDTO.getShiftStart(), shiftDTO.getShiftEnd());


            Shift res = shiftDAO.create(shift, id);
            ctx.json(res.toDTO());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Invalid input");
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
       List<Shift> res =  shiftDAO.getShiftsByUserId(employeeId);
       if (res.isEmpty()){
           context.status(404).result("No shifts found for user with id: " + employeeId);
           return;
       }
        context.json(res.stream().map(Shift::toDTO).toList());

    }

/*    public void setForSale(Context context)
    {
        int employeeId = Integer.parseInt(context.queryParam("e_id"));
        int shiftId = Integer.parseInt(context.pathParam("id"));

        Shift shift = shiftDAO.readById(shiftId).orElse(null);
        Employee employee = employeeDAO.readById(employeeId).orElse(null);

        if (shift == null || employee == null) {
            context.status(404);
            return;
        }

        if (shift.getEmployee().getId() == employeeId) {
            shift.setStatus(Status.FOR_SALE);
            shiftDAO.update(shift);
            context.status(200);
        }
    }*/

    public void getByStatus(Context context)
    {
        Status status = Status.valueOf(context.pathParam("status"));
        context.json(shiftDAO.getByStatus(status));
    }
}
