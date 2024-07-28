package com.klef.project.services;

import java.util.List;
import com.klef.project.models.Enrolment;
import com.klef.project.models.Event;

public interface EnrolmentService {
    String addEnrolment(Enrolment e);
    String updateEnrolment(Enrolment e);
    String deleteEnrolment(Enrolment e);
    List<Enrolment> viewEnrolments();
    Enrolment viewEnrolmentById(int id);
    List<Enrolment> getEnrolmentsByStudentAndEvent(String studentId, int eventId);
 
}
