package com.klef.project.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.klef.project.models.Faculty;
import com.klef.project.models.Student;
import javax.ejb.TransactionManagementType;
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class FacultyServiceImpl implements FacultyService {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");

    @Override
    public String addFaculty(Faculty faculty) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(faculty); 
        em.getTransaction().commit();
        em.close();
        return "Faculty Registered Successfully";
    }

    @Override
    public String updateFaculty(Faculty faculty) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Faculty s = em.find(Faculty.class, faculty.getId());
        if (s != null) {
            s.setContact(faculty.getContact());
            s.setName(faculty.getName());
            s.setEmail(faculty.getEmail());
            s.setDepartment(faculty.getDepartment());
            s.setGender(faculty.getGender());
            s.setPassword(faculty.getPassword());
            em.getTransaction().commit();
        } else {
            em.getTransaction().rollback();
            em.close();
            return "Faculty not found";
        }
        em.close();
        return "Faculty Updated Successfully";
    }

    @Override
    public String deleteFaculty(String fid) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Faculty s = em.find(Faculty.class, fid);
        if (s != null) {
            em.remove(s);
            em.getTransaction().commit();
        } else {
            em.getTransaction().rollback();
            em.close();
            return "Faculty not found";
        }
        em.close();
        return "Faculty Deleted Successfully";
    }

    @Override
    public List<Faculty> viewallFaculty() {
        EntityManager em = emf.createEntityManager();
        List<Faculty> faculties = em.createQuery("SELECT f FROM Faculty f", Faculty.class).getResultList();
        em.close();
        return faculties;
    }


    @Override
    public Faculty viewFacultyById(String id) {
        EntityManager em = emf.createEntityManager();
        Faculty faculty = em.find(Faculty.class, id);
        em.close();
        return faculty;
    }

	@Override
	public Faculty checkFacultylogin(String email, String password) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
		
		Query qry = em.createQuery("select f from Faculty f where f.email=? and f.password=?");
		qry.setParameter(1,email);
		qry.setParameter(2, password);
		
	Faculty fac=null;
		
		if(qry.getResultList().size()>0)
		{
		fac=(Faculty) qry.getSingleResult();	
		}
		return fac;

	}

	

}
