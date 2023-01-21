package fxControllers;

import hibernate.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Equipment;
import model.Gym;
import model.User;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

public class EquipmentPage {

    public ListView<Equipment> equipmentList;
    public Button addEquipmentBtn;
    public Button updEquipmentBtn;
    public Button delEquipmentBtn;
    private EquipmentHib equipmentHib;
    private EntityManagerFactory entityManagerFactory;
    private User user;

    private Gym gym;
    private CheckBox isRentProvider;
    public void setData(EntityManagerFactory entityManagerFactory, User user, Gym gym, EquipmentHib equipmentHib, CheckBox isRentProvider) {
        this.entityManagerFactory = entityManagerFactory;
        this.equipmentHib = equipmentHib;
        this.gym = gym;
        this.user = user;
        this.isRentProvider = isRentProvider;
        fillList();
        disableData(isRentProvider);
    }

    private void disableData(CheckBox isRentProvider) {
        if(!isRentProvider.isSelected()){
            addEquipmentBtn.setVisible(false);
            updEquipmentBtn.setVisible(false);
            delEquipmentBtn.setVisible(false);
        } else{
            addEquipmentBtn.setVisible(true);
            updEquipmentBtn.setVisible(true);
            delEquipmentBtn.setVisible(true);
        }
    }

    private void fillList() {
        List<Equipment> equipment;
        equipment = gym.getEquipment();
        equipment.forEach(g->equipmentList.getItems().add(g));
    }

    public void addEquipment(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/equipment-registration-page.fxml"));
        Parent parent = fxmlLoader.load();
        EquipmentRegistrationPage equipmentRegistrationPage = fxmlLoader.getController();
        equipmentRegistrationPage.setData(gym);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(equipmentList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }

    public void updateEquipment(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/equipment-registration-page.fxml"));
        Parent parent = fxmlLoader.load();
        EquipmentRegistrationPage equipmentRegistrationPage = fxmlLoader.getController();
        equipmentRegistrationPage.setData(entityManagerFactory, user, equipmentList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        //stage.initOwner(clientList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }

    public void deleteEquipment(ActionEvent actionEvent) {
        equipmentHib.deleteEquipment(equipmentList.getSelectionModel().getSelectedItem());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Equipment delete report", "Equipment " + equipmentList.getSelectionModel().getSelectedItem().getName() + " deleted successfully");

    }
}
