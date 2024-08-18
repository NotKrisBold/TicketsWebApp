package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.Comment;
import ch.supsi.webapp.web.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ticket")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{ticketId}/comment/{parentCommentId}/reply")
    public String saveReply(@PathVariable int ticketId, @PathVariable int parentCommentId, @ModelAttribute Comment reply) {
        commentService.saveReply(ticketId, parentCommentId, reply);
        return "redirect:/ticket/" + ticketId;
    }
}
