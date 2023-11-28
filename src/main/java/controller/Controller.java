package controller;

import java.util.*;
import java.util.logging.Logger;

import models.*;
import repositories.departamentos.DepartamentosRepository;
import repositories.empleados.EmpleadosRepository;
import repositories.proyecto.ProyectoRepository;

public class Controller {
	private final Logger logger = Logger.getLogger(Controller.class.getName());

	// DEPENDENCIAS
	private final EmpleadosRepository empleadosRepository;
	private final DepartamentosRepository departamentosRepository;
	private final ProyectoRepository proyectoRepository;

	public Controller(EmpleadosRepository empleadosRepository, DepartamentosRepository departamentosRepository,
			ProyectoRepository proyectoRepository) {
		this.empleadosRepository = empleadosRepository;
		this.departamentosRepository = departamentosRepository;
		this.proyectoRepository = proyectoRepository;
	}

	// EMPLEADOS
	public List<Empleado> getEmpleados() {
		logger.info("Obteniendo empleados");
		return empleadosRepository.findAll();
	}

	public Boolean createEmpleado(Empleado e) {
		logger.info("Guardando empleado");
		return empleadosRepository.save(e);
	}

	public Optional<Empleado> getEmpleadoById(Integer id) {
		logger.info("Obteniendo empleado con id: " + id);
		return empleadosRepository.findById(id);
	}

	public Boolean deleteEmpleado(Empleado e) {
		 logger.info("Borrando empleado con id: " + e.getId());
		return empleadosRepository.delete(e);
	}

	public boolean updateEmpleado(Empleado e) {
		logger.info("Modificando empleado con id: " + e.getId());
		return empleadosRepository.update(e);
	}

	// DEPARTAMENTOS
	public List<Departamento> getDepartamentos() {
		logger.info("Obteniendo departamentos");
		return departamentosRepository.findAll();
	}

	public boolean createDepartamento(Departamento d) {
		logger.info("Guardando departamento");
		return departamentosRepository.save(d);
	}

	public Optional<Departamento> getDepartamentoById(Integer id) {
		logger.info("Obteniendo empleado con id: " + id);
		return departamentosRepository.findById(id);
	}

	public boolean deleteDepartamento(Departamento d) {
		logger.info("Borrando departamento con id: "+ d.getId());
		return departamentosRepository.delete(d);
	}

	public boolean updateDepartamento(Departamento d) {
		logger.info("Modificando departamento con id: "+ d.getId());
		return departamentosRepository.update(d);
	}

	public boolean addPrToEmpl(Proyecto p, Empleado e) {
		logger.info("Añadiendo un proyecto al empleado con ID: " + e.getId());
		return empleadosRepository.addProyect(p, e);
	}
	
	public boolean delPrFromEmpl(Proyecto p, Empleado e) {
		logger.info("Eliminando un proyecto del empleado con ID: " + e.getId());
		return empleadosRepository.deleteProyect(p, e);
	}

	// PROYECTOS
	public List<Proyecto> getProyectos() {
		logger.info("Obteniendo proyectos");
		return proyectoRepository.findAll();
	}

	public boolean createProyecto(Proyecto p) {
		logger.info("Guardando proyecto");
		return proyectoRepository.save(p);
	}

	public Optional<Proyecto> getProyectoById(Integer id) {
		logger.info("Obteniendo proyecto con id: " + id);
		return proyectoRepository.findById(id);
	}

	public boolean deleteProyecto(Proyecto p) {
		logger.info("Borrando proyecto con id: "+ p.getId());
		return proyectoRepository.delete(p);
	}
	
	public boolean updateProyecto(Proyecto p) {
		logger.info("Modificando proyecto con id: " + p.getId());
		return proyectoRepository.update(p);
	}

	public boolean addEmplToProy(Empleado e, Proyecto p) {
		logger.info("Añadiendo un empleado al proyecto con ID: " + p.getId());
		return proyectoRepository.addEmpleado(e, p);
	}
	
	public boolean delEmplFromProy(Empleado e, Proyecto p) {
		logger.info("Eliminando un empleado del proyecto con ID: " + p.getId());
		return proyectoRepository.deleteEmpleado(e, p);
	}

}
