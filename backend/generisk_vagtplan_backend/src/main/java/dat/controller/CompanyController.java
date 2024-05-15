package dat.controller;

import dat.dao.DAO;
import dat.dto.CompanyDTO;
import dat.model.Company;

public class CompanyController extends Controller<Company, CompanyDTO>{
    public CompanyController(DAO dao) {
        super(dao);
    }
}
