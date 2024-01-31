package ch.supsi.webapp.web.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User author;
    private Date time;
    private String content;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> responses;
}
