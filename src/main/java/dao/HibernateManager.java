package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.Getter;

@Getter
public class HibernateManager {
	
	private static HibernateManager controller;
	private EntityManagerFactory emf;
	private EntityManager em;
	private EntityTransaction transaction;

	private HibernateManager() {

	}
	
	public static HibernateManager getInstance() {
		if(controller == null)
			controller = new HibernateManager();
		return controller;
	}
	
	public void open() {
		emf = Persistence.createEntityManagerFactory("empresaPersistence");
		em = emf.createEntityManager();
		transaction = em.getTransaction();
	}
	
	public void close() {
		em.close();
		emf.close();
	}
}
