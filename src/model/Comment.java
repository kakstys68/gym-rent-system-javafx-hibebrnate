package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User user;
    private String title;
    private String commentText;
    @ManyToOne
    private Gym gym;
    //@OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Comment> replies;
    //@ManyToOne
    //private Comment parentComment;

    public Comment(String title, String commentText, Gym gym, User user) {
        this.title = title;
        this.commentText = commentText;
        this.gym = gym;
        this.user = user;
    }

    @Override
    public String toString() {
        return user.getUsername() + " -> " + title + ": " + commentText;
    }


}
