package view;

import java.util.Date;
import java.util.List;
import IO.IO;
import controller.Controller;
import dao.HibernateManager;
import models.Departamento;
import models.Empleado;
import models.Proyecto;
import repositories.departamentos.DepartamentosRepositoryImpl;
import repositories.empleados.EmpleadosRepositoryImpl;
import repositories.proyecto.ProyectoRepositoryImpl;

public class Menu {

	public static void main(String[] args) {
		// Iniciamos tablas BBDD
		initDataBase();
		// Creamos controlador
		Controller controller = new Controller(
				new EmpleadosRepositoryImpl(), 
				new DepartamentosRepositoryImpl(),
				new ProyectoRepositoryImpl());
		List<String> opciones;
		while(true) {
			opciones = List.of("BBDD Empresa ~ Hibernate:\n1. DEPARTAMENTOS", "2. EMPLEADOS", "3. PROYECTOS", "4. CERRAR");
			IO.println(opciones);
			int seleccionado = IO.readString().charAt(0);
			boolean inDep = true;
			switch (seleccionado) {
			case '1':
				while (inDep) {
					opciones = List.of("DEPARTAMENTOS:\n1. VER", "2. AÑADIR", "3. ELIMINAR", "4. VOLVER");
					IO.println(opciones);
					switch (IO.readString().charAt(0)) {
					case '1':
						verDepartamentos(controller);
						break;
					case '2':
						String tryAdd = (addDepartamento(controller) ? "Se pudo añadir departamento" : "No se pudo añadir el nuevo departamento");
						IO.println(tryAdd);
						break;
					case '3':
						String tryDelete = (deleteDepartamento(controller) ? "Se eliminó correctamente el departamento" : "No se pudo eliminar el departamento");
						IO.println(tryDelete);
						break;
					case '4':
						inDep = false;
						seleccionado = 0;
						break;
					}
				}
				break;
			case '2':
				while (inDep) {
					opciones = List.of("EMPLEADOS:\n1. VER", "2. AÑADIR", "3. ELIMINAR", "4. VOLVER");
					IO.println(opciones);
					switch (IO.readString().charAt(0)) {
					case '1':
						verEmpleados(controller);
						break;
					case '2':
						String tryAdd = (addEmpleado(controller) ? "Se pudo añadir empleado" : "No se pudo añadir el nuevo empleado");
						IO.println(tryAdd);
						break;
					case '3':
						String tryDelete = (deleteEmpleado(controller) ? "Se eliminó correctamente el empleado" : "No se pudo eliminar el empleado");
						IO.println(tryDelete);
						break;
					case '4':
						inDep = false;
						seleccionado = 0;
						break;
					}
				}
				break;
			case '3':
				while (inDep) {
					opciones = List.of("PROYECTOS:\n1. VER", "2. AÑADIR", "3. ELIMINAR", "4. VOLVER");
					IO.println(opciones);
					switch (IO.readString().charAt(0)) {
					case '1':
						verProyectos(controller);
						break;
					case '2':
						String tryAdd = (addProyecto(controller) ? "Se pudo añadir proyecto" : "No se pudo añadir el nuevo proyecto");
						IO.println(tryAdd);
						break;
					case '3':
						String tryDelete = (deleteProyecto(controller) ? "Se eliminó correctamente el proyecto" : "No se pudo eliminar el proyecto");
						IO.println(tryDelete);
						break;
					case '4':
						inDep = false;
						seleccionado = 0;
						break;
					}
				}
				break;
			case '4':
				IO.println("CERRANDO BBDD");
				return;
			}
		}
	}

	/**
	 * Método para ver todos los departamentos de 'hib_departamentos'
	 * @param controller
	 */
	public static void verDepartamentos(Controller controller) {
		List<Departamento> departamentos = controller.getDepartamentos();
		for (Departamento departamento : departamentos) {
			IO.println(departamento);
		}
	}
	
	/**
	 * Método para ver todos los empleados de 'hib_empleados'
	 * @param controller
	 */
	public static void verEmpleados(Controller controller) {
		List<Empleado> empleados = controller.getEmpleados();
		for (Empleado empleado : empleados) {
			IO.println(empleado);
		}
	}

