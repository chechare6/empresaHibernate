package view;

import java.util.List;
import IO.IO;
import dao.HibernateManager;
import models.Departamento;
import models.Empleado;
import models.Proyecto;

public class Menu {
	public static void main(String[] args) {
		HibernateManager bd = new HibernateManager();
		Empleado e = new Empleado();
		Departamento d = new Departamento();
		Proyecto p = new Proyecto();
		List<String> opciones = List.of("1. Ver departamentos", "2. Añadir departamentos", "3. Eliminar departamentos",
				"4. Ver empleados", "5. Añadir empleados", "6. Eliminar empleados", "7. Ver proyectos",
				"8. Añadir proyecto", "9. Eliminar proyecto", "0. Salir");
		while (true) {
			System.out.println(opciones);
			switch (IO.readString().charAt(0)) {
			case '1':
				verDepartamentos(d);
				break;
			case '2':
				addDepartamento(d);
				break;
			case '3':
//				deleteDepartamentos(d);
				System.out.println("deleteDepartamentos");
				break;
			case '4':
				verEmpleados(e);
				break;
			case '5':
				addEmpleado(e);
				break;
			case '6':
//				deleteEmpleados(e);
				System.out.println("deleteEmpleados");
				break;
			case '7':
				verProyectos(p);
				break;
			case '8':
//				addProyecto(p);
				System.out.println("addProyecto");
				break;
			case '9':
//				deleteProyecto(p);
				System.out.println("deleteProyecto");
				break;
			case '0':
//				cerrarEmp(e);
//				cerrarDep(d);
				IO.println("CERRANDO BBDD");
				HibernateManager.em.close();
				HibernateManager.emf.close();
				return;
			default:
				break;
			}
		}
	}

	public static void verDepartamentos(Departamento d) {
		System.out.println(d.toString());
	}

	public static void verEmpleados(Empleado e) {
		System.out.println(e.toString());
	}

	public static void verProyectos(Proyecto p) {
		System.out.println(p.toString());
	}
	
	/**
	 * Método para añadir departamentos a la BBDD
	 * @param d
	 */
	public static void addDepartamento(Departamento d) {
		HibernateManager.em.getTransaction().begin();
		HibernateManager.em.persist(d.addDepartamento());
		HibernateManager.em.getTransaction().commit();
	}
	
	/**
	 * Método para añadir empleados a la BBDD
	 * @param e
	 */
	public static void addEmpleado(Empleado e) {
		HibernateManager.em.getTransaction().begin();
		HibernateManager.em.persist(e.addEmpleados());
		HibernateManager.em.getTransaction().commit();
	}
	
	/**
	 * Método para añadir proyectos a la BBDD
	 * @param p
	 */
	public static void addProyecto(Proyecto p) {
		HibernateManager.em.getTransaction().begin();
		HibernateManager.em.persist(p.addProyectos());
		HibernateManager.em.getTransaction().commit();
	}
}
