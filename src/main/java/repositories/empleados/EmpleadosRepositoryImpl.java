package repositories.empleados;

import java.util.List;
import java.util.Optional;

import IO.IO;
import dao.HibernateManager;
import exceptions.DepartamentoException;
import exceptions.EmpleadoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import models.Departamento;
import models.Empleado;
import models.Proyecto;

public class EmpleadosRepositoryImpl implements EmpleadosRepository{
	
	HibernateManager hb = HibernateManager.getInstance();
	
	@Override
	public List<Empleado> findAll() {
		hb.open();
		EntityManager em = hb.getEm();
		TypedQuery<Empleado> query = em.createNamedQuery("Empleado.findAll", Empleado.class);
		List<Empleado> list = query.getResultList();
		hb.close();
		return list;
	}

	@Override
	public Optional<Empleado> findById(Integer id) {
		hb.open();
		Optional<Empleado> emp = Optional.ofNullable(hb.getEm().find(Empleado.class, id));
		hb.close();
		return emp;
	}

	@Override
	public Boolean save(Empleado emp) {
        hb.open();
        hb.getTransaction().begin();
        try {
            hb.getEm().persist(emp);
            hb.getTransaction().commit();
            return true;
        } catch (Exception e) {
            throw new DepartamentoException("Error al guardar departamento con id: " + emp.getId() + "\n" + e.getMessage());
        } finally {
            if (hb.getTransaction().isActive()) {
                hb.getTransaction().rollback();
            }
            hb.close();
        }
	}

	@Override
	public Boolean delete(Empleado e) {
        hb.open();
        hb.getTransaction().begin();
        try {
        	Empleado eDelete = hb.getEm().find(Empleado.class, e.getId());
        	if(eDelete != null) {
        		hb.getEm().remove(eDelete);
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

	@Override
	public Boolean update(Empleado e) {
		hb.open();
		try {
			hb.getTransaction().begin();
			if(hb.getEm().find(Empleado.class, e.getId()) == null)
				throw new EmpleadoException("No se encontró al empleado");
			if(e.getDepartamento() != null && e.getDepartamento().getId() != null) {
				Departamento d = null;
				d = hb.getEm().find(Departamento.class, e.getDepartamento().getId());
				if(d != null) {
					if(d.getJefe().getId().equals(e.getId()))
						e.setDepartamento(d);
					else {
						e.setDepartamento(d);
						Empleado updateE = hb.getEm().find(Empleado.class, e.getId());
						Departamento updateD = updateE.getDepartamento();
						if(updateD != null) {
							updateD.setJefe(null);
							hb.getEm().merge(updateD);
							hb.getTransaction().commit();
							hb.getEm().clear();
							hb.getTransaction().begin();
						}
					}
				}
			} else {
				Empleado newE = hb.getEm().find(Empleado.class, e.getId());
				Departamento newD = newE.getDepartamento();
				if(newD != null) {
					newD.setJefe(null);
					hb.getEm().merge(newD);
					hb.getTransaction().commit();
					hb.getEm().clear();
					hb.getTransaction().begin();
				}
			}
			hb.getEm().merge(e);
			hb.getTransaction().commit();
			hb.close();
			return true;
		}catch (Exception ex) {
			throw new EmpleadoException("No se pudo modificar el empleado con ID: " + e.getId() + " - " + ex.getMessage());
		}
	}

	@Override
	public boolean addProyect(Proyecto p, Empleado e) {
		hb.open();
		hb.getTransaction().begin();
		try {
			p = hb.getEm().find(Proyecto.class, p.getId());
			e = hb.getEm().find(Empleado.class, e.getId());
			e.addProyecto(p);
			hb.getEm().merge(e);
			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception ex) {
			throw new EmpleadoException("No se pudo añadir el proyecto al empleado con ID: " + e.getId() + " - " + ex.getMessage());
		} finally {
			if(hb.getTransaction().isActive())
				hb.getTransaction().rollback();
			hb.close();
		}
	}
	
	@Override
	public boolean deleteProyect(Proyecto p, Empleado e) {
		hb.open();
		hb.getTransaction().begin();
		try {
			p = hb.getEm().find(Proyecto.class, p.getId());
			e = hb.getEm().find(Empleado.class, e.getId());
			e.removeProyecto(p);
			hb.getEm().merge(e);
			hb.getTransaction().commit();
			hb.close();
			return true;
		} catch (Exception ex) {
			throw new EmpleadoException("No se pudo eliminar el proyecto del empleado con ID: " + e.getId() + " - " + ex.getMessage());
		} finally {
			if(hb.getTransaction().isActive())
				hb.getTransaction().rollback();
			hb.close();
		}
	}
	
}
