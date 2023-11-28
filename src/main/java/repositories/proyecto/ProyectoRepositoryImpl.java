package repositories.proyecto;

import java.util.List;
import java.util.Optional;

import IO.IO;
import dao.HibernateManager;
import exceptions.ProyectoException;
import jakarta.persistence.TypedQuery;
import models.Empleado;
import models.Proyecto;

public class ProyectoRepositoryImpl implements ProyectoRepository{

	HibernateManager hb = HibernateManager.getInstance();
	
	@Override
	public List<Proyecto> findAll() {
		hb.open();
		TypedQuery<Proyecto> query = hb.getEm().createNamedQuery("Proyecto.findAll", Proyecto.class);
		List <Proyecto> list = query.getResultList();
		hb.close();
		return list;
	}

	@Override
	public Optional<Proyecto> findById(Integer id) {
		hb.open();
		Optional <Proyecto> dep = Optional.ofNullable(hb.getEm().find(Proyecto.class, id));
		return dep;
	}

	@Override
	public Boolean save(Proyecto proy) {
		hb.open();
		hb.getTransaction().begin();
		try {
			hb.getEm().merge(proy);
			hb.getTransaction().commit();
			return true;
		} catch (Exception e) {
			throw new ProyectoException("Error al guardar el proyecto con id" + proy.getId() + "\n" + e.getMessage());
		} finally {
			if(hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
			hb.close();
		}
	}

	@Override
	public Boolean delete(Proyecto p) {
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

	@Override
	public Boolean update(Proyecto p) {
		hb.open();
		hb.getTransaction().begin();
		try {
			if(hb.getEm().find(Proyecto.class, p.getId()) == null)
				throw new ProyectoException("No se encontró el proyecto con ID: " + p.getId());
			Proyecto updateP = hb.getEm().find(Proyecto.class, p.getId());
			updateP.setNombre(p.getNombre());
			hb.getEm().merge(updateP);
			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception e) {
			throw new ProyectoException("Error al modificar el proyecto con ID: " + p.getId() + " - " + e.getMessage());
		} finally {
			if(hb.getTransaction().isActive())
				hb.getTransaction().rollback();
		}
	}
	
	@Override
	public boolean addEmpleado(Empleado e, Proyecto p) {
		hb.open();
		hb.getTransaction().begin();
		try {
			p = hb.getEm().find(Proyecto.class, p.getId());
			e = hb.getEm().find(Empleado.class, e.getId());
			p.addEmpleado(e);
			hb.getEm().merge(p);
			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception ex) {
			throw new ProyectoException("No se pudo añadir empleado al proyecto con ID: " + p.getId() + " - " + ex.getMessage());
		} finally {
			if(hb.getTransaction().isActive())
				hb.getTransaction().rollback();
			hb.close();
		}
	}
	
	@Override
	public boolean deleteEmpleado(Empleado e, Proyecto p) {
		hb.open();
		hb.getTransaction().begin();
		try {
			p = hb.getEm().find(Proyecto.class, p.getId());
			e = hb.getEm().find(Empleado.class, e.getId());
			p.removeEmpleado(e);
			hb.getEm().merge(p);
			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception ex) {
			throw new ProyectoException("No se pudo eliminar empleado del proyecto con ID: " + p.getId() + " - " + ex.getMessage());
		}
	}

}
