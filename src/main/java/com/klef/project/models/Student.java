package com.klef.project.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name="student_table")
public class Student implements Serializable {

  @Id 
  @Column(name="student_id", length=50)
  @Size(min = 10, max = 10, message = "ID must be exactly 10 characters long")
  private String id;

  @Column(name="student_name", nullable=false, length=50)
  private String name;

  @Column(name="student_gender", nullable=false, length=10)
  private String gender;

  @Column(name="student_department", nullable=false, length=50)
  private String department;

  @Column(name="student_email", nullable=false, length=50, unique=true)
  private String email;

  @Column(name="student_password", nullable=false, length=50)
  private String password;

  @Column(name="student_contact", nullable=false, length=20, unique=true)
  private String contact;

  // Getters and setters

  public String getId() {
    return id;
  }

  public void setId(String id2) {
    this.id = id2;
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
}
