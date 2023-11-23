package repositories.empleados;

import java.util.List;
import java.util.Optional;

import IO.IO;
import dao.HibernateManager;
import exceptions.DepartamentoException;
import exceptions.EmpleadoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import models.Empleado;

public class EmpleadosRepositoryImpl implements EmpleadosRepository{

	@Override
	public List<Empleado> findAll() {
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		EntityManager em = hb.getEm();
		TypedQuery<Empleado> query = em.createNamedQuery("Empleado.findAll", Empleado.class);
		List<Empleado> list = query.getResultList();
		hb.close();
		return list;
	}

	@Override
	public Optional<Empleado> findById(Integer id) {
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		Optional<Empleado> emp = Optional.ofNullable(hb.getEm().find(Empleado.class, id));
		hb.close();
		return emp;
	}

	@Override
	public Empleado save(Empleado emp) {
		HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();
        try {
            hb.getEm().persist(emp);
            hb.getTransaction().commit();
            hb.close();
            return emp;
        } catch (Exception e) {
            throw new DepartamentoException("Error al guardar departamento con id: " + emp.getId() + "\n" + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}

	@Override
	public Boolean delete(Empleado e) {
		HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();
        try {
        	Empleado eDelete = hb.getEm().find(Empleado.class, e.getId());
        	if(eDelete != null) {
        		hb.getEm().remove(e);
        		hb.getTransaction().commit();
        		hb.close();
        		return true;
        	} else {
        		IO.println("No existe empleado con ID: " + e.getId());
        		return false;
        	}
        } catch (Exception ex) {
            throw new EmpleadoException("Error al eliminar empleado con id: " + e.getId() + " - " + ex.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}

}
