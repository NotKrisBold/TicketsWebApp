package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {}