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

		List<String> opciones = List.of("1. Ver departamentos", "2. Añadir departamentos", "3. Eliminar departamentos",
				"4. Ver empleados", "5. Añadir empleados", "6. Eliminar empleados", "7. Ver proyectos",
				"8. Añadir proyecto", "9. Eliminar proyecto", "0. Salir");
		while (true) {
			System.out.println(opciones);
			switch (IO.readString().charAt(0)) {
			case '1':
				verDepartamentos(controller);
				break;
			case '2':
				System.out.println("add departamentos");
				break;
			case '3':
				System.out.println("deleteDepartamentos");
				break;
			case '4':
				System.out.println("ver empleados");
				break;
			case '5':
				System.out.println("add empleados");
				break;
			case '6':
				System.out.println("deleteEmpleados");
				break;
			case '7':
				System.out.println("ver proyectos");
				break;
			case '8':
				System.out.println("addProyecto");
				break;
			case '9':
				System.out.println("deleteProyecto");
				break;
			case '0':
//				cerrarEmp(e);
//				cerrarDep(d);
				IO.println("CERRANDO BBDD");
				return;
			default:
				break;
			}
		}
	}

	public static void verDepartamentos(Controller controller) {
		/* AQUI */
		List<Departamento> departamentos = controller.getDepartamentos();
		for (Departamento departamento : departamentos) {
			System.out.println(departamento);
		}
	}

	public static void verEmpleados(Empleado e) {
		System.out.println(e.toString());
	}

	public static void verProyectos(Proyecto p) {
		System.out.println(p.toString());
	}

	/**
	 * Método para añadir departamentos a la BBDD
	 * 
	 * @param d
	 */
	public static void addDepartamento(Departamento d) {
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
