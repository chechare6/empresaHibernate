package controller;

import java.util.*;
import java.util.logging.Logger;

import models.*;
import repositories.departamentos.DepartamentosRepository;
import repositories.empleados.EmpleadosRepository;
import repositories.proyecto.ProyectoRepository;

public class Controller {
	private final Logger logger = Logger.getLogger(Controller.class.getName());
	
	//DEPENDENCIAS
	private final EmpleadosRepository empleadosRepository;
	private final DepartamentosRepository departamentosRepository;
	private final ProyectoRepository proyectoRepository;
	
	public Controller(EmpleadosRepository empleadosRepository, DepartamentosRepository departamentosRepository, ProyectoRepository proyectoRepository) {
		this.empleadosRepository = empleadosRepository;
		this.departamentosRepository = departamentosRepository;
		this.proyectoRepository = proyectoRepository;
	}
	
	//EMPLEADOS	
	public List<Empleado> getEmpleados() {
		logger.info("Obteniendo empleados");
		return empleadosRepository.findAll();
	}
	
	public Empleado createEmpleado(Empleado empleado) {
		logger.info("Guardando empleado");
		return empleadosRepository.save(empleado);
	}
	
	public Optional<Empleado> getEmpleadoById(Integer id) {
		logger.info("Obteniendo empleado con id: " + id);
		return empleadosRepository.findById(id);
	}
	
	public Boolean deleteEmpleado(Empleado empleado) {
		logger.info("Borrando empleado con id: "+ empleado.getId());
		return empleadosRepository.delete(empleado);
	}
	
	//DEPARTAMENTOS
	public List<Departamento> getDepartamentos() {
		logger.info("Obteniendo departamentos");
		return departamentosRepository.findAll();
	}
	
	public Departamento createDepartamento(Departamento departamento) {
		logger.info("Guardando departamento");
		return departamentosRepository.save(departamento);
	}
	
	public Optional<Departamento> getDepartamentoById(Integer id) {
		logger.info("Obteniendo empleado con id: " + id);
		return departamentosRepository.findById(id);
	}
	
	public Boolean deleteDepartamento(Departamento departamento) {
		logger.info("Borrando departamento con id: "+ departamento.getId());
		return departamentosRepository.delete(departamento);
	}
	
	//PROYECTOS
	public List<Proyecto> getProyectos() {
		logger.info("Obteniendo proyectos");
		return proyectoRepository.findAll();
	}
	
	public Proyecto createProyecto(Proyecto proyecto) {
		logger.info("Guardando proyecto");
		return proyectoRepository.save(proyecto);
	}
	
	public Optional<Proyecto> getProyectoById(Integer id) {
		logger.info("Obteniendo proyecto con id: " + id);
		return proyectoRepository.findById(id);
	}
	
	public Boolean deleteProyecto(Proyecto proyecto) {
		logger.info("Borrando empleado con id: "+ proyecto.getId());
		return proyectoRepository.delete(proyecto);
	}
	
}
