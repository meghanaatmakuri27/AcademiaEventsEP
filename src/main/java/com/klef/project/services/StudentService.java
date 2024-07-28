package com.klef.project.services;

import java.util.List;

import javax.ejb.Remote;

import com.klef.project.models.Student;

@Remote
public interface StudentService {
public String addStudent(Student student);
public String updateStudent(Student student);
public String deleteStudent(String sid);
public List<Student> viewallStudents();

public Student checkstudentlogin(String email,String password);
public Student viewStudentById(String id);
}
