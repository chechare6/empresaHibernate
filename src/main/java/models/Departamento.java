package models;

import java.util.*;
import IO.IO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hib_departamento")
@NamedQueries({
		@NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento r"),
		@NamedQuery(name = "Departamento.findByNombre", query = "SELECT d FROM Departamento d WHERE d.nombre LIKE :nombre"),
		@NamedQuery(name = "Departamento.findByEmpleado", query = "SELECT DISTINCT d FROM Empleado e JOIN e.departamento d WHERE e.id = :id") })
public class Departamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nombre;

	private Integer jefe;

	@OneToMany(mappedBy = "departamento")
//	@OneToMany(mappedBy = "departamento", orphanRemoval = true)
	private Set<Empleado> empleados = new HashSet<>();

	public Departamento(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Departamento(String nombre, Integer jefe) {
		this.nombre = nombre;
		this.jefe = jefe;
	}

	public Departamento(Integer id) {
		this.id = id;
	}

	public Departamento(String nombre) {
		this.nombre = nombre;
	}

	public Departamento addDepartamento() {
		IO.print("Nombre del nuevo departamento: ");
		String nombre = IO.readString();
		IO.print("¿Tiene jefe de departamento? (S/N): ");
		switch (IO.readString().toUpperCase().charAt(0)) {
		case 'S':
			IO.print("ID del Jefe: ");
			Integer idJefe = IO.readInt();
			Departamento depJefe = new Departamento();
			depJefe.setNombre(nombre);
			depJefe.setJefe(idJefe);
			IO.println("Departamento añadido con éxito");
			return depJefe;
		case 'N':
			Departamento depSJefe = new Departamento();
			depSJefe.setNombre(nombre);
			IO.println("Departamento añadido con éxito");
			return depSJefe;
		default:
			IO.println("No se pudo añadir el departamento");
			return null;
		}
	}

	public void addEmpleado(Empleado e) {
		if (e.getDepartamento() != null) {
			e.getDepartamento().getEmpleados().remove(e);
		}
		e.setDepartamento(this);
		empleados.add(e);
	}

	public void removeEmpleado(Empleado e) {
		e.setDepartamento(null);
		empleados.remove(e);
	}

	@Override
	public String toString() {
		List<String> emps = empleados.stream().map(e -> e.getNombre()).sorted().toList();
		return String.format("Departamento [%-2d %-25s %s]", id, nombre, emps);
	}
}
