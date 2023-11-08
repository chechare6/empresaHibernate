package models;

import java.util.*;

import IO.IO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hib_proyecto")
public class Proyecto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;

	@OneToMany()
	private Set<Empleado> empleados = new HashSet<>();

	public Proyecto(String nombre) {
		this.nombre = nombre;
	}

	public Proyecto addProyectos() {
		IO.print("Nombre del proyecto: ");
		String nombre = IO.readString();
		Proyecto pro = new Proyecto();
		pro.setNombre(nombre);
		IO.println("Proyecto añadido con éxito");
		return pro;
	}
	
	public void addEmpleado(Empleado e) {
		empleados.add(e);
	}

	public void removeEmpleado(Empleado e) {
		empleados.remove(e);
	}

	@Override
	public String toString() {
		List<String> emps = empleados.stream().map(e -> e.getNombre()).sorted().toList();
		return String.format("Proyecto [%-2d %-25s %s]", id, nombre, emps);
	}
}
