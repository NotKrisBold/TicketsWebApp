package ch.supsi.webapp.web.model;

import jakarta.persistence.ManyToOne;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class User {
    @Id
    private String username;

    private String password;

    private String firstname;
    private String lastname;

    @ManyToOne
    Role role;

    public User(String username) {
        this.username = username;
    }

}
