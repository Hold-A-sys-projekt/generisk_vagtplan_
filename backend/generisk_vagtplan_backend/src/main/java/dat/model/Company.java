package dat.model;

import dat.dto.CompanyDTO;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "companies")
public class Company implements dat.model.Entity<CompanyDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", unique = true, nullable = false)
    private int id;

    @JoinColumn(name = "company_admin_id", referencedColumnName = "id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private User companyAdmin;

    @Override
    public void setId(Object id) {
        if (!(id instanceof Integer)) {
            throw new IllegalArgumentException("Company id must be an integer");
        }

        this.id = (Integer) id;
    }

    @Override
    public CompanyDTO toDTO() {
        return new CompanyDTO(this);
    }
}
