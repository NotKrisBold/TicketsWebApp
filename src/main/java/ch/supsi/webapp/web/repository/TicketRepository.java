package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByOrderByDateDesc();

    List<Ticket> findTop5ByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByDateDesc(String title, String description);

}