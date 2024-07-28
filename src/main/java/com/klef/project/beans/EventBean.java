package com.klef.project.beans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.klef.project.models.Event;
import com.klef.project.services.EventService;

@ManagedBean(name = "eventbean", eager = true)
public class EventBean {

    @EJB(lookup="java:global/EPProject/EventServiceImpl!com.klef.project.services.EventService")
    private EventService service;

    private int id;
    private String name;
    private String description;
    private String date;
    private String time;
    private Part eventImage;
    private String category;

    // Getter and setter for EventService
    public EventService getService() {
        return service;
    }

    public void setService(EventService service) {
        this.service = service;
    }

    // Getters and setters for other fields
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Part getEventImage() {
        return eventImage;
    }

    public void setEventImage(Part eventImage) {
        this.eventImage = eventImage;
    }

    public List<Event> getEventlist() {
        return service.viewallEvent();
    }

   
    public String add() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Event event = new Event();
            event.setName(name);
            event.setDescription(description);
            event.setDate(date);
            event.setTime(time);
			event.setCategory(category);
            event.setPic(convertImageToByteArray(eventImage));
            service.addEvent(event);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Event added successfully"));
            return "success"; // Navigate to success page or show success message
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred while adding the event. Please try again."));
            e.printStackTrace();
            return null; // Stay on the same page
        }
    }

    private byte[] convertImageToByteArray(Part eventImage) {
        try (InputStream input = eventImage.getInputStream()) {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            return output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get Base64 image representation
    public String getImageBase64(Event event) {
        byte[] image = event.getPic();
        return image != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(image) : null;
    }
 // In EventBean.java
    public String delete(Integer id) {
        service.deleteEvent(id); 
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Event deleted successfully"));
        return "viewevents"; 
    }
    public void downloadImage(int eventId) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try {
            Event event = service.viewEventById(eventId); // Assuming a method to get event by ID
            byte[] image = event.getPic();
            if (image != null) {
                response.reset();
                response.setContentType("image/jpeg"); // Change MIME type if needed
                response.setHeader("Content-Disposition", "attachment; filename=\"event_image.jpg\"");
                OutputStream output = response.getOutputStream();
                output.write(image);
                output.flush();
                output.close();
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Image not found.");
                context.addMessage(null, message);
            }
        } catch (IOException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred while downloading the image.");
            context.addMessage(null, message);
            e.printStackTrace();
        }
        context.responseComplete();
    }

}
