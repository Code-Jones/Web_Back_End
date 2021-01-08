package models;

import java.io.Serializable;

public class Note implements Serializable {

    private String title;
    private String content;

    public Note() {
    }

    public Note(String title, String conent) {
        this.title = title;
        this.content = conent;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}
