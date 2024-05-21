package dat.controller;

import dat.dao.CompanyDAO;
import dat.dao.DAO;
import dat.dao.UserDAO;
import dat.dto.CompanyDTO;
import dat.exception.AuthorizationException;
import dat.model.Company;
import dat.model.User;
import io.javalin.http.Context;

public class CompanyController {
    private static UserDAO userDAO = UserDAO.getInstance();
    private static CompanyDAO companyDAO = CompanyDAO.getInstance();

    public void create(Context ctx) throws AuthorizationException {
        String companyName = ctx.pathParam("companyName");
        String companyAdminUsername = ctx.pathParam("companyAdmin");
        User user = userDAO.getUser(companyAdminUsername);

        Company company = new Company(companyName,user);
        companyDAO.create(company);

        ctx.status(201);
        ctx.json(company.toDTO());
    }

}
