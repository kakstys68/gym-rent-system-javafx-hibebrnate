package fxControllers;

import hibernate.EquipmentHib;
import hibernate.GymHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Equipment;
import model.Gym;
import model.User;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EquipmentRegistrationPage {
    @FXML
    public TextField equipmentNameField;
    @FXML
    public TextField equipmentTypeField;
    @FXML
    public TextField equipmentQuantityField;
    public Button actionButton;
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RentSystem");
    private EquipmentHib equipmentHib;
    private User currentUser;
    private Equipment selectedEquipment;
    private Gym gym;

    public void setData(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
        this.equipmentHib = new EquipmentHib(entityManagerFactory);
    }
    public void setData(Gym gym){
        this.gym = gym;
    }

    public void createEquipment(ActionEvent actionEvent) {
        setData(entityManagerFactory);
        Equipment equipment = new Equipment(equipmentNameField.getText(),equipmentTypeField.getText(),Integer.parseInt(equipmentQuantityField.getText()), gym);
        equipmentHib.createEquipment(equipment);
    }
    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, Equipment selectedEquipment) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.selectedEquipment = selectedEquipment;
        this.equipmentHib = new EquipmentHib(entityManagerFactory);

        fillFields();
    }
    private void fillFields() {
        Equipment equipment = (Equipment) equipmentHib.getEquipmentById(selectedEquipment.getId());
        equipmentNameField.setText(equipment.getName());
        equipmentTypeField.setText(equipment.getSportType());
        equipmentQuantityField.setText(Integer.toString(equipment.getQuantity()));

        actionButton.setOnAction(actionEvent -> {
            updateEquipment(equipment);
        });
        actionButton.setText("Update equipment");
    }
    public void updateEquipment(Equipment equipment){
        equipment.setName(equipmentNameField.getText());
        equipment.setSportType(equipmentTypeField.getText());
        equipment.setQuantity(Integer.parseInt(equipmentQuantityField.getText()));
        equipmentHib.updateEquipment(equipment);
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Equipment update report", "Equipment " + equipment.getName() + " updated successfully");
    }


}
