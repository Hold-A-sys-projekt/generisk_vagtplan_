package dat.controller;

import dat.dao.EmployeeDAO;
import dat.dto.EmployeeDTO;
import dat.dto.ShiftDTO;
import dat.model.Employee;
import dat.model.Shift;
import io.javalin.http.Context;

import java.util.List;
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
}
