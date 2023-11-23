package repositories.proyecto;

import java.util.List;
import java.util.Optional;

import IO.IO;
import dao.HibernateManager;
import exceptions.ProyectoException;
import jakarta.persistence.TypedQuery;
import models.Proyecto;

public class ProyectoRepositoryImpl implements ProyectoRepository{

	@Override
	public List<Proyecto> findAll() {
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		TypedQuery<Proyecto> query = hb.getEm().createNamedQuery("Proyecto.findAll", Proyecto.class);
		List <Proyecto> list = query.getResultList();
		hb.close();
		return list;
	}

	@Override
	public Optional<Proyecto> findById(Integer id) {
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		Optional <Proyecto> dep = Optional.ofNullable(hb.getEm().find(Proyecto.class, id));
		return dep;
	}

	@Override
	public Proyecto save(Proyecto proy) {
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.getTransaction().begin();
		try {
			hb.getEm().merge(proy);
			hb.getTransaction().commit();
			hb.close();
			return proy;
		} catch (Exception e) {
			throw new ProyectoException("Error al guardar el proyecto con id" + proy.getId() + "\n" + e.getMessage());
		} finally {
			if(hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
	}

	@Override
	public Boolean delete(Proyecto p) {
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.getTransaction().begin();
		try {
			Proyecto pDelete = hb.getEm().find(Proyecto.class, p.getId());
			if(pDelete != null) {
				hb.getEm().remove(pDelete);
				hb.getTransaction().commit();
				hb.close();
				return true;
			} else {
				IO.println("No existe nigún proyecto con la ID: " + p.getId());
				return false;
			}
		} catch (Exception e) {
			throw new ProyectoException("Error al eliminar el proyecto con id" + p.getId() + "\n" + e.getMessage());
		} finally {
			if(hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
	}

}
