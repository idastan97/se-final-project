package kz.edu.nu.cs.Services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import kz.edu.nu.cs.Model.User;

class UserDbManager {
	private EntityManagerFactory emfactory;
	private EntityManager em;
	
	public void createUser(User user) {
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		em = emfactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
		emfactory.close();
	}


	
	public User getUserByEmail(String email) {
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		em = emfactory.createEntityManager();
		em.getTransaction().begin();
		User u;
		try {
			System.out.println("Befoooooooooooooooooooooore ");
			u = (User)em.createNamedQuery("User.findByEmail").setParameter("email", email).getSingleResult();
			System.out.println("afteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeer " + u);
		}catch(NoResultException e) {
			u = null;
		}
		em.getTransaction().commit();
		em.close();
		emfactory.close();
		return u;
	}
	public User getUserById(int id) {
		emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		em = emfactory.createEntityManager();
		em.getTransaction().begin();
		User u;
		try {
			u = (User)em.createNamedQuery("User.findById").setParameter("id", id).getSingleResult();
		}catch(NoResultException e) {
			u = null;
		}
		em.getTransaction().commit();
		em.close();
		emfactory.close();
		return u;
	}



}