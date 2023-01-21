package fxControllers;

import hibernate.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Comment;
import model.Equipment;
import model.User;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class LoginPage {
    public CheckBox isRentProvider;
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RentSystem");
    UserHib userHib = new UserHib(entityManagerFactory);
    GymHib gymHib = new GymHib(entityManagerFactory);
    CommentHib commentHib = new CommentHib(entityManagerFactory);
    OrdersHib ordersHib = new OrdersHib(entityManagerFactory);

    EquipmentHib equipmentHib = new EquipmentHib(entityManagerFactory);
    public TextField usernameField;
    public PasswordField passwordField;

    public void validate() throws IOException {
        User user = userHib.getUserByLoginData(usernameField.getText(), passwordField.getText(), isRentProvider.isSelected());
        if(user != null){
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/main-page.fxml"));
            Parent parent = fxmlLoader.load();
            MainPage mainPage = fxmlLoader.getController();
            mainPage.setData(entityManagerFactory, user, gymHib, equipmentHib, commentHib, ordersHib, isRentProvider);
            Scene scene = new Scene(parent);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setTitle("Rent system");
            stage.setScene(scene);
            stage.show();
        }else{
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User login report", "No such user or wrong credentials");
        }
    }

    public void openRegistration() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/registration-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }


}
