package fxControllers;

import hibernate.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import org.hibernate.annotations.Check;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class MainPage implements Initializable {

    public ListView<Client> clientList;
    public ListView<RentProvider> rentProviderList;
    public Tab gymManagementTab;
    public ListView<Orders> orderList;
    public Tab commentTab;
    public Tab orderTab;
    public Tab userTab;
    public Button addGymBtn;
    public Button updateGymBtn;
    public Button deleteGymBtn;
    public Button orderGymBtn;
    public Button filterBtn;
    public DatePicker dateFromFilter;
    public DatePicker dateToFilter;
    public TextField durationFilter;
    public TextField priceFilter;
    public BarChart<String, Number> chartGyms;

    private EntityManagerFactory entityManagerFactory;
    private User user;
    private UserHib userHib;
    public ListView<Gym> gymList;
    private Gym gym;
    private OrdersHib ordersHib;
    private GymHib gymHib;
    private EquipmentHib equipmentHib;
    private CommentHib commentHib;
    private CheckBox isRentProvider;

    //USERS
    public void setData(EntityManagerFactory entityManagerFactory, User user, GymHib gymHib, EquipmentHib equipmentHib, CommentHib commentHib, OrdersHib ordersHib, CheckBox isRentProvider) {
        this.entityManagerFactory = entityManagerFactory;
        this.gymHib = gymHib;
        this.equipmentHib = equipmentHib;
        this.commentHib = commentHib;
        this.userHib = new UserHib(entityManagerFactory);
        this.ordersHib = ordersHib;
        this.user = user;
        this.isRentProvider=isRentProvider;
        fillAllLists(isRentProvider);
        disableData(isRentProvider);
    }

    //TODO fix cia
    private void disableData(CheckBox checkIfRentProvider) {
        if(checkIfRentProvider.isSelected()){
            userTab.setDisable(true);
            chartGyms.setVisible(true);
        } else if(!checkIfRentProvider.isSelected()){
            userTab.setDisable(true);
            addGymBtn.setVisible(false);
            updateGymBtn.setVisible(false);
            deleteGymBtn.setVisible(false);
            chartGyms.setVisible(false);
        }
        if(user.isAdmin()){
            userTab.setDisable(false);
            addGymBtn.setVisible(true);
            updateGymBtn.setVisible(true);
            deleteGymBtn.setVisible(true);
            chartGyms.setVisible(false);
        }
    }
    private void fillAllLists(CheckBox checkIfRentProvider) {
        if(checkIfRentProvider.isSelected()){
            List<Gym> rentProviderGyms;
            RentProvider tempProvider = (RentProvider) user;
            rentProviderGyms = tempProvider.getGyms();
            rentProviderGyms.forEach(g->gymList.getItems().add(g));
            refreshChart(rentProviderGyms);
        } else{
            List<Gym> allGyms = gymHib.getAllGyms();
            allGyms.forEach(g->gymList.getItems().add(g));
        }
        if(user.isAdmin()){
            List<Orders> allOrders = ordersHib.getAllOrders();
            allOrders.forEach(o->orderList.getItems().add(o));
            List<Client> allClients = userHib.getAllClients();
            allClients.forEach(c->clientList.getItems().add(c));
            List<RentProvider> allRentProviders = userHib.getAllRentProviders();
            allRentProviders.forEach(r->rentProviderList.getItems().add(r));
            List<Gym> allGyms = gymHib.getAllGyms();
            allGyms.forEach(g->gymList.getItems().add(g));
        } else{
            List<Orders> ordersByUser = user.getMyOrders();
            ordersByUser.forEach(o->orderList.getItems().add(o));
        }
    }



    public void updateClient(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/registration-page.fxml"));
        Parent parent = fxmlLoader.load();
        RegistrationPage registrationPage = fxmlLoader.getController();
        registrationPage.setData(entityManagerFactory, user, clientList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(clientList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }
    public void updateRentProvider(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/registration-page.fxml"));
        Parent parent = fxmlLoader.load();
        RegistrationPage registrationPage = fxmlLoader.getController();
        registrationPage.setData(entityManagerFactory, user, rentProviderList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(rentProviderList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }
    public void deleteClient(ActionEvent actionEvent) {
        userHib.deleteUser(clientList.getSelectionModel().getSelectedItem());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User delete report", "User " + user.getUsername() + " deleted successfully");
        gymList.refresh();
    }

    public void deleteRentProvider(ActionEvent actionEvent) throws IOException {
            userHib.deleteUser(rentProviderList.getSelectionModel().getSelectedItem());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User delete report", "User " + user.getUsername() + " deleted successfully");
    }

    //GYM

    public void openAddGym(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/gym-registration-page.fxml"));
        Parent parent = fxmlLoader.load();
        GymRegistrationPage gymRegistrationPage = fxmlLoader.getController();
        gymRegistrationPage.setUser(entityManagerFactory, user);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(gymList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }

    public void updateGym(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/gym-registration-page.fxml"));
        Parent parent = fxmlLoader.load();
        GymRegistrationPage gymRegistrationPage = fxmlLoader.getController();
        gymRegistrationPage.setData(entityManagerFactory, user, gymList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(rentProviderList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }

    public void deleteGym(ActionEvent actionEvent) {
        gymHib.deleteGym(gymList.getSelectionModel().getSelectedItem());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Gym delete report", "Gym " + gymList.getSelectionModel().getSelectedItem().getName() + " deleted successfully");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    public void updateOrder(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/order-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        OrderCreationPage orderCreationPage = fxmlLoader.getController();
        orderCreationPage.setData(entityManagerFactory, user, orderList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(clientList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }

    public void deleteOrder(ActionEvent actionEvent) {
        ordersHib.deleteOrders(orderList.getSelectionModel().getSelectedItem());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Order delete report", "Order deleted successfully");
    }

    public List<Orders> getOrdersWithFilter(List<Orders> orders, LocalDate from, LocalDate to, int duration, double price){
        List<Orders> result = new ArrayList<>();

        for(Orders d : orders){
            if(d.getDate().isAfter(from) && d.getDate().isBefore(to) && d.getTimeSpent() <= duration && d.getPrice() <= price){
                result.add(d);
            }
        }
        return result;
    }

    public void filter(ActionEvent actionEvent) {
        List<Orders> allOrders;
        List<Orders> filteredOrders;
        if(user.isAdmin()){
            allOrders = ordersHib.getAllOrders();
        } else {
            allOrders = user.getMyOrders();
        }
        filteredOrders = getOrdersWithFilter(allOrders, dateFromFilter.getValue(), dateToFilter.getValue(), Integer.parseInt(durationFilter.getText()), Double.parseDouble(priceFilter.getText()));
        orderList.getItems().clear();
        filteredOrders.forEach(o -> orderList.getItems().add(o));
        orderList.refresh();
    }

    public void orderGym(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/order-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        OrderCreationPage orderCreationPage = fxmlLoader.getController();
        orderCreationPage.setData(entityManagerFactory, user, gymList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(clientList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();

    }

    public void openEquipmentList(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/equipment-page.fxml"));
        Parent parent = fxmlLoader.load();
        EquipmentPage equipmentPage = fxmlLoader.getController();
        equipmentPage.setData(entityManagerFactory, user, gymList.getSelectionModel().getSelectedItem(), equipmentHib, isRentProvider);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(clientList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }
    public void openComments(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/comment-page.fxml"));
        Parent parent = fxmlLoader.load();
        CommentPage commentPage = fxmlLoader.getController();
        commentPage.setData(entityManagerFactory, user, gymList.getSelectionModel().getSelectedItem(), commentHib);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(clientList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }

    public void refreshChart(List<Gym> rentProviderGyms) {
        chartGyms.getData().clear();

        // cia pagal kuri destination'a grafa darysi

        // Checkpoint address, days to complete it
        //defining the axes
        final Axis<String> xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Gyms");
        yAxis.setLabel("€");
        chartGyms.setTitle("Price comparison");

        //defining a series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("€");

        //populating the series with data
        // cia fillinsi x ir y reiksmes pagal savo
        int size = rentProviderGyms.size();
        for(int i = 0; i < size; i++){
            series.getData().add(new XYChart.Data<>(rentProviderGyms.get(i).getName(), rentProviderGyms.get(i).getHourlyPrice()));
        }

        //Displaying series data
        chartGyms.getData().add(series);
    }


}
