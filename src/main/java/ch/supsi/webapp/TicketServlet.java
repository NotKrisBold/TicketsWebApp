package ch.supsi.webapp;

import java.io.*;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@WebServlet(value = "/tickets")
public class TicketServlet extends HttpServlet {
    private final ArrayList<Ticket> tickets = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String contentType = req.getContentType();

        if(contentType == null) {
            resp.getWriter().println("Impossibile creare il ticket - [contentType is NULL]");
            return;
        }

        Ticket toAdd;
        if(contentType.contains("application/json")){
            BufferedReader reader = req.getReader();
            StringBuilder jsonInput = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonInput.append(line);
            }

            // Parsa la stringa JSON in un oggetto Java utilizzando Jackson
            toAdd = mapper.readValue(jsonInput.toString(), Ticket.class);
        }
        else if(contentType.contains("application/x-www-form-urlencoded")){
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String author = req.getParameter("author");

            toAdd = new Ticket(title, description, author);
        }
        else {
            resp.getWriter().println("Impossibile creare il ticket - [unsupported contentType]");
            return;
        }

        tickets.add(toAdd);
        resp.getWriter().println("Il ticket è stato creato con successo!");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonString = mapper.writeValueAsString(tickets);
        resp.getWriter().println(jsonString);
    }
}
