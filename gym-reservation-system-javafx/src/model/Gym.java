package model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Gym implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<Equipment> equipment;
    @OneToMany(mappedBy = "gym", cascade= CascadeType.ALL)
    private List<Comment> comments;
    @ManyToOne
    private RentProvider rentProvider;
    @OneToMany(mappedBy = "gym", cascade = CascadeType.ALL)
    private List<Orders> orders;
    private String address;
    private double hourlyPrice;
    private String type;

    public Gym(String name, String address, double hourlyPrice, String type, RentProvider rentProvider) {
        this.name = name;
        this.address = address;
        this.hourlyPrice = hourlyPrice;
        this.type = type;
        this.rentProvider = rentProvider;
    }

    @Override
    public String toString() {
        return "Name: " + name + "; Address: " + address + "; Price: " + hourlyPrice + "€/h";
    }
}
