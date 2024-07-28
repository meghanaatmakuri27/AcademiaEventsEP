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
import com.klef.project.models.Winner;
import com.klef.project.services.WinnerService;

@ManagedBean(name = "winnerbean", eager = true)
public class WinnerBean {
    @EJB(lookup = "java:global/EPProject/WinnerServiceImpl!com.klef.project.services.WinnerService")
    private WinnerService service;
    
    private int id;
    private String name;
    private String ename;
    private Part certificate;

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

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Part getCertificate() {
        return certificate;
    }

    public void setCertificate(Part certificate) {
        this.certificate = certificate;
    }

    public List<Winner> getWinnerslist() {
        return service.viewWinners();
    }

    public String add() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (service == null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Service is not initialized"));
                return null;
            }
            Winner winner = new Winner();
            winner.setName(name);
            winner.setEventname(ename);
            winner.setPic(convertImageToByteArray(certificate));

            service.addWinner(winner);
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
    public String getImageBase64(Winner w) {
        byte[] image = w.getPic();
        return image != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(image) : null;
    }

    // In EventBean.java
    public String delete(Integer id) {
        service.deleteWinner(id);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Event deleted successfully"));
        return "viewevents";
    }

    public void downloadImage(int eventId) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try {
            Winner event = service.viewWinnerById(eventId); // Assuming a method to get event by ID
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
