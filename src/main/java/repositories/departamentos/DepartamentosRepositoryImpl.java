package repositories.departamentos;

import java.util.List;
import java.util.Optional;

import dao.HibernateManager;
import exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import models.Departamento;

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
		Optional<Departamento> dep = Optional.ofNullable(hb.getEm().find(Departamento.class, id));
		hb.close();
		return dep;
	}

	@Override
	public Departamento save(Departamento dep) {
		HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();
        try {
            hb.getEm().merge(dep);
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
	public Boolean delete(Departamento dep) {
		HibernateManager hb = HibernateManager.getInstance();
        hb.open();
        hb.getTransaction().begin();
        try {
            dep = hb.getEm().find(Departamento.class, dep.getId());
            hb.getEm().remove(dep);
            hb.getTransaction().commit();
            hb.close();
            return true;
        } catch (Exception e) {
            throw new EmpleadoException("Error al eliminar empleado con id: " + dep.getId() + " - " + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
        }
	}

}
