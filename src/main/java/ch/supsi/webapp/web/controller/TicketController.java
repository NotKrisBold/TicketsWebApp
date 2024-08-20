package ch.supsi.webapp.web.controller;

import org.springframework.ui.Model;
import ch.supsi.webapp.web.dto.Success;
import ch.supsi.webapp.web.dto.TicketDTO;
import ch.supsi.webapp.web.model.Ticket;
import ch.supsi.webapp.web.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<TicketDTO> list(){
        return service.getAll().stream().map(TicketDTO::ticket2DTO).collect(Collectors.toList());
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<TicketDTO> get(@PathVariable int id){
        if (!service.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Ticket ticket = service.get(id);
        return ticket != null ? new ResponseEntity<>(TicketDTO.ticket2DTO(ticket), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value="")
    public ResponseEntity<TicketDTO> post(@RequestBody Ticket ticket){
        ticket = service.post(ticket);
        return new ResponseEntity<>(TicketDTO.ticket2DTO(ticket), HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<TicketDTO> put(@PathVariable int id, @RequestBody Ticket ticket){
        if (!service.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ticket.setId(id);
        service.put(ticket);
        return new ResponseEntity<>(TicketDTO.ticket2DTO(ticket), HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Success> delete(@PathVariable int id){
        if (!service.exists(id)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        boolean remove = service.delete(id);
        if(remove) return new ResponseEntity<>(new Success(true), HttpStatus.OK);
        else return new ResponseEntity<>(new Success(false), HttpStatus.OK);
    }
}

