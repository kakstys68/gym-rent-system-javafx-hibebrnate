package model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client extends User implements Serializable {

    LocalDate birthDate;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Client(String username, String password, String name, String surname, String email, LocalDate birthDate) {
        super(username, password, name, surname, email);
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return getUsername() + ", " + getName() + " " + getSurname();
    }
}
