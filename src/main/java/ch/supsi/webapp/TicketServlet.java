package ch.supsi.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@WebServlet(value = "/tickets")
public class TicketServlet extends HttpServlet {
    private final ArrayList<Ticket> tickets = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String author = req.getParameter("author");

        Ticket newTicket = new Ticket(title, description, author);

        tickets.add(newTicket);

        resp.getWriter().println("Il ticket è stato creato con successo!");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        JSONArray ticketList = new JSONArray();

        for (Ticket ticket : tickets) {
            JSONObject ticketJSON = new JSONObject();
            ticketJSON.put("title", ticket.getTitle());
            ticketJSON.put("description", ticket.getDescription());
            ticketJSON.put("author", ticket.getAuthor());
            ticketList.add(ticketJSON);
        }

        PrintWriter out = resp.getWriter();
        out.print(ticketList.toJSONString());
        out.flush();
    }

}
