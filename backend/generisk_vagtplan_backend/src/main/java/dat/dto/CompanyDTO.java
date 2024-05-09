package dat.dto;

import dat.dao.CompanyDAO;
import dat.model.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanyDTO implements DTO<Company> {
    private int id;

    public CompanyDTO(Company company) {
        this.id = company.getId();
    }

    @Override
    public Company toEntity() {
        return CompanyDAO.getInstance().readById(this.id).orElse(null);
    }
}
