package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.Comment;
import ch.supsi.webapp.web.model.Ticket;
import ch.supsi.webapp.web.repository.CommentRepository;
import ch.supsi.webapp.web.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public void saveReply(int ticketId, int parentCommentId, Comment reply) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        Comment parentComment = commentRepository.findById(parentCommentId).orElseThrow(() -> new RuntimeException("Parent comment not found"));

        reply.setTicket(ticket);
        parentComment.addReply(reply);

        commentRepository.save(reply);
    }
}
