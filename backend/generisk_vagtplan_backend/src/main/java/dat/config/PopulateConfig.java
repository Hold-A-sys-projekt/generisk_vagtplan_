package dat.config;

import dat.dao.UserDAO;
import dat.model.User;

public class PopulateConfig {

    public static void main(String[] args) {
        UserDAO userDao = UserDAO.getInstance();

        try {
            userDao.registerUser("john@gmail.com", "john", "1234", "user");
            userDao.registerUser("karen@gmail.com", "karen", "1234", "user");
            userDao.registerUser("admin@admin.com", "admin", "admin", "admin");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}