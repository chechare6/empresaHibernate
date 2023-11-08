package dao;

import jakarta.persistence.*;

public class EmfSingleton {
	private static EmfSingleton ourInstance = new EmfSingleton();
	static private final String PERSISTENCE_UNIT_NAME = "empresaDB";
	private EntityManagerFactory emf = null;
	
	public static EmfSingleton getInstance() {
		return ourInstance;
	}
		
	public EntityManagerFactory getEmf() {
		if(this.emf == null)
			this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		return this.emf;
	}
}
