package ch.supsi.webapp.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of = {"id"}) @ToString
@Entity
public class Ticket {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	private Date date;

	@ManyToOne
	private User author;

	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	@Enumerated(EnumType.STRING)
	private TicketType type;

	@Embedded
	@JsonIgnore
	Attachment attachment;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	public void addComment(Comment comment){
		comments.add(0, comment);
	}
}
