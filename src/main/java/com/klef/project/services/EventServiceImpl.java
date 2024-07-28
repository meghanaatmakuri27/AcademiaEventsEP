package com.klef.project.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.klef.project.models.Event;
import com.klef.project.models.Student;


@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class EventServiceImpl implements EventService {

	@Override
	public String addEvent(Event event) {
		
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
	        EntityManager em = emf.createEntityManager();
	        em.getTransaction().begin();
	        em.persist(event);
	        em.getTransaction().commit();
	        em.close();
	        emf.close();
	         return "success";
	}

	@Override
	public String updateEvent(Event event) {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Event e = em.find(Event.class, event.getId());
        if (e != null) {
           e.setName(event.getName());
           e.setTime(event.getTime());
           e.setDescription(event.getDescription());
           e.setPic(event.getPic());
           e.setDate(event.getDate());
            em.getTransaction().commit();
        } else {
            em.getTransaction().rollback();
            em.close();
            return "event not found";
        }
        em.close();
        return "event Updated Successfully";
	}

	@Override
	public String deleteEvent(int eid) {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Event e= em.find(Event.class, eid);
        if (e != null) {
            em.remove(e);
            em.getTransaction().commit();
        } else {
            em.getTransaction().rollback();
            em.close();
            return "Event not found";
        }
        em.close();
        return "Event Deleted Successfully";
	}

	@Override
	public List<Event> viewallEvent() {
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
        List<Event> events = em.createQuery("SELECT e FROM Event e", Event.class).getResultList();
        em.close();
        return events;
	}

	@Override
	public Event viewEventById(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
		
		Event e = em.find(Event.class, id);
		
		if(e==null) {
			em.close();
			emf.close();
			
			return null;
		}
		em.close();
		emf.close();
		
		return e;
	}

}
