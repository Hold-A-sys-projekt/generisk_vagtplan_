package dat.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dao.CompanyDAO;
import dat.dao.DepartmentDAO;
import dat.dao.EmployeeDAO;
import dat.dao.RoleDAO;
import dat.dto.ShiftDTO;
import dat.dto.UserDTO;
import dat.exception.ApiException;
import dat.exception.DatabaseException;
import dat.model.*;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.List;

public class EmployeeController extends Controller<User, UserDTO> {

    private final EmployeeDAO dao;
    private final RoleDAO roleDAO = RoleDAO.getInstance();
    private final DepartmentDAO departmentDAO = DepartmentDAO.getInstance();
    private final CompanyDAO companyDAO = CompanyDAO.getInstance();

    public EmployeeController(EmployeeDAO dao) {
        super(dao);
        this.dao = dao;
    }

    public void createEmployee(Context context) {
        String body = context.body();

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode jsonNode = mapper.readTree(body);
            String username = jsonNode.get("username").asText();
            String password = jsonNode.get("password").asText();
            String email = jsonNode.get("email").asText();
            String rolename = jsonNode.get("role").asText();
            int departmentId = jsonNode.get("department").asInt();


            Role role = roleDAO.readByName(rolename);
            Department department = departmentDAO.readById(departmentId).orElse(null);

            if (role == null) {
                throw new ApiException(400, "Role does not exist");
            }

            User user = new User(email, username, password, role, department );

            role.addUser(user);
            department.addUser(user);
            User createdUser = dao.create(user);

            context.json(createdUser.toDTO());
        } catch (Exception e) {
            e.printStackTrace(); // Debugging: Print stack trace if an exception occurs
            context.result(e.getMessage());
        }
    }


    public void getEmployeeShifts(Context context) {

        int employeeId = Integer.parseInt(context.pathParam("id"));

        User employee = dao.readById(employeeId).orElse(null);

        if (employee == null) {
            context.status(404).result("Employee not found");
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

        // Changed from being set to null to throw exception if Optional is empty.
        Shift currentShift = dao.readCurrentShift(employeeId, currentDate).orElseThrow(() -> new DatabaseException(404, "No future shifts found for employee with ID: " + employeeId));
        System.out.println(currentShift);
        System.out.println(currentDate);
        if(currentShift.getShiftStart().toLocalDate().isEqual(currentDate.toLocalDate())){
            context.json(currentShift.toDTO());
        } else if (currentShift.getShiftStart().isAfter(currentDate)){
            context.result("Next shift starts at: " + currentShift.getShiftStart() + " and ends at: " + currentShift.getShiftEnd());
        } else {
            context.status(404).result("No future shifts found for employee with ID: " + employeeId);
        }
    }
}
