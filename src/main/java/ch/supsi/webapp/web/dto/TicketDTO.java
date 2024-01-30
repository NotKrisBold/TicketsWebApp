package ch.supsi.webapp.web.dto;

import ch.supsi.webapp.web.model.Ticket;
import ch.supsi.webapp.web.model.TicketStatus;
import ch.supsi.webapp.web.model.TicketType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class TicketDTO {
    private int id;
    private String title;
    private String description;
    private Date date;
    private String author;
    private TicketStatus status;
    private TicketType type;

    public static TicketDTO ticket2DTO(Ticket ticket){
        return TicketDTO.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .date(ticket.getDate())
                .author(ticket.getAuthor().getUsername())
                .status(ticket.getStatus())
                .type(ticket.getType())
                .build();
    }
}
