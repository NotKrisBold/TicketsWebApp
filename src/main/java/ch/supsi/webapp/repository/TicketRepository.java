package ch.supsi.webapp.repository;

import ch.supsi.webapp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findAllByOrderByDateDesc();
}