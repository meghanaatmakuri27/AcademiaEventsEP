package com.klef.project.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="faculty_table") // Updated table name to faculty_table
public class Faculty implements Serializable {
    @Id 
    @Column(name="faculty_id") // Updated column name to faculty_id
    @Size(min = 4, max = 4, message = "ID must be exactly 4 characters long")
    private String id;
    
    @Column(name="faculty_name", nullable=false, length=50)
    private String name;
    
    @Column(name="faculty_gender", nullable=false, length=10)
    private String gender;
    
    @Column(name="faculty_department", nullable=false, length=50)
    private String department;
    
    @Column(name="faculty_email", nullable=false, length=50, unique=true)
    private String email;
    
    @Column(name="faculty_password", nullable=false, length=50)
    private String password;
    
    @Column(name="faculty_contact", nullable=false, length=20, unique=true)
    private String contact;
    @Column(name="faculty_salary", nullable=false, length=20, unique=true)
    private String salary;
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
	public void setSalary(String salary2) {
		this.salary = salary2;
	}
    
    
    
}
