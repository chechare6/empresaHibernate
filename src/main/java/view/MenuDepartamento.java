package view;

import java.util.List;

import IO.IO;
import controller.Controller;
import models.Departamento;
import models.Empleado;
import repositories.empleados.EmpleadosRepository;
import repositories.empleados.EmpleadosRepositoryImpl;

public class MenuDepartamento {

	public static void menuDep(Controller controller) {
		while(true) {
			List<String> opciones = List.of("DEPARTAMENTOS:\n1. VER", "2. AÑADIR", "3. ELIMINAR", "4. VOLVER");
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
				Menu.main(null);
				break;
			default:
				IO.println("Selecciona una opción válida");
			}
		}
	}
	
	/**
	 * Método para ver todos los departamentos de 'hib_departamentos'
	 * @param controller
	 */
	private static void verDepartamentos(Controller controller) {
		List<Departamento> departamentos = controller.getDepartamentos();
		for (Departamento departamento : departamentos) {
			IO.println(departamento);
		}
	}
	
	/**
	 * Método para añadir departamentos a la BBDD
	 * @param controller
	 * @return true si lo crea, false en caso contrario
	 */
	private static Boolean addDepartamento(Controller controller) {
		IO.print("Nombre del nuevo departamento: ");
		String nombre = IO.readString();
		IO.print("¿Tiene jefe el nuevo departamento? (Y/N): ");
		char jefe = IO.readString().toUpperCase().charAt(0);
		switch (jefe) {
		case 'Y':
			IO.print("¿Qué empleado es el jefe? ID: ");
			Integer idJefe = IO.readInt();
			EmpleadosRepository eR = new EmpleadosRepositoryImpl();
			Empleado e = eR.findById(idJefe).orElse(null);
			if(e != null) {
				Departamento d = new Departamento(nombre, e);
				controller.getDepartamentos().add(d);		
				return controller.createDepartamento(d);
			} else {
				IO.println("No se encontró ningún empleado con ID: " + idJefe);
				return false;
			}
		case 'N':
			Departamento d = new Departamento(nombre);
			controller.getDepartamentos().add(d);
			return controller.createDepartamento(d);
		default:
			IO.println("Por favor, introduzca los datos correctamente");
			break;
		}		
		return false;
	}

	/**
	 * Método que elimina un departamento (si existe) según ID
	 * @param controller
	 * @return
	 */
	private static Boolean deleteDepartamento(Controller controller) {
		IO.print("¿Qué departamento deseas eliminar? ID: ");
		Integer id = IO.readInt();
		Departamento d = new Departamento(id);
		return controller.deleteDepartamento(d);
	}
	
}
