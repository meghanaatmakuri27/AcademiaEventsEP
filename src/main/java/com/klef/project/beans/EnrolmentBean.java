package com.klef.project.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.klef.project.models.Enrolment;
import com.klef.project.models.Event;
import com.klef.project.models.Student;
import com.klef.project.services.EnrolmentService;
import com.klef.project.services.EventService;
import com.klef.project.services.StudentService;

@ManagedBean(name = "enrolmentbean", eager = true)
@RequestScoped
public class EnrolmentBean {
    @EJB(lookup = "java:global/EPProject/EnrolmentServiceImpl!com.klef.project.services.EnrolmentService")
    private EnrolmentService service;
    
    @EJB(lookup = "java:global/EPProject/EventServiceImpl!com.klef.project.services.EventService")
    private EventService eservice;

    @EJB(lookup = "java:global/EPProject/StudentServiceImpl!com.klef.project.services.StudentService")
    private StudentService sservice;

    private int id;
    private String studentid;
    private int eventid;
    private String status;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public int getEventid() {
        return eventid;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Enrolment> getEnrolments() {
        return service.viewEnrolments();
    }

    public boolean isEnrolled(int eventId) {
        FacesContext context = FacesContext.getCurrentInstance();
        Student student = (Student) context.getExternalContext().getSessionMap().get("stu");
        if (student != null) {
            List<Enrolment> enrolments = service.getEnrolmentsByStudentAndEvent(student.getId(), eventId);
            return !enrolments.isEmpty();
        }
        return false;
    }

    public String add(int eventid) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Student student = (Student) context.getExternalContext().getSessionMap().get("stu");
            if (student != null) {
                if (isEnrolled(eventid)) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "You are already registered for this event."));
                    return null; // Stay on the same page
                }
                Event e1 = eservice.viewEventById(eventid);
                Student s1 = sservice.viewStudentById(student.getId());
                Enrolment e = new Enrolment();
                e.setEventid(eventid);
                e.setStudentid(student.getId()); // Set the student ID from session
                e.setStatus("Enrolled"); // Set status to "Enrolled" or another appropriate value
                e.setEventname(e1.getName());
                e.setStudentname(s1.getName());
                service.addEnrolment(e);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Enrolment added successfully"));
                return "success"; // Navigate to success page or show success message
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Student ID not found in session. Please log in again."));
                return null; // Stay on the same page
            }
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred while adding the enrolment. Please try again."));
            ex.printStackTrace();
            return null; // Stay on the same page
        }
    }
        public String delete(Enrolment e) {
            service.deleteEnrolment(e);
            return "enrolment deleted successfully";
        }
    
    }