package view;

import java.util.Date;
import java.util.List;

import IO.IO;
import controller.Controller;
import models.Departamento;
import models.Empleado;
import repositories.departamentos.DepartamentosRepository;
import repositories.departamentos.DepartamentosRepositoryImpl;

public class MenuEmpleado {
	
	public static void menuEmp(Controller controller) {
		while(true) {
			List<String> opciones = List.of("EMPLEADOS:\n1. VER", "2. AÑADIR", "3. ELIMINAR", "4. VOLVER");
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
}
