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

import com.klef.project.models.Faculty;
import com.klef.project.models.Student;
import com.klef.project.services.FacultyService;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

@ManagedBean(name="facultybean", eager=true)

public class FacultyBean {
    @EJB(lookup="java:global/EPProject/FacultyServiceImpl!com.klef.project.services.FacultyService")
    FacultyService service;
    private String id;
    private String name;
    private String gender;
    private String department;
    private String email;
    private String password;
    private String contact;
    private String salary;
    private List<Faculty> facultylist;

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
    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }
    public List<Faculty> getFacultylist() {
        return service.viewallFaculty();
    }

    public String add() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Faculty f = new Faculty();
            f.setId(id);
            f.setName(name);
            f.setEmail(email);
            f.setDepartment(department);
            f.setContact(contact);
            f.setPassword(password);
            f.setGender(gender);
            f.setSalary(salary);

            service.addFaculty(f);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Faculty added successfully"));
            return "success"; // Navigate to success page or show success message
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred while adding the faculty. Please try again."));
            e.printStackTrace();
            return null; // Stay on the same page
        }
    }

    public String delete(String fid) {
        service.deleteFaculty(fid);
        return "faculty deleted successfully";
    }

    public String update() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            Faculty f = service.viewFacultyById(id);
            if (f != null) {
                Faculty f1 = new Faculty();
                f1.setEmail(email);
                f1.setId(id);
                f1.setName(name);
                f1.setPassword(password);
                f1.setGender(gender);
                f1.setDepartment(department);
                f1.setContact(contact);
                f1.setSalary(salary);

                service.updateFaculty(f1);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Faculty updated successfully"));
                return "success"; // Navigate to success page or show success message
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Faculty ID not found"));
                return "update failed"; // Stay on the same page
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred while updating the faculty. Please try again."));
            e.printStackTrace();
            return null; // Stay on the same page
        }
    }
    public void login() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        Faculty f = service.checkFacultylogin(email, password);
        if (f!= null) {
            HttpSession session = request.getSession();
            session.setAttribute("fac", f);
          response.sendRedirect("layout2.jsf");
        } else {
            response.sendRedirect("index.jsf");
        }
    }

}
