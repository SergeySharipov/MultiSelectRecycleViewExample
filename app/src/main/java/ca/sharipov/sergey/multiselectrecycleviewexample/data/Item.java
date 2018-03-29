package ca.sharipov.sergey.multiselectrecycleviewexample.data;

import java.util.Date;

public class Item {
    private String id;
    private String title;
    private Date date;

    Item(String id, String title) {
        this.id = id;
        this.title = title;
        date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
