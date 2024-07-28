package com.klef.project.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.klef.project.models.Student;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class StudentServiceImpl implements StudentService{

	
	public String addStudent(Student student) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(student); 
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		
		return "Student Registered Successfully";
		
	}

	@Override
	public String updateStudent(Student student) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Student s = em.find(Student.class, student.getId());
		s.setContact(student.getContact());
		s.setName(student.getName());
		s.setEmail(student.getEmail());
		s.setDepartment(s.getDepartment());
		s.setGender(student.getGender());
		s.setPassword(student.getPassword());
		em.getTransaction().commit();
		em.close();
		emf.close();
		
		return "Student Updated Successfully";
	}

	@Override
	public String deleteStudent(String sid) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Student s = em.find(Student.class, sid);
		em.remove(s);
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		
		return "Student Deleted Successfully";
	
	}

	@Override
	public List<Student> viewallStudents() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
		
		Query qry = em.createQuery("select s from Student s");
		// e is an alias of Employee Class
		List<Student> studentlist = qry.getResultList();
		
	    em.close();
	    emf.close();
	    
	    return studentlist;
		
	}

	@Override
	public Student viewStudentById(String sid) {
	
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
		
		Student s = em.find(Student.class, sid);
		
		if(s==null) {
			em.close();
			emf.close();
			
			return null;
		}
		em.close();
		emf.close();
		
		return s;
	}

	@Override
	public Student checkstudentlogin(String email, String password) 
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
		EntityManager em = emf.createEntityManager();
		
		Query qry = em.createQuery("select s from Student s where s.email=? and s.password=?");
		qry.setParameter(1,email);
		qry.setParameter(2, password);
		
		Student stu=null;
		
		if(qry.getResultList().size()>0)
		{
		stu=(Student) qry.getSingleResult();	
		}
		return stu;

		
	}

	
}


