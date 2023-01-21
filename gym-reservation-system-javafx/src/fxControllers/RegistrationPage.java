package fxControllers;

import hibernate.UserHib;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Client;
import model.RentProvider;
import model.User;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistrationPage implements Initializable {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField emailField;
    @FXML
    public DatePicker birthdateField;
    @FXML
    public Button actionButton;
    @FXML
    public CheckBox isRentProvider;
    @FXML
    public TextField companyName;

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RentSystem");
    private User currentUser;
    private UserHib userHib;
    private Client selectedClient;
    private RentProvider selectedRentProvider;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, Client selectedClient) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.selectedClient = selectedClient;
        this.userHib = new UserHib(entityManagerFactory);

        fillFields();
    }
    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, RentProvider selectedRentProvider) {
        this.currentUser = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.selectedRentProvider = selectedRentProvider;
        this.userHib = new UserHib(entityManagerFactory);

        fillFieldsRentProvider();
    }

    private void fillFieldsRentProvider() {
        RentProvider rentProvider = (RentProvider) userHib.getUserById(selectedRentProvider.getId());
        usernameField.setText(rentProvider.getUsername());
        passwordField.setText(rentProvider.getPassword());
        nameField.setText(rentProvider.getName());
        surnameField.setText(rentProvider.getSurname());
        emailField.setText(rentProvider.getEmail());
        companyName.setText(rentProvider.getCompanyName());
        actionButton.setOnAction(actionEvent -> {
            updateRentProvider(rentProvider);
        });
        actionButton.setText("Update user");
    }

    private void fillFields() {
        Client client = (Client)userHib.getUserById(selectedClient.getId());
        usernameField.setText(client.getUsername());
        passwordField.setText(client.getPassword());
        nameField.setText(client.getName());
        surnameField.setText(client.getSurname());
        emailField.setText(client.getEmail());
        birthdateField.setValue(client.getBirthDate());
        actionButton.setOnAction(actionEvent -> {
            updateClient(client);
        });
        actionButton.setText("Update user");
    }

    private void updateClient(Client client) {
        client.setUsername(usernameField.getText());
        client.setPassword(passwordField.getText());
        client.setName(nameField.getText());
        client.setSurname(surnameField.getText());
        client.setEmail(emailField.getText());
        client.setBirthDate(birthdateField.getValue());
        userHib.updateUser(client);
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User update report", "User updated successfully");
    }
    private void updateRentProvider(RentProvider rentProvider){
        rentProvider.setUsername(usernameField.getText());
        rentProvider.setPassword(passwordField.getText());
        rentProvider.setName(nameField.getText());
        rentProvider.setSurname(surnameField.getText());
        rentProvider.setEmail(emailField.getText());
        rentProvider.setCompanyName(companyName.getText());
        userHib.updateUser(rentProvider);
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User update report", "User updated successfully");
    }

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.userHib = new UserHib(entityManagerFactory);
    }

    public void disableFields() {
        if (isRentProvider.isSelected()) {
            birthdateField.setDisable(true);
            companyName.setDisable(false);
        } else {
            birthdateField.setDisable(false);
            companyName.setDisable(true);
        }
    }

    public void createNewUser(ActionEvent actionEvent) throws IOException {
        setData(entityManagerFactory);
        if(isRentProvider.isSelected()){
            if(Objects.equals(usernameField.getText(), "") || Objects.equals(passwordField.getText(), "") || Objects.equals(nameField.getText(), "") || Objects.equals(surnameField.getText(), "") || Objects.equals(emailField.getText(), "") || Objects.equals(companyName.getText(), "")){
                FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Registration error", "Some value was not found. Please fill all fields");
            } else{
                RentProvider rentProvider = new RentProvider(usernameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), emailField.getText(), companyName.getText(), true);
                userHib.createUser(rentProvider);
                openLoginPage();
            }
        }else{
            if(Objects.equals(usernameField.getText(), "") || Objects.equals(passwordField.getText(), "") || Objects.equals(nameField.getText(), "") || Objects.equals(surnameField.getText(), "") || Objects.equals(emailField.getText(), "") || birthdateField.getValue() == null) {
                FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Registration error", "Some value was not found. Please fill all fields");
            } else {
                Client client = new Client(usernameField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), emailField.getText(), birthdateField.getValue());
                userHib.createUser(client);
                openLoginPage();
            }
        }
    }
    public void openLoginPage()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegistrationPage.class.getResource("../view/login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }

    //public void showDetails(EntityManagerFactory entityManagerFactory, User currentUser, RentProvider selectedRentProvider){
    //    userHib.deleteUser(clientList.getSelectionModel().getSelectedItem());
    //}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        disableFields();

    }


}
