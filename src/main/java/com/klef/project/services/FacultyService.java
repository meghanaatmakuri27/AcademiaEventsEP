package com.klef.project.services;

import java.util.List;

import javax.ejb.Remote;

import com.klef.project.models.Faculty;
import com.klef.project.models.Student;

@Remote
public interface FacultyService {
public String addFaculty(Faculty faculty);
public String updateFaculty(Faculty faculty);
public String deleteFaculty(String fid);
public List<Faculty> viewallFaculty();

public Faculty checkFacultylogin(String id,String password);
public Faculty viewFacultyById(String id);
}
