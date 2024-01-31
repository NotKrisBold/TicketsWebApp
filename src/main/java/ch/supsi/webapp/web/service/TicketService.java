package ch.supsi.webapp.web.service;


import ch.supsi.webapp.web.model.*;
import ch.supsi.webapp.web.repository.RoleRepository;
import ch.supsi.webapp.web.repository.TicketRepository;
import ch.supsi.webapp.web.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        if(roleRepository.findAll().isEmpty()) {
            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleUser = new Role("ROLE_USER");
            roleRepository.save(roleAdmin);
            roleRepository.save(roleUser);
        }

        if(userRepository.findAll().isEmpty()) {
            User admin = new User("admin", encoder.encode("admin"), "Amministratore", "Unico", roleRepository.getReferenceById("ROLE_ADMIN"));
            userRepository.save(admin);
        }

        if(ticketRepository.findAll().isEmpty()) {
            Ticket ticket = new Ticket();
            ticket.setAuthor(userRepository.getReferenceById("admin"));
            ticket.setStatus(TicketStatus.OPEN);
            ticket.setTitle("Login non funziona");
            ticket.setDescription("Da ieri sera non riesco più a loggarmi");
            ticket.setType(TicketType.BUG);
            ticket.setDate(new Date());
            ticket.setComments(new ArrayList<>());
            post(ticket);
            ticket = new Ticket();
            ticket.setAuthor(userRepository.getReferenceById("admin"));
            ticket.setStatus(TicketStatus.OPEN);
            ticket.setTitle("Schermata bianca");
            ticket.setDescription("Quando apro l'applicativo ho una schermata bianca");
            ticket.setType(TicketType.BUG);
            ticket.setDate(new Date());
            ticket.setComments(new ArrayList<>());
            post(ticket);
        }
    }

    public List<Ticket> getAll(){
        return ticketRepository.findAll();
    }

    public Ticket get(int id){
        return ticketRepository.findById(id).get();
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

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void postUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(roleRepository.findById("ROLE_USER").get());
        userRepository.save(user);
    }

    public List<Ticket> list(String search) {
        return ticketRepository.findTop5ByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByDateDesc(search, search);
    }


}
