package view;

import java.util.List;
import java.util.Optional;

import IO.IO;
import controller.Controller;
import models.Empleado;
import models.Proyecto;

public class MenuProyecto {
	public static void MenuProy(Controller controller) {
		while(true) {
			List<String> opciones = List.of("PROYECTOS:\n1. VER", "2. BUSCAR PROYECTO", "3. AÑADIR", "4. ELIMINAR", "5. MODIFICAR", "6. AÑADIR EMPLEADO A PROYECTO", "7. ELIMINAR EMPLEADO DE PROYECTO", "0. VOLVER");
			IO.println(opciones);
			switch (IO.readString().charAt(0)) {
			case '1':
				verProyectos(controller);
				break;
			case '2':
				searchProyecto(controller);
				break;
			case '3':
				String tryAdd = (addProyecto(controller) ? "Se pudo añadir departamento" : "No se pudo añadir el nuevo departamento");
				IO.println(tryAdd);
				break;
			case '4':
				String tryDelete = (deleteProyecto(controller) ? "Se eliminó correctamente el departamento" : "No se pudo eliminar el departamento");
				IO.println(tryDelete);
				break;
			case '5':
				updateProyecto(controller);
				break;
			case '6': 
				addEmpleado(controller);
				break;
			case '7':
				deleteEmpleado(controller);
				break;
			case '0':
				Menu.main(null);
				break;
			default:
				IO.println("Selecciona una opción válida");
			}
		}
	}

	/**
	 * Método para ver todos los proyectos en 'hib_proyectos'
	 * @param controller
	 */
	private static void verProyectos(Controller controller) {
		List<Proyecto> proyectos = controller.getProyectos();
		for (Proyecto proyecto : proyectos) {
			IO.println(proyecto);
		}
	}
	
	/**
	 * Método para buscar proyectos según ID
	 * @param controller
	 */
	private static void searchProyecto(Controller controller) {
		IO.print("ID del proyeto a buscar: ");
		Integer id = IO.readInt();
		Optional<Proyecto> p = controller.getProyectoById(id);
		if(p.isPresent())
			IO.print(p.get());
		else
			IO.print("No se ha encontrado un proyeto con ID: " + id);
	}
	
	/**
	 * Método para modificar proyectos
	 * @param controller
	 */
	private static void updateProyecto(Controller controller) {
		IO.print("ID del proyecto a modificar: ");
		Integer id = IO.readInt();
		IO.print("Nuevo nombre del proyecto");
		String nombre = IO.readString();
		Proyecto p = new Proyecto(id, nombre);
		if(controller.updateProyecto(p))
			IO.println("Proyecto modificado con éxito");
		else
			IO.println("No se pudo modificar el proyecto");
	}
	
	/**
	 * Método para añadir proyectos a la BBDD
	 * @param controller
	 * @return
	 */
	private static Boolean addProyecto(Controller controller) {
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
	 * Método para eliminar un proyecto (si existe) según ID
	 * @param controller
	 * @return
	 */
	private static Boolean deleteProyecto(Controller controller) {
		IO.print("¿Qué departamento desea eliminar? ID: ");
		Integer id = IO.readInt();
		Proyecto p = new Proyecto(id);
		return controller.deleteProyecto(p);
	}

	/**
	 *	Método que añade un empleado al proyecto indicado
	 * @param controller
	 */
	private static void addEmpleado(Controller controller) {
		IO.print("ID del proyecto que tendrá un nuevo empleado: ");
		Integer id = IO.readInt();
		IO.print("ID del empleado que será añadido: ");
		Integer eId = IO.readInt();
		if(id != null && eId != null) {
			if(controller.addEmplToProy(new Empleado(eId), new Proyecto(id)))
				IO.println("Empleado añadido con éxito al proyecto");
			else
				IO.println("No se pudo añadir el empleado al proyecto");
		} else {
			IO.println("Los campos están vacíos");
		}
	}
	
	/**
	 * Método que elimina un empleado del proyecto indicado
	 * @param controller
	 */
	private static void deleteEmpleado(Controller controller) {
		IO.print("ID del proyecto que tendrá un nuevo empleado: ");
		Integer id = IO.readInt();
		IO.print("ID del empleado que será añadido: ");
		Integer eId = IO.readInt();
		if(id != null && eId != null) {
			if(controller.delEmplFromProy(new Empleado(eId), new Proyecto(id)))
				IO.println("Empleado añadido con éxito al proyecto");
			else
				IO.println("No se pudo añadir el empleado al proyecto");
		} else {
			IO.println("Los campos están vacíos");
		}
		
	}
}
