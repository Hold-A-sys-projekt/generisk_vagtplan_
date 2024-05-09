package dat.config;

import dat.dao.CompanyDAO;
import dat.dao.DepartmentDAO;
import dat.dao.UserDAO;
import dat.model.Company;
import dat.model.Department;
import dat.model.User;

public class PopulateConfig {

    public static void main(String[] args) {
        UserDAO userDao = UserDAO.getInstance();
        DepartmentDAO departmentDao = DepartmentDAO.getInstance();
        CompanyDAO companyDAO = CompanyDAO.getInstance();

        try {
            userDao.registerUser("john@gmail.com", "john", "1234", "employee");
            userDao.registerUser("karen@gmail.com", "karen", "1234", "employee");
            userDao.registerUser("admin@admin.com", "admin", "admin", "manager");
            User companyAdmin = userDao.registerUser("admin@company.com", "companyAdmin", "admin", "company_admin");

            Company company = companyDAO.create(new Company(companyAdmin));
            Department dep = departmentDao.create(new Department("Marketing", company));
            departmentDao.create(new Department("Sales", company));
            departmentDao.create(new Department("production", company));
            userDao.readAll().forEach(user -> {
                user.setDepartment(dep);
                userDao.update(user);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}