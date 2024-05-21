package dat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.dto.CompanyDTO;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "companies")
public class Company extends SoftDeletableEntity implements dat.model.Entity<CompanyDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", unique = true, nullable = false)
    private int id;

    @JoinColumn(name = "company_admin_id", referencedColumnName = "id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private User companyAdmin;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    public Company(String companyName, User companyAdmin) {
        this.companyAdmin = companyAdmin;
        this.companyName = companyName;
    }

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
