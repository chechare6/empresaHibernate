package repositories.departamentos;

import java.util.List;
import java.util.Optional;

import IO.IO;
import dao.HibernateManager;
import exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;

public class DepartamentosRepositoryImpl implements DepartamentosRepository {

	@Override
	public List<Departamento> findAll() {
		HibernateManager hb = HibernateManager.getInstance();
	    hb.open();
	    EntityManager em = hb.getEm();
	    TypedQuery<Departamento> query = em.createNamedQuery("Departamento.findAll", Departamento.class);
	    List<Departamento> list = query.getResultList();
	    hb.close();
	    return list;
	}
	@Override
	public Optional<Departamento> findById(Integer id) {
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		EntityManager em = hb.getEm();
		Optional<Departamento> dep = Optional.ofNullable(em.find(Departamento.class, id));
		hb.close();
		return dep;
	}

	@Override
	public Optional<Departamento> findByJefe(Empleado jefe){
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		EntityManager em = hb.getEm();
		TypedQuery<Departamento> query = em.createNamedQuery("Departamento.findByJefe", Departamento.class);
        query.setParameter("jefe", jefe);
        try {
            Departamento departamento = query.getSingleResult();
            return Optional.of(departamento); 
        } catch (EmpleadoException e) {
        	e.printStackTrace();
            return Optional.empty();
        }
	}
	
	@Override
	public Departamento save(Departamento dep) {
	    HibernateManager hb = HibernateManager.getInstance();
	    hb.open();
	    hb.getTransaction().begin();
	    try {
	        hb.getEm().persist(dep);
	        hb.getTransaction().commit();
	        hb.close();
	        return dep;
	    } catch (Exception e) {
	        throw new DepartamentoException("Error al guardar departamento con id: " + dep.getId() + "\n" + e.getMessage());
	    } finally {
	        if (hb.getTransaction().isActive()) {
	            hb.getTransaction().rollback();
	        }
	    }
	}


	@Override
	public Boolean delete(Departamento d) {
		HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();
        try {
            Departamento dDelete = hb.getEm().find(Departamento.class, d.getId());
            if(dDelete != null) {
            	hb.getEm().remove(dDelete);
            	hb.getTransaction().commit();
            	hb.close();            	
            	return true;
            } else {
            	IO.println("No existe ningún departamento con ID: " + d.getId());
            	return false;
			}
        } catch (Exception e) {
            throw new EmpleadoException("Error al eliminar empleado con id: " + d.getId() + " - " + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}

}
