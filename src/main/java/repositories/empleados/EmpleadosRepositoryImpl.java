package repositories.empleados;

import java.util.List;
import java.util.Optional;

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
            hb.getEm().merge(emp);
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
	public Boolean delete(Empleado emp) {
		HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        try {
            hb.getTransaction().begin();
            emp = hb.getEm().find(Empleado.class, emp.getId());
            hb.getEm().remove(emp);
            hb.getTransaction().commit();
            hb.close();
            return true;
        } catch (Exception e) {
            throw new EmpleadoException("Error al eliminar empleado con id: " + emp.getId() + " - " + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}

}
