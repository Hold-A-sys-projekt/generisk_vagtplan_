package dat.config;

import dat.dao.DepartmentDAO;
import dat.dao.UserDAO;
import dat.model.Department;
import dat.model.User;

public class PopulateConfig {

    public static void main(String[] args) {
        UserDAO userDao = UserDAO.getInstance();
        DepartmentDAO departmentDao = DepartmentDAO.getInstance();

        try {
            userDao.registerUser("john@gmail.com", "john", "1234", "employee");
            userDao.registerUser("karen@gmail.com", "karen", "1234", "employee");
            userDao.registerUser("admin@admin.com", "admin", "admin", "manager");

            Department dep = departmentDao.create(new Department("Marketing"));
            userDao.readAll().forEach(user -> {
                user.setDepartment(dep);
                userDao.update(user);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}