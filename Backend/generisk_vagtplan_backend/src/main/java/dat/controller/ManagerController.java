package dat.controller;

import dat.dao.ManagerDAO;
import dat.dto.EmployeeDTO;
import dat.dto.ManagerDTO;
import dat.model.Employee;
import dat.model.Manager;
import io.javalin.http.Context;


public class ManagerController extends Controller<Manager, ManagerDTO> {
    private final ManagerDAO dao;

    public ManagerController(ManagerDAO dao) {
        super(dao);
        this.dao = dao;
    }

    public void addEmployee(Context ctx) {
        EmployeeDTO employeeDTO = ctx.bodyAsClass(EmployeeDTO.class);
        Employee employee = new Employee();
        employee.setEmployeename(employeeDTO.getEmployeename());
        employee.setRole(employeeDTO.getRole());
        dao.addEmployee(employee);
        ctx.status(201);
    }

    public void updateEmployeeRole(Context ctx) {
        int employeeId = Integer.parseInt(ctx.pathParam("id"));
        try {
            EmployeeDTO requestBody = ctx.bodyAsClass(EmployeeDTO.class);
            String newRole = requestBody.getRole();

            Employee employee = dao.getEmployee(employeeId);
            if (employee != null) {
                employee.setRole(newRole);
                dao.updateEmployeeRole(employee, newRole);
                ctx.status(200).json(employee);
            } else {
                ctx.status(404).result("The employee does not exist");
            }
        } catch (Exception e) {
            ctx.status(400).result("Invalid request body");
        }
    }
}


