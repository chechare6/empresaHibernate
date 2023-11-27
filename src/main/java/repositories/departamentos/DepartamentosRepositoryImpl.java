package repositories.departamentos;

import java.util.List;
import java.util.Optional;
import dao.HibernateManager;
import exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;

public class DepartamentosRepositoryImpl implements DepartamentosRepository {
	HibernateManager hb = HibernateManager.getInstance();

	@Override
	public List<Departamento> findAll() {
		hb.open();
		EntityManager em = hb.getEm();
		TypedQuery<Departamento> query = em.createNamedQuery("Departamento.findAll", Departamento.class);
		List<Departamento> list = query.getResultList();
		hb.close();
		return list;
	}

	@Override
	public Optional<Departamento> findById(Integer id) {
		hb.open();
		EntityManager em = hb.getEm();
		Optional<Departamento> dep = Optional.ofNullable(em.find(Departamento.class, id));
		hb.close();
		return dep;
	}

	@Override
	public Optional<Departamento> findByJefe(Empleado jefe) {
		hb.open();
		TypedQuery<Departamento> query = hb.getEm().createNamedQuery("Departamento.findByJefe", Departamento.class);
		query.setParameter("jefe", jefe);
		try {
			Departamento departamento = query.getSingleResult();
			return Optional.of(departamento);
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public Boolean save(Departamento d) {
	    hb.open();
	    hb.getTransaction().begin();
	    try {
	    	Empleado jefe = null;
	    	if(d.getJefe() != null)
	    		jefe = hb.getEm().find(Empleado.class, d.getJefe().getId());
	    	d.setJefe(jefe);
	    	Departamento updateD = hb.getEm().merge(d);
	    	if(updateD.getJefe() != null) {
	    		Empleado updateJefe = updateD.getJefe();
	    		if(updateJefe.getDepartamento() != null && !updateJefe.getDepartamento().equals(updateD)) {
	    			updateJefe.getDepartamento().setJefe(null);
	    			hb.getEm().merge(updateJefe.getDepartamento());
	    		}
	    		updateJefe.setDepartamento(updateD);
	    		hb.getEm().merge(updateJefe);
	    	}
	    	hb.getTransaction().commit();
	    	return true;
	    } catch (Exception e) {
	        throw new DepartamentoException("Error al guardar departamento con id: " + d.getId() + "\n" + e.getMessage());
	    } finally {
	        if (hb.getTransaction().isActive()) {
	            hb.getTransaction().rollback();
	        }
	        hb.close();
	    }
	}


	@Override
	public Boolean delete(Departamento d) {
		hb.open();
		try {
			hb.getTransaction().begin();
			d = hb.getEm().find(Departamento.class, d.getId());
			if (d != null) {
				for (Empleado e : d.getEmpleados()) {
					e.setDepartamento(null);
					hb.getEm().merge(e);
				}
				hb.getTransaction().commit();
				hb.getEm().clear();
				hb.getTransaction().begin();
				d = hb.getEm().find(Departamento.class, d.getId());
				hb.getEm().remove(d);
				hb.getTransaction().commit();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new EmpleadoException("Error al eliminar empleado con id: " + d.getId() + " - " + e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
			hb.close();
		}
	}

}
