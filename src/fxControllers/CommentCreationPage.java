package fxControllers;

import hibernate.CommentHib;
import hibernate.EquipmentHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Comment;
import model.Equipment;
import model.Gym;
import model.User;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CommentCreationPage {
    @FXML
    public TextField commentNameField;
    @FXML
    public TextField commentTextField;
    public Button actionButton;
    private Gym gym;
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("RentSystem");
    private User user;
    private Comment selectedComment;
    private CommentHib commentHib;

    public void setData(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
        this.commentHib = new CommentHib(entityManagerFactory);
    }

    public void createNewComment(ActionEvent actionEvent) {
        setData(entityManagerFactory);
        Comment comment = new Comment(commentNameField.getText(),commentTextField.getText(), gym, user);
        commentHib.createComment(comment);
    }
    public void setData(Gym gym, User user){
        this.gym = gym;
        this.user = user;
    }

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser, Comment selectedComment) {
        this.user = currentUser;
        this.entityManagerFactory = entityManagerFactory;
        this.selectedComment = selectedComment;
        this.commentHib = new CommentHib(entityManagerFactory);

        fillFields();
    }
    private void fillFields() {
        Comment comment = (Comment) commentHib.getCommentById(selectedComment.getId());
        commentNameField.setText(comment.getTitle());
        commentTextField.setText(comment.getCommentText());

        actionButton.setOnAction(actionEvent -> {
            updateComment(comment);
        });
        actionButton.setText("Update comment");
    }
    public void updateComment(Comment comment){
        comment.setTitle(commentNameField.getText());
        comment.setCommentText(commentTextField.getText());
        commentHib.updateComment(comment);
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Comment update report", "Comment " + comment.getTitle() + " updated successfully");
    }

}
