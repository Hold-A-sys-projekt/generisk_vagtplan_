package dat.controller;

import dat.dao.EmployeeDAO;
import dat.dao.ShiftDAO;
import dat.dto.EmployeeDTO;
import dat.dto.ShiftDTO;
import dat.model.Employee;
import dat.model.Shift;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EmployeeController extends Controller<Employee, EmployeeDTO> {

    private final EmployeeDAO dao;

    public EmployeeController(EmployeeDAO dao) {
        super(dao);
        this.dao = dao;
    }

    public void getEmployeeShifts(Context context) {

        int employeeId = Integer.parseInt(context.pathParam("id"));

        Employee employee = dao.readById(employeeId).orElse(null);

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
        Shift currentShift = dao.readCurrentShift(employeeId, currentDate).orElse(null);

        if (currentShift != null) {
            if (currentShift.getShiftStart().isAfter(currentDate)) {
                context.json(currentShift.toDTO());
            } else {
                context.result("Next shift starts on: " + currentShift.getShiftStart() + " to: " + currentShift.getShiftEnd());
            }

        } else {
            context.status(404).result("No future shifts found for employee with ID: " + employeeId);
        }
    }


}
