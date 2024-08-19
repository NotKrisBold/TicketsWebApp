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
    @JoinColumn(name = "user_id") // Specifica la colonna della chiave esterna
    private User author;

    private LocalDate time;

    private String content;

    @ManyToOne
    @JoinColumn(name = "ticket_id") // Specifica la colonna della chiave esterna
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id") // Specifica la colonna della chiave esterna
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> responses = new ArrayList<>();

    public void addReply(Comment reply) {
        reply.setParentComment(this);
        this.responses.add(reply);
    }
}
