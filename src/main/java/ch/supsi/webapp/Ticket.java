package ch.supsi.webapp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticket {
    private String title;
    private String description;
    private String author;

    @JsonCreator
    Ticket(@JsonProperty("title") String title, @JsonProperty("description") String description,
           @JsonProperty("author") String author) {
        this.title = title;
        this.description = description;
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return title + ", " + author + ", " + description;
    }
}
