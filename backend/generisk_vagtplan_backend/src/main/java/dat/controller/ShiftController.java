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
import java.util.stream.Collectors;

public class ShiftController extends Controller<Shift, ShiftDTO> {

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
    public void post(Context ctx) {

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

    public void getShiftsByUserId(Context context) {
        int employeeId = Integer.parseInt(context.pathParam("id"));
        List<Shift> res = shiftDAO.getShiftsByUserId(employeeId);
        if (res.isEmpty()) {
            context.status(404).result("No shifts found for user with id: " + employeeId);
            return;
        }
        context.json(res.stream().map(Shift::toDTO).toList());

    }

    public void getShiftsByUserIdWithDTOs(Context context) {
    int userId = Integer.parseInt(context.pathParam("id"));
    List<Shift> shifts = shiftDAO.getShiftsByUserId(userId);
    if (shifts.isEmpty()) {
        context.status(404).result("No shifts found for user with id: " + userId);
        return;
    }
    List<ShiftDTO> shiftDTOs = shifts.stream().map(Shift::toDTO).collect(Collectors.toList());
    // Log the shiftDTOs
    shiftDTOs.forEach(shiftDTO -> System.out.println("ShiftDTO: " + shiftDTO));
    context.json(shiftDTOs);
}

 public void selectShifts(Context context) {
        List<Integer> shiftIds = context.bodyAsClass(ShiftIdsDTO.class).getShiftIds();

        List<Shift> shifts = shiftDAO.getShiftsByIds(shiftIds);
        if (shifts.size() != shiftIds.size()) {
            context.status(404).result("One or more shifts not found");
            return;
        }

        
        /* for (Shift shift : shifts) {
            shift.setStatus(Status.SELECTED);
            shiftDAO.update(shift);
        } */

        context.json(shifts.stream().map(Shift::toDTO).collect(Collectors.toList()));
    }

    // DTO class to handle the request body
    public static class ShiftIdsDTO {
        private List<Integer> shiftIds;

        public List<Integer> getShiftIds() {
            return shiftIds;
        }

        public void setShiftIds(List<Integer> shiftIds) {
            this.shiftIds = shiftIds;
        }
    }
}