	/**
	 * Método para ver todos los proyectos en 'hib_proyectos'
	 * @param controller
	 */
	public static void verProyectos(Controller controller) {
		List<Proyecto> proyectos = controller.getProyectos();
		for (Proyecto proyecto : proyectos) {
			IO.println(proyecto);
		}
	}

	/**
	 * Método para añadir departamentos a la BBDD
	 * @param controller
	 * @return
	 */
	public static Boolean addDepartamento(Controller controller) {
		IO.print("Nombre del nuevo departamento: ");
		String nombre = IO.readString();
		IO.print("¿Tiene jefe el nuevo departamento? (Y/N): ");
		char jefe = IO.readString().toUpperCase().charAt(0);
		switch (jefe) {
		case 'Y':
			//HAY JEFE
			return false;
		case 'N':
			Departamento d = new Departamento(nombre);
			controller.getDepartamentos().add(d);
			controller.createDepartamento(d);
			return true;
		default:
			IO.println("Por favor, introduzca los datos correctamente");
			break;
		}		
		return false;
	}

	/**
	 * Método para añadir empleados a la BBDD
	 * @param controller
	 * @return
	 */
	public static Boolean addEmpleado(Controller controller) {
		IO.print("Nombre del nuevo empleado: ");
		String nombre = IO.readString();
		IO.print("Salario del nuevo empleado: ");
		Double salario = IO.readDouble();
		IO.print("Fecha de nacimiento del nuevo empleado (yyyy-MM-dd): ");
		Date nacimiento = java.sql.Date.valueOf(IO.readLocalDate());
		IO.print("¿Se encuentra asignado a algún departamento? (Y/N): ");
		char departamento = IO.readString().toUpperCase().charAt(0);
		switch (departamento) {
		case 'Y':
			IO.print("¿En qué departamento trabaja el empleado? ID: ");
			Integer idDep = IO.readInt();
			Empleado eJefe = new Empleado(nombre, salario, nacimiento, idDep);
			controller.getEmpleados().add(eJefe);
			controller.createEmpleado(eJefe);
			return true;
		case 'N':
			Empleado e = new Empleado(nombre, salario, nacimiento);
			controller.getEmpleados().add(e);
			controller.createEmpleado(e);
			return true;
		default:
			break;
		}
		return false;
	}

	/**
	 * Método para añadir proyectos a la BBDD
	 * @param controller
	 * @return
	 */
	public static Boolean addProyecto(Controller controller) {
		IO.print("Nombre del nuevo proyecto: ");
		String nombre = IO.readString();
		try {
			Proyecto p = new Proyecto(nombre);
			controller.getProyectos().add(p);
			controller.createProyecto(p);
			return true;			
		} catch (Exception e) {
			IO.println("No se pudo crear el proyecto: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Método que elimina un departamento (si existe) según ID
	 * @param controller
	 * @return
	 */
	public static Boolean deleteDepartamento(Controller controller) {
		IO.print("¿Qué departamento deseas eliminar? ID: ");
		Integer id = IO.readInt();
		Departamento d = new Departamento(id);		
		return controller.deleteDepartamento(d);
	}
	
	/**
	 * Método para eliminar un empleado (si existe) según ID
	 * @param controller
	 * @return
	 */
	public static Boolean deleteEmpleado(Controller controller) {
		IO.print("¿Qué empleado desea eliminar? ID: ");
		Integer id = IO.readInt();
		Empleado e = new Empleado(id);
		return controller.deleteEmpleado(e);
	}
	
	/**
	 * Método para eliminar un proyecto (si existe) según ID
	 * @param controller
	 * @return
	 */
	public static Boolean deleteProyecto(Controller controller) {
		IO.print("¿Qué departamento desea eliminar? ID: ");
		Integer id = IO.readInt();
		Proyecto p = new Proyecto(id);
		return controller.deleteProyecto(p);
	}
	
	private static void initDataBase() {
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.close();
	}
}
