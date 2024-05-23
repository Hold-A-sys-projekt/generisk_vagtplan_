package dat.config;

import dat.dao.CompanyDAO;
import dat.dao.DepartmentDAO;
import dat.dao.RoleDAO;
import dat.dao.ShiftDAO;
import dat.dao.UserDAO;
import dat.model.*;

import java.time.LocalDateTime;

public class PopulateConfig {

    public static void main(String[] args) {
        UserDAO userDao = UserDAO.getInstance();
        DepartmentDAO departmentDao = DepartmentDAO.getInstance();
        CompanyDAO companyDAO = CompanyDAO.getInstance();
        ShiftDAO shiftDAO = ShiftDAO.getInstance();

        try {
            userDao.registerUser("john@gmail.com", "john", "1234", "employee");
            userDao.registerUser("karen@gmail.com", "karen", "1234", "employee");
            userDao.registerUser("admin@admin.com", "admin", "admin", "manager");
            User companyAdmin = userDao.registerUser("admin@company.com", "companyAdmin", "admin", "company_admin");

            Company company = companyDAO.create(new Company("Vask aps", companyAdmin));
            Department dep = departmentDao.create(new Department("Marketing", company));
            departmentDao.create(new Department("Sales", company));
            departmentDao.create(new Department("production", company));

            Shift shift1 = new Shift(LocalDateTime.now(), LocalDateTime.now().plusHours(8), userDao.readById(1).get());
            shift1.setStatus(Status.FOR_SALE);
            shiftDAO.create(shift1);

            userDao.readAll().forEach(user -> {
                user.setDepartment(dep);
                userDao.update(user);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}