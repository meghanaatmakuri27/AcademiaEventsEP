package com.klef.project.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.klef.project.models.Admin;


@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
//@NamedQuery(name=)
public class AdminServiceImpl implements AdminService
{

  @Override
  public Admin checkadminlogin(String username, String password) 
  {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("epproject");
    EntityManager em = emf.createEntityManager();
    
    // a is an alias of Admin Class
    Query qry = em.createQuery("select a from Admin a where a.username=? and a.password=?  ");
    qry.setParameter(1, username);
    qry.setParameter(2, password);
    
        Admin admin = null;
        
        if(qry.getResultList().size()>0)
        {
          admin = (Admin) qry.getSingleResult();
        }
    em.close();
    emf.close();
    
    return admin;
  }

}