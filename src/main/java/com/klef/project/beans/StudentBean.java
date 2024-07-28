package com.klef.project.beans;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.klef.project.models.Student;
import com.klef.project.services.StudentService;

@ManagedBean(name="studentbean", eager=true)
public class StudentBean {
    @EJB(lookup="java:global/EPProject/StudentServiceImpl!com.klef.project.services.StudentService")
    StudentService service;
    private String id;
    private String name;
    private String gender;
    private String department;
    private String email;
    private String password;
    private String contact;
    private List<Student> studentlist;

    // Getters and setters for all fields

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public List<Student> getStudentlist() {
        return service.viewallStudents();
    }

    public String add() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Student s = new Student();
           s.setId(id);
            s.setName(name);
            s.setEmail(email);
            s.setDepartment(department);
            s.setContact(contact);
            s.setPassword(password);
            s.setGender(gender);

            service.addStudent(s);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Student added successfully"));
            return "success"; // Navigate to success page or show success message
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred while adding the student. Please try again."));
            e.printStackTrace();
            return null; // Stay on the same page
        }
    }

    public String delete(String sid) {
        service.deleteStudent(sid);
        return "student deleted successfully";
    }

    public String update() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Student s = service.viewStudentById(id);
            if (s != null) {
                Student s1 = new Student();
                s1.setEmail(email);
                s1.setId(id);
                s1.setName(name);
                s1.setPassword(password);
                s1.setGender(gender);
                s1.setContact(contact);

                service.updateStudent(s1);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Student updated successfully"));
                return "success"; // Navigate to success page or show success message
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Student ID not found"));
                return "update failed"; // Stay on the same page
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred while updating the student. Please try again."));
            e.printStackTrace();
            return null; // Stay on the same page
        }
    }
    public void login() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        Student s = service.checkstudentlogin(email, password);
        if (s != null) {
            HttpSession session = request.getSession();
            session.setAttribute("stu", s);
          response.sendRedirect("home.jsf");
        } else {
            response.sendRedirect("loginfail.jsf");
        }
    }
    public boolean isStudentLoggedIn() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession session = (HttpSession) externalContext.getSession(true);
        Student student = (Student) session.getAttribute("stu");
        return student != null;
    }


}
