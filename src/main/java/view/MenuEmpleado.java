package view;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import IO.IO;
import controller.Controller;
import exceptions.EmpleadoException;
import models.Departamento;
import models.Empleado;
import models.Proyecto;
import repositories.departamentos.DepartamentosRepository;
import repositories.departamentos.DepartamentosRepositoryImpl;

public class MenuEmpleado {
	
	public static void menuEmp(Controller controller) {
		while(true) {
			List<String> opciones = List.of("EMPLEADOS:\n1. VER", "2. BUSCAR", "3. AÑADIR","4. ELIMINAR","5. MODIFICAR","6. AÑADIR PROYECTO A EMPLEADO","7. ELIMINAR PROYECTO DE EMPLEADO", "0. VOLVER");
			IO.println(opciones);
			switch (IO.readString().charAt(0)) {
			case '1':
				verEmpleados(controller);
				break;
			case '2':
				searchEmpleado(controller);
				break;
			case '3':
				String tryAdd = (addEmpleado(controller) ? "Se pudo añadir empleado" : "No se pudo añadir el nuevo empleado");
				IO.println(tryAdd);
				break;
			case '4':
				String tryDelete = (deleteEmpleado(controller) ? "Se eliminó correctamente el empleado" : "No se pudo eliminar el empleado");
				IO.println(tryDelete);
				break;
			case '5':
				updateEmpleado(controller);
				break;
			case '6':
				addProyect(controller);
				break;
			case '7':
				deleteProyect(controller);
				break;
			case '0':
				Menu.main(null);
				break;
			}
		}
	}

	/**
	 * Método para ver todos los empleados de 'hib_empleados'
	 * @param controller
	 */
	private static void verEmpleados(Controller controller) {
		List<Empleado> empleados = controller.getEmpleados();
		for (Empleado empleado : empleados) {
			IO.println(empleado);
		}
	}
	
	/**
	 * Método para buscar departamento según su ID
	 * @param controller
	 */
	private static void searchEmpleado(Controller controller) {
		IO.print("ID del empleado a buscar");
		Integer id = IO.readInt();
		Optional<Empleado> e = controller.getEmpleadoById(id);
		if(e.isPresent())
			IO.println(e.get());
		else
			IO.println("No se ha encontrado un empleado con ID: " + id);
	}
	
	/**
	 * Método para añadir empleados a la BBDD
	 * @param controller
	 * @return
	 */
	private static Boolean addEmpleado(Controller controller) {
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
			DepartamentosRepository dR = new DepartamentosRepositoryImpl();
			Departamento d = dR.findById(idDep).orElse(null);
			if(d != null) {
				Empleado eJefe = new Empleado(nombre, salario, nacimiento, d);
				controller.getEmpleados().add(eJefe);				
				return controller.createEmpleado(eJefe);				
			} else {
				IO.println("No se encontró ningún departamento con la ID: " + idDep);
				return false;
			}			
		case 'N':
			Empleado e = new Empleado(nombre, salario, nacimiento);
			controller.getEmpleados().add(e);			
			return controller.createEmpleado(e);
		default:
			break;
		}
		return false;
	}
	
	/**
	 * Método para eliminar un empleado (si existe) según ID
	 * @param controller
	 * @return
	 */
	private static Boolean deleteEmpleado(Controller controller) {
		IO.print("¿Qué empleado desea eliminar? ID: ");
		Integer id = IO.readInt();
		Empleado e = new Empleado(id);
		return controller.deleteEmpleado(e);
	}
	
	/**
	 * Método para modificar los datos de un empleado
	 * @param controller
	 */
	private static void updateEmpleado(Controller controller) {
		IO.print("ID del empleado a modificar: ");
		Integer id = IO.readInt();
		IO.print("Nuevo nombre de empleado: ");
		String nombre = IO.readString();
		IO.print("Nuevo salario de empleado: ");
		Double salario = IO.readDouble();
		IO.print("Fecha de nacimiento: ");
		Date nacimiento = java.sql.Date.valueOf(IO.readLocalDate());
		IO.print("Departamento del empleado: ");
		Integer dep = IO.readInt();
		Optional<Departamento> newDep = controller.getDepartamentoById(dep);
		Empleado e = null;
		if(newDep.isEmpty())
			e = new Empleado(id, nombre, salario, nacimiento);
		else
			e = new Empleado(id, nombre, salario, nacimiento, newDep.get());
		if(controller.updateEmpleado(e))
			IO.println("Empelado modificado con éxito.");
		else
			throw new EmpleadoException("No se pudo modificar al empleado correctamente");
	}

	private static void addProyect(Controller controller) {
		IO.print("ID de empleado que entra en proyecto: ");
		Integer id = IO.readInt();
		IO.print("ID del proyecto al que entra: ");
		Integer pId = IO.readInt();
		if(id != null && pId != null) {
			if(controller.addPrToEmpl(new Proyecto(pId), new Empleado(id)))
				IO.println("Proyecto añadido con éxito");
			else
				IO.println("No se pudo añadir el proyecto");
		} else {
			IO.println("Los campos están vacíos");
		}
		
	}
	
	private static void deleteProyect(Controller controller) {
		IO.print("ID del empleado que sale de un proyecto: ");
		Integer id = IO.readInt();
		IO.print("ID del proyecto del que sale: ");
		Integer pId = IO.readInt();
		if(id != null && pId != null) {
			if(controller.delPrFromEmpl(new Proyecto(pId), new Empleado(id)))
				IO.println("Proyecto eliminado con éxito");
			else
				IO.println("No se pudo eliminar el proyecto");
		} else {
			IO.println("Los campos están vacíos");
		}
		
	}
}
