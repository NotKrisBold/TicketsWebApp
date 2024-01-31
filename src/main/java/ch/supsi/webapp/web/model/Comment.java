package ch.supsi.webapp.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User author;
    private LocalDate time;
    private String content;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> responses;

}
