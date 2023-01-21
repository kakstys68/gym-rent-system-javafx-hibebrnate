package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Gym gym;
    private String name;
    private String sportType;
    private int quantity;


    public Equipment(String name, String sportType, int quantity, Gym gym) {
        this.name = name;
        this.sportType = sportType;
        this.quantity = quantity;
        this.gym = gym;
    }

    @Override
    public String toString() {
        return name + " type: " + sportType + ", qty: " + quantity;
    }

}
