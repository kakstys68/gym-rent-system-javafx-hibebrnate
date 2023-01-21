package fxControllers;

import hibernate.CommentHib;
import hibernate.OrdersHib;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.Comment;
import model.Gym;
import model.Orders;
import model.User;
import org.jboss.jandex.Main;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class OrderCreationPage {


    public TextField durationField;
    public DatePicker datePickOrder;
    public Button actionButton;
    private User currentUser;
    private Gym selectedGym;
    private OrdersHib ordersHib;
    private Orders orders;

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RentSystem");

    public void setData(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
        this.ordersHib = new OrdersHib(entityManagerFactory);
    }
    public void setData(EntityManagerFactory entityManagerFactory, User user, Gym selectedItem) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.selectedGym = selectedItem;
        this.ordersHib = new OrdersHib(entityManagerFactory);
    }
    private void fillFields() {
        Orders orders1 = (Orders)ordersHib.getOrderById(orders.getId());
        durationField.setText(Integer.toString(orders1.getTimeSpent()));
        datePickOrder.setValue(orders1.getDate());
        actionButton.setOnAction(actionEvent -> {
            updateOrder(orders1);
        });
        actionButton.setText("Update order");
    }
    public void setData(EntityManagerFactory entityManagerFactory, User user, Orders orders) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = user;
        this.orders = orders;
        this.ordersHib = new OrdersHib(entityManagerFactory);
        fillFields();
    }

    public void createNewOrder(ActionEvent actionEvent) {
        setData(entityManagerFactory);
        Orders orders = new Orders(currentUser, datePickOrder.getValue(), Integer.parseInt(durationField.getText()),selectedGym.getHourlyPrice() * Integer.parseInt(durationField.getText()), selectedGym);
        ordersHib.createOrder(orders);
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Order creation report", "Order created during - " + orders.getDate() + ", in gym - " + selectedGym.getName() + ", duration - " + orders.getTimeSpent() + " hours, price - " + orders.getPrice() + "€");

    }
    public void updateOrder(Orders orders){
        orders.setDate(datePickOrder.getValue());
        orders.setTimeSpent(Integer.parseInt(durationField.getText()));
        orders.setPrice(orders.getGym().getHourlyPrice() * orders.getTimeSpent());
        ordersHib.updateOrders(orders);
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Order update report", "Order updated successfully");
    }


}
