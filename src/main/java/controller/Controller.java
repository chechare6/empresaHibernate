package controller;

import java.util.*;
import models.*;
import repositories.departamentos.DepartamentosRepository;
import repositories.empleados.EmpleadosRepository;
import repositories.proyecto.ProyectoRepository;

public class Controller {
//	private final Logger logger = Logger.getLogger(Controller.class.getName());
	
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
//		logger.info("Obteniendo empleados");
		return empleadosRepository.findAll();
	}
	
	public Boolean createEmpleado(Empleado e) {
//		logger.info("Guardando empleado");
		return empleadosRepository.save(e);
	}
	
	public Optional<Empleado> getEmpleadoById(Integer id) {
//		logger.info("Obteniendo empleado con id: " + id);
		return empleadosRepository.findById(id);
	}
	
	public Boolean deleteEmpleado(Empleado e) {
		try {
			Optional<Empleado> empleadoOptional = empleadosRepository.findById(e.getId());
			if (empleadoOptional.isPresent()) {
				Empleado empleado = empleadoOptional.get();
				Optional<Departamento> departamentoOptional = departamentosRepository.findByJefe(empleado);
				if (departamentoOptional.isPresent()) {
					Departamento departamento = departamentoOptional.get();
					departamento.setJefe(null);
				}
//				logger.info("Borrando empleado con id: " + e.getId());
				return empleadosRepository.delete(e);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;			
	}
	
	//DEPARTAMENTOS
	public List<Departamento> getDepartamentos() {
//		logger.info("Obteniendo departamentos");
		return departamentosRepository.findAll();
	}
	
	public Boolean createDepartamento(Departamento d) {
//		logger.info("Guardando departamento");
		return departamentosRepository.save(d);
	}
	
	public Optional<Departamento> getDepartamentoById(Integer id) {
//		logger.info("Obteniendo empleado con id: " + id);
		return departamentosRepository.findById(id);
	}
	
	public Boolean deleteDepartamento(Departamento d) {
//		logger.info("Borrando departamento con id: "+ d.getId());
		return departamentosRepository.delete(d);
	}
	
	//PROYECTOS
	public List<Proyecto> getProyectos() {
//		logger.info("Obteniendo proyectos");
		return proyectoRepository.findAll();
	}
	
	public Boolean createProyecto(Proyecto p) {
//		logger.info("Guardando proyecto");
		return proyectoRepository.save(p);
	}
	
	public Optional<Proyecto> getProyectoById(Integer id) {
//		logger.info("Obteniendo proyecto con id: " + id);
		return proyectoRepository.findById(id);
	}
	
	public Boolean deleteProyecto(Proyecto p) {
//		logger.info("Borrando empleado con id: "+ p.getId());
		return proyectoRepository.delete(p);
	}
	
}
