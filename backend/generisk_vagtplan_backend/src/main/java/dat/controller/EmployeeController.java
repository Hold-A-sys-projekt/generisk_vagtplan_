package dat.controller;

import com.aayushatharva.brotli4j.common.annotations.Local;
import dat.dao.EmployeeDAO;
import dat.dao.RoleDAO;
import dat.dao.ShiftDAO;
import dat.dto.EmployeeDTO;
import dat.dto.ShiftDTO;
import dat.dto.UserDTO;
import dat.exception.ApiException;
import dat.exception.DatabaseException;
import dat.model.Role;
import dat.model.User;
import dat.model.Shift;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.List;

public class EmployeeController extends Controller<User, UserDTO> {

    private final EmployeeDAO dao;
    private final RoleDAO roleDAO = RoleDAO.getInstance();

    public EmployeeController(EmployeeDAO dao) {
        super(dao);
        this.dao = dao;
    }

    public void createEmployee(Context context) throws ApiException {
        String username = context.formParam("username");
        String password = context.formParam("password");
        String roleDTO = context.formParam("role");
        LocalDateTime createdAt = LocalDateTime.now();


//TODO: Issues with DTO structure, so im leaving this method halfway until further notice
    }

    public void getEmployeeShifts(Context context) {

        int employeeId = Integer.parseInt(context.pathParam("id"));

        User employee = dao.readById(employeeId).orElse(null);

        if (employee == null) {
            context.status(404);
            return;
        }

        List<ShiftDTO> shifts = employee.getShifts().stream()
                .map(Shift::toDTO)
                .toList();

        context.json(shifts);
    }

    public void getCurrentShift(Context context) {
        int employeeId = Integer.parseInt(context.pathParam("id"));

        LocalDateTime currentDate = LocalDateTime.now();

        // This is so when we query and say it is 2 pm, we still get today's shift even if its starts at 8 am
        //if we dont set these to 0, it will give us the next future shift if the hour number is past the shiftstart
        currentDate = currentDate.withHour(0).withMinute(0).withSecond(0).withNano(0);

        System.out.println("DATE IS " + currentDate);
        // Changed from being set to null to throw exception if Optional is empty.
        Shift currentShift = dao.readCurrentShift(employeeId, currentDate).orElseThrow(() -> new DatabaseException(404, "No future shifts found for employee with ID: " + employeeId));

        if (currentShift.getShiftStart().isAfter(currentDate)) {
            context.json(currentShift.toDTO());
        } else {
            context.result("Next shift starts on: " + currentShift.getShiftStart() + " to: " + currentShift.getShiftEnd());
        }

    }
}
