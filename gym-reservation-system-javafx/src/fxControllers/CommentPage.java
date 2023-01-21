package fxControllers;

import hibernate.CommentHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Comment;
import model.Gym;
import model.User;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;

public class CommentPage {
    public ListView<Comment> commentList;
    private CommentHib commentHib;
    private EntityManagerFactory entityManagerFactory;
    private User user;
    private Gym gym;
    public void setData(EntityManagerFactory entityManagerFactory, User user, Gym gym, CommentHib commentHib) {
        this.entityManagerFactory = entityManagerFactory;
        this.gym = gym;
        this.user = user;
        this.commentHib = commentHib;
        fillList();
    }

    private void fillList() {
        List<Comment> comments;
        comments = gym.getComments();
        comments.forEach(g->commentList.getItems().add(g));
    }

    public void addComment(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/comment-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        CommentCreationPage commentCreationPage = fxmlLoader.getController();
        commentCreationPage.setData(gym, user);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initOwner(commentList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }

    public void updComment(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../view/comment-creation-page.fxml"));
        Parent parent = fxmlLoader.load();
        CommentCreationPage commentCreationPage = fxmlLoader.getController();
        commentCreationPage.setData(entityManagerFactory, user, commentList.getSelectionModel().getSelectedItem());
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        //stage.initOwner(clientList.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle("Rent system");
        stage.setScene(scene);
        stage.show();
    }

    public void delComment(ActionEvent actionEvent) {
        commentHib.deleteComment(commentList.getSelectionModel().getSelectedItem());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Comment delete report", "Comment " + commentList.getSelectionModel().getSelectedItem().getTitle() + " deleted successfully");

    }
}
