package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //private OrderStatus status;
    @ManyToOne
    private User buyer;
    private LocalDate date;
    private int timeSpent;
    private double price;
    //CIA PAKLAUSTI DEL RYSIO
    @ManyToOne
    private Gym gym;

    public Orders(User buyer, LocalDate date, int timeSpent, double price, Gym gym) {
        this.buyer = buyer;
        this.date = date;
        this.timeSpent = timeSpent;
        this.price = price;
        this.gym = gym;
    }


    @Override
    public String toString() {
        return getDate().toString() + ", gym: " + gym.getName() + ", duration: " + getTimeSpent() + " hours, total price: " + getPrice() + "€, buyer: " + buyer.getUsername();
    }
}
