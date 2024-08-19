package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.Comment;
import ch.supsi.webapp.web.model.Ticket;
import ch.supsi.webapp.web.repository.CommentRepository;
import ch.supsi.webapp.web.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public void put(int ticketId, Integer parentCommentId, Comment comment) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        Comment parentComment = null;
        if(parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId).orElseThrow(() -> new RuntimeException("Parent comment not found"));
            parentComment.addReply(comment);
        }
        comment.setParentComment(parentComment);
        comment.setTicket(ticket);
        commentRepository.save(comment);
    }

    public void deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        deleteRecursive(comment);
    }

    private void deleteRecursive(Comment comment) {
        if (comment != null) {
            // Elimina ricorsivamente tutte le risposte di questo commento
            for (Comment response : new ArrayList<>(comment.getResponses())) {
                deleteRecursive(response);
            }
            // Rimuovi il commento dalla sua lista di commenti nel ticket
            if (comment.getTicket() != null) {
                comment.getTicket().getComments().remove(comment);
                ticketRepository.save(comment.getTicket());
            }
            // Elimina il commento stesso
            commentRepository.delete(comment);
        }
    }
}
