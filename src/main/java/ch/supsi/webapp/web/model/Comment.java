package ch.supsi.webapp.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private User author;

    private LocalDate time;

    private String content;

    @ManyToOne
    @JoinColumn(name = "ticket_id") // The column that will be used for the relationship
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> responses = new ArrayList<>();

    // Helper method to add a reply
    public void addReply(Comment reply) {
        reply.setParentComment(this);
        this.responses.add(reply);
    }
}
