package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.dto.TicketDTO;
import ch.supsi.webapp.web.model.Attachment;
import ch.supsi.webapp.web.model.Comment;
import ch.supsi.webapp.web.model.Ticket;
import ch.supsi.webapp.web.service.CommentService;
import ch.supsi.webapp.web.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final TicketService ticketService;

    private final CommentService commentService;

    public MainController(TicketService service, CommentService commentService) {
        this.ticketService = service;
        this.commentService = commentService;
    }

    @ModelAttribute("servletPath")
    public String contextPath(final HttpServletRequest request) {
        return request.getServletPath();
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("tickets", ticketService.getAll());
        return "index-card";
    }

    @GetMapping("/home-table")
    public String grid(Model model){
        model.addAttribute("tickets", ticketService.getAll());
        return "index";
    }

    @GetMapping("/ticket/{id}")
    public String detail(@PathVariable int id, Model model){
        checkTicketExists(id);
        model.addAttribute("ticket", ticketService.get(id));
        return "detail";
    }

    @GetMapping("/ticket/new")
    public String newPost(Model model){
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("isNew", true);
        return "edit";
    }

    @PostMapping("/ticket/new")
    public String post(Ticket ticket, @RequestParam("file") MultipartFile file) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ticket.setAuthor(ticketService.findUserByUsername(user.getUsername()));
        setAttachment(ticket, file);
        ticket = ticketService.post(ticket);
        return "redirect:/ticket/"+ticket.getId();
    }

    private static void setAttachment(Ticket ticket, MultipartFile attachment) throws IOException {
        if(!attachment.isEmpty()) {
            ticket.setAttachment(Attachment.builder()
                    .bytes(attachment.getBytes())
                    .name(attachment.getOriginalFilename())
                    .contentType(attachment.getContentType())
                    .size(attachment.getSize())
                    .build()
            );
        }
    }

    @GetMapping("/ticket/{id}/edit")
    public String edit(@PathVariable int id, Model model){
        checkTicketExists(id);
        Ticket ticket = ticketService.get(id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("isNew", false);
        return "edit";
    }

    @PostMapping("/ticket/{id}/edit")
    public String put(@PathVariable int id, Ticket updatedTicket,  @RequestParam("file") MultipartFile file) throws IOException {
        Ticket ticket = ticketService.get(id);
        ticket.setType(updatedTicket.getType());
        ticket.setTitle(updatedTicket.getTitle());
        ticket.setType(updatedTicket.getType());
        ticket.setStatus(updatedTicket.getStatus());
        ticket.setDescription(updatedTicket.getDescription());
        setAttachment(ticket, file);
        ticketService.put(ticket);
        return "redirect:/ticket/{id}";
    }

    @GetMapping("/ticket/{id}/comment")
    public String comment(@PathVariable int id, Model model){
        checkTicketExists(id);
        Ticket ticket = ticketService.get(id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("isReply", false);
        return "comment";
    }

    @PostMapping("/ticket/{id}/comment")
    public String putComment(@PathVariable int id, @RequestParam("commentText") String commentText) throws IOException {
        Ticket ticket = ticketService.get(id);
        Comment comment = new Comment();
        comment.setTime(LocalDate.now());
        comment.setContent(commentText);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setAuthor(ticketService.findUserByUsername(user.getUsername()));
        ticket.addComment(comment);
        ticketService.put(ticket);
        commentService.put(id, null, comment);
        return "redirect:/ticket/{id}";
    }

    @GetMapping("/ticket/{id}/reply")
    public String replyForm(@PathVariable int id, Model model) {
        checkTicketExists(id);
        Ticket ticket = ticketService.get(id);
        model.addAttribute("ticket", ticket);
        model.addAttribute("isReply", true);
        return "comment";
    }

    @PostMapping("/ticket/{id}/reply")
    public String putReply(@PathVariable int id, @RequestParam("commentText") String commentText, @RequestParam("parentId") int parentId) throws IOException {
        Comment comment = new Comment();
        comment.setTime(LocalDate.now());
        comment.setContent(commentText);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setAuthor(ticketService.findUserByUsername(user.getUsername()));
        commentService.put(id, parentId, comment);
        return "redirect:/ticket/{id}";
    }

    @GetMapping("/ticket/{ticketId}/comment/delete")
    public String deleteComment(@PathVariable int ticketId, @RequestParam("commentId") int commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/ticket/" + ticketId;
    }

    @GetMapping(value = "/ticket/{id}/delete")
    public String detail(@PathVariable int id){
        ticketService.delete(id);
        return "redirect:/";
    }

    @GetMapping(value = "/ticket/{id}/attachment")
    @ResponseBody
    public ResponseEntity<byte[]> getAttachmentBytes(@PathVariable int id) {
        Attachment attachment = ticketService.get(id).getAttachment();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(attachment.getContentType()))
                .body(attachment.getBytes());
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/register")
    public String register(){
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(ch.supsi.webapp.web.model.User user){
        ticketService.postUser(user);
        return "redirect:/login";
    }

    @GetMapping("/ticket/search")
    @ResponseBody
    public List<TicketDTO> search(@RequestParam("q") String search, Model model){
        return ticketService.list(search).stream().map(TicketDTO::ticket2DTO).collect(Collectors.toList());
    }

    @PatchMapping("/ticket/{ticketId}/fastedit")
    public ResponseEntity<?> updateTicketField(
            @PathVariable int ticketId,
            @RequestParam String fieldName,
            @RequestParam String fieldValue) {

        Ticket ticket = ticketService.findById(ticketId);
        if (ticket == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found");
        }

        switch (fieldName) {
            case "title":
                ticket.setTitle(fieldValue);
                break;
            case "description":
                ticket.setDescription(fieldValue);
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid field name");
        }

        try {
            ticketService.put(ticket);
            return ResponseEntity.ok("Field updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating field");
        }
    }

    @GetMapping("/ticket/{ticketId}/fastedit")
    public String editTicket(@PathVariable int ticketId, Model model) {
        Ticket ticket = ticketService.findById(ticketId);
        if (ticket == null) {
            return "redirect:/home";
        }
        model.addAttribute("ticket", ticket);
        return "edit-ticket";
    }


    private void checkTicketExists(int id){
        if (!ticketService.exists(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
    }
}
