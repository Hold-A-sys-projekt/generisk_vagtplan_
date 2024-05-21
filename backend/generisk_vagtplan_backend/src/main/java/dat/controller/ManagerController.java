package dat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dao.ManagerDAO;
import dat.dao.ShiftDAO;
import dat.dto.ShiftDTO;
import dat.dto.UserDTO;
import dat.model.Role;
import dat.model.Shift;
import dat.model.User;
import io.javalin.http.Context;


public class ManagerController extends Controller<User, UserDTO> {
    private final ManagerDAO dao;

    private final ShiftDAO shiftDAO = ShiftDAO.getInstance();

    public ManagerController(ManagerDAO dao) {
        super(dao);
        this.dao = dao;
    }

    public void addEmployee(Context ctx) {
        UserDTO employeeDTO = ctx.bodyAsClass(UserDTO.class);
        User employee = new User();
        employee.setUsername(employeeDTO.getUsername());
        employee.setRole(employeeDTO.getRole().toEntity());
        dao.addEmployee(employee);
        ctx.status(201);
    }

    public void updateEmployeeRole(Context ctx) {
        int employeeId = Integer.parseInt(ctx.pathParam("id"));
        try {
            UserDTO requestBody = ctx.bodyAsClass(UserDTO.class);
            String newRole = requestBody.getRole().getName();

            User employee = dao.getEmployee(employeeId);
            if (employee != null) {
                employee.setRole(new Role(newRole));
                dao.updateEmployeeRole(employee, newRole);
                ctx.status(200).json(employee);
            } else {
                ctx.status(404).result("The employee does not exist");
            }
        } catch (Exception e) {
            ctx.status(400).result("Invalid request body");
        }
    }

    public void getAllEmployees(Context context) {

        context.json(dao.getAllEmployees());
    }

    public void updateShift(Context context) {

        int userId = Integer.parseInt(context.pathParam("userid"));
        int shiftId = Integer.parseInt(context.pathParam("id"));

        try {
            ShiftDTO shiftDTO = context.bodyAsClass(ShiftDTO.class);
            Shift shift = shiftDTO.toEntity();
            Shift updatedShift = shiftDAO.updateShift(shift);
            context.json(updatedShift.toDTO());


        } catch (Exception e) {
            e.printStackTrace();
            context.result(e.getMessage());
        }
    }
}

