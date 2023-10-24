package ch.supsi.webapp.web.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

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

}
