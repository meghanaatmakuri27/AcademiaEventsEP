package com.klef.project.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.klef.project.models.Event;
import com.klef.project.models.Winner;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class WinnerServiceImpl implements WinnerService{

	@Override
	public void addWinner(Winner w) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(w);
        em.getTransaction().commit();
        em.close();
        emf.close();
         
		
	}

	@Override
	public List<Winner> viewWinners() {
		
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
			EntityManager em = emf.createEntityManager();
	        List<Winner> winners = em.createQuery("SELECT w FROM Winner w", Winner.class).getResultList();
	        em.close();
	        return winners;
	}

	@Override
	public void deleteWinner(int wid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Winner w = em.find(Winner.class, wid);
            if (w != null) {
                em.remove(w);
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
            }
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }


	@Override
	public Winner viewWinnerById(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
		
		Winner w = em.find(Winner.class, id);
		
		if(w==null) {
			em.close();
			emf.close();
			
			return null;
		}
		em.close();
		emf.close();
		
		return w;
	}

}
