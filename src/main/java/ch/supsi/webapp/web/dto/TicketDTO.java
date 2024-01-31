package ch.supsi.webapp.web.dto;

import ch.supsi.webapp.web.model.Comment;
import ch.supsi.webapp.web.model.Ticket;
import ch.supsi.webapp.web.model.TicketStatus;
import ch.supsi.webapp.web.model.TicketType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data @Builder
public class TicketDTO {
    private int id;
    private String title;
    private String description;
    private Date date;
    private String author;
    private TicketStatus status;
    private TicketType type;
    private List<Comment> comments;

    public static TicketDTO ticket2DTO(Ticket ticket){
        return TicketDTO.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .date(ticket.getDate())
                .author(ticket.getAuthor().getUsername())
                .status(ticket.getStatus())
                .type(ticket.getType())
                .comments(ticket.getComments())
                .build();
    }
}
