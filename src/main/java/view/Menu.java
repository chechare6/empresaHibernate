package view;

import java.util.List;
import IO.IO;
import controller.Controller;
import dao.HibernateManager;
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
			opciones = List.of("BBDD Empresa ~ Hibernate:\n1. DEPARTAMENTOS", "2. EMPLEADOS", "3. PROYECTOS", "0. CERRAR");
			IO.println(opciones);
			int seleccionado = IO.readString().charAt(0);
			switch (seleccionado) {
			case '1':
				MenuDepartamento.menuDep(controller);
				break;
			case '2':
				MenuEmpleado.menuEmp(controller);
				break;
			case '3':
				MenuProyecto.MenuProy(controller);
				break;
			case '0':
				IO.println("CERRANDO BBDD");
				System.exit(0);
				break;
			}
		}
	}
	
	private static void initDataBase() {
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.close();
	}
}
