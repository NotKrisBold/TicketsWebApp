package ch.supsi.webapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
