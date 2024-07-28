package com.klef.project.models;

import java.io.Serializable;
import java.util.Base64;
import javax.persistence.*;

@Entity
@Table(name="event_table")
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="event_id")
    private int id;

    @Column(name="event_name")
    private String name;

    @Column(name="event_category")
    private String category;
    
    @Column(name="event_description")
    private String description;

    @Column(name="event_date")
    private String date;

    @Column(name="event_time")
    private String time;
    
    

    @Lob
    @Column(name ="img")
    private byte[] pic;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category=category;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public String getBase64Image() {
        if (pic != null) {
            return Base64.getEncoder().encodeToString(pic);
        }
        return null;
    }
}
