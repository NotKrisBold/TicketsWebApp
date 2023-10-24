package ch.supsi.webapp.web.service;


import ch.supsi.webapp.web.model.Ticket;
import ch.supsi.webapp.web.model.TicketStatus;
import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.repository.TicketRepository;
import ch.supsi.webapp.web.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        if(userRepository.findAll().size() == 0) {
            User admin = new User("admin");
            userRepository.save(admin);
        }

        if(ticketRepository.findAll().size() == 0) {
            Ticket ticket = new Ticket();
            ticket.setAuthor(userRepository.findById("admin").orElseThrow(NoSuchElementException::new));
            ticket.setStatus(TicketStatus.OPEN);
            ticket.setTitle("Login non funziona");
            ticket.setDescription("Da ieri sera non riesco più a loggarmi");
            ticket.setDate(new Date());
            post(ticket);
        }
    }

    public List<Ticket> getAll(){
        return ticketRepository.findAll();
    }

    public Ticket get(int id) {
        return ticketRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public Ticket post(Ticket ticket){
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setDate(new Date());
        return ticketRepository.save(ticket);
    }

    public Ticket put(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public boolean exists(int id) {
        return ticketRepository.existsById(id);
    }

    public boolean delete(int id){
        ticketRepository.deleteById(id);
        return !ticketRepository.existsById(id);
    }
}
