package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RentProvider extends User implements Serializable {
    private String companyName;
    private boolean isLegal;
    @OneToMany(mappedBy = "rentProvider", cascade = CascadeType.ALL)
    private List<Gym> gyms;

    /*public RentProvider(String username, String password, String name, String surname, String email, boolean isAdmin, String representative, boolean isLegal) {
        super(username, password, name, surname, email, isAdmin);
        this.representative = representative;
        this.isLegal = isLegal;
    }*/

    public RentProvider(String username, String password, String name, String surname, String email, String companyName, boolean isLegal) {
        super(username, password, name, surname, email);
        this.companyName = companyName;
        this.isLegal = isLegal;
    }


    public boolean isLegal() {
        return isLegal;
    }

    public void setLegal(boolean legal) {
        isLegal = legal;
    }
    @Override
    public String toString() {
        return getUsername() + ", Company: " + getCompanyName();
    }
}
