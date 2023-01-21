package fxControllers;

import com.sun.javafx.menu.MenuItemBase;
import hibernate.GymHib;
import hibernate.UserHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Client;
import model.Gym;
import model.RentProvider;
import model.User;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;

public class GymRegistrationPage {
    @FXML
    public TextField typeField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField hpriceField;
    @FXML
    public TextField gymNameField;
    @FXML
    public Button actionButton;
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RentSystem");
    private GymHib gymHib;
    private User currentUser;
    private Gym selectedGym;

    public void setData(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
        this.gymHib = new GymHib(entityManagerFactory);
    }

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, Gym selectedGym) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.selectedGym = selectedGym;
        this.gymHib = new GymHib(entityManagerFactory);
        fillFields();
    }
    public void setUser(EntityManagerFactory entityManagerFactory, User currentUser){
        this.currentUser = currentUser;
    }
    private void fillFields() {
        Gym gym = (Gym)gymHib.getGymById(selectedGym.getId());
        gymNameField.setText(gym.getName());
        typeField.setText(gym.getType());
        addressField.setText(gym.getAddress());
        hpriceField.setText(Double.toString(gym.getHourlyPrice()));

        actionButton.setOnAction(actionEvent -> {
            updateGym(gym);
        });
        actionButton.setText("Update gym");
    }

    public void createGym(ActionEvent actionEvent) {
        setData(entityManagerFactory);
        if (Objects.equals(gymNameField.getText(), "") || Objects.equals(addressField.getText(), "") || Objects.equals(hpriceField.getText(), "") || Objects.equals(typeField.getText(), "")){
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Gym creation error", "Enter all values correctly");
        } else{
            Gym gym = new Gym(gymNameField.getText(),addressField.getText(),Double.parseDouble(hpriceField.getText()), typeField.getText(), (RentProvider) currentUser);
            gymHib.createGym(gym);
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Gym creation success", "Gym successfully added");
        }


    }

    public void updateGym(Gym gym){
        gym.setName(gymNameField.getText());
        gym.setType(typeField.getText());
        gym.setAddress(addressField.getText());
        gym.setHourlyPrice(Double.parseDouble(hpriceField.getText()));
        gymHib.updateGym(gym);
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Gym update report", "Gym " + gym.getName() + " updated successfully");
    }



}
