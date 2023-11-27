package view;

import java.util.List;

import IO.IO;
import controller.Controller;
import models.Proyecto;

public class MenuProyecto {
	public static void MenuProy(Controller controller) {
		while(true) {
			List<String> opciones = List.of("PROYECTOS:\n1. VER", "2. AÑADIR", "3. ELIMINAR", "4. VOLVER");
			IO.println(opciones);
			switch (IO.readString().charAt(0)) {
			case '1':
				verProyectos(controller);
				break;
			case '2':
				String tryAdd = (addProyecto(controller) ? "Se pudo añadir departamento" : "No se pudo añadir el nuevo departamento");
				IO.println(tryAdd);
				break;
			case '3':
				String tryDelete = (deleteProyecto(controller) ? "Se eliminó correctamente el departamento" : "No se pudo eliminar el departamento");
				IO.println(tryDelete);
				break;
			case '4':
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
}
