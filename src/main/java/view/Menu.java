package view;

import java.util.List;
import IO.IO;
import controller.Controller;
import dao.EmfSingleton;
import dao.HibernateManager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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
		Controller controller = new Controller(new EmpleadosRepositoryImpl(), new DepartamentosRepositoryImpl(),
				new ProyectoRepositoryImpl());
		EmfSingleton emfS = EmfSingleton.getInstance();
		EntityManagerFactory emf = emfS.getEmf();
		EntityManager em = emf.createEntityManager();
		List<String> opciones;
		while(true) {
			opciones = List.of("BBDD Empresa ~ Hibernate:\n1. DEPARTAMENTOS", "2. EMPLEADOS", "3. PROYECTOS", "4. CERRAR");
			System.out.println(opciones);
			int seleccionado = IO.readString().charAt(0);
			boolean inDep = true;
			switch (seleccionado) {
			case '1':
				while (inDep) {
					opciones = List.of("DEPARTAMENTOS:\n1. VER", "2. AÑADIR", "3. ELIMINAR", "4. VOLVER");
					System.out.println(opciones);
					switch (IO.readString().charAt(0)) {
					case '1':
						verDepartamentos(controller);
						break;
					case '2':
						String tryAdd = (addDepartamento(controller) ? "Se pudo añadir departamento" : "No se pudo añadir el nuevo departamento");
						System.out.println(tryAdd);
						break;
					case '3':
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
					System.out.println(opciones);
					switch (IO.readString().charAt(0)) {
					case '1':
						verEmpleados(controller);
						break;
					case '2':
						System.out.println("METODO addEmpleado()");
						break;
					case '3':
						System.out.println("METODO deleteEmpleado()");
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
					System.out.println(opciones);
					switch (IO.readString().charAt(0)) {
					case '1':
						verProyectos(controller);
						break;
					case '2':
						System.out.println("METODO addProyecto()");
						break;
					case '3':
						System.out.println("METODO deleteProyecto()");
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

	public static void verDepartamentos(Controller controller) {
		List<Departamento> departamentos = controller.getDepartamentos();
		for (Departamento departamento : departamentos) {
			System.out.println(departamento);
		}
	}

	public static void verEmpleados(Controller controller) {
		List<Empleado> empleados = controller.getEmpleados();
		for (Empleado empleado : empleados) {
			System.out.println(empleado);
		}
	}

	public static void verProyectos(Controller controller) {
		List<Proyecto> proyectos = controller.getProyectos();
		for (Proyecto proyecto : proyectos) {
			System.out.println(proyecto);
		}
	}

	/**
	 * Método para añadir departamentos a la BBDD
	 * 
	 * @param d
	 */
	public static Boolean addDepartamento(Controller controller) {
		IO.print("Nombre del nuevo departamento: ");
		String nombre = IO.readString();
		IO.print("Tiene jefe el nuevo departamento (Y/N): ");
		char jefe = IO.readString().toUpperCase().charAt(0);
		switch (jefe) {
		case 'Y':
			//HAY JEFE
			return false;
		case 'N':
			Departamento d = new Departamento(nombre);
			controller.createDepartamento(d);
			controller.getDepartamentos().add(d);
			return true;
		default:
			IO.println("Por favor, introduzca los datos correctamente");
			break;
		}		
		return false;
	}

	/**
	 * Método para añadir empleados a la BBDD
	 * 
	 * @param e
	 */
	public static void addEmpleado(Empleado e) {

	}

	/**
	 * Método para añadir proyectos a la BBDD
	 * 
	 * @param p
	 */
	public static void addProyecto(Proyecto p) {

	}

	private static void initDataBase() {
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.close();
	}
}
