package com.klef.project.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.klef.project.models.Enrolment;
import com.klef.project.models.Event;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class EnrolmentServiceImpl implements EnrolmentService {

  @Override
  public String addEnrolment(Enrolment e) {
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
          EntityManager em = emf.createEntityManager();
          em.getTransaction().begin();
          em.persist(e);
          em.getTransaction().commit();
          em.close();
          emf.close();
           return "success";
  }

  @Override
  public String updateEnrolment(Enrolment e) {
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
      EntityManager em = emf.createEntityManager();
          em.getTransaction().begin();
          Enrolment e1 = em.find(Enrolment.class, e.getId());
          if (e1 != null) {
             
              em.getTransaction().commit();
          } else {
              em.getTransaction().rollback();
              em.close();
              return "enrolment not found";
          }
          em.close();
          return "ernolment Updated Successfully";
  }

  @Override
  public String deleteEnrolment(Enrolment e) {
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
      EntityManager em = emf.createEntityManager();
          em.getTransaction().begin();
          Enrolment e1= em.find(Enrolment.class, e);
          if (e1 != null) {
              em.remove(e1);
              em.getTransaction().commit();
          } else {
              em.getTransaction().rollback();
              em.close();
              return "Enrolment not found";
          }
          em.close();
          return "Enrolment Deleted Successfully";
    
  }

  @Override
  public List<Enrolment> viewEnrolments() {
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
      EntityManager em = emf.createEntityManager();
          List<Enrolment> enrolments = em.createQuery("SELECT e FROM Enrolment e", Enrolment.class).getResultList();
          em.close();
          return enrolments;
  }

  @Override
  public Enrolment viewEnrolmentById(int id) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
    EntityManager em = emf.createEntityManager();
    
    Enrolment e = em.find(Enrolment.class, id);
    
    if(e==null) {
      em.close();
      emf.close();
      
      return null;
    }
    em.close();
    emf.close();
    
    return e;
  }

   public List<Enrolment> getEnrolmentsByStudentAndEvent(String studentId, int eventId) {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
          EntityManager em = emf.createEntityManager();
          try {
              TypedQuery<Enrolment> query = em.createQuery(
                  "SELECT e FROM Enrolment e WHERE e.studentid = :studentId AND e.eventid = :eventId", 
                  Enrolment.class);
              query.setParameter("studentId", studentId);
              query.setParameter("eventId", eventId);
              return query.getResultList();
          } finally {
              em.close();
          }
      }
  

}