package models;

import java.util.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hib_proyecto")
@NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p")
public class Proyecto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;

	@ManyToMany
	@JoinTable(name = "proyecto_empleado", joinColumns = @JoinColumn(name = "proyectoId"), inverseJoinColumns = @JoinColumn(name = "empleadoId"))
	private Set<Empleado> empleados = new HashSet<>();

	public Proyecto(Integer id) {
		this.id = id;
	}

	public Proyecto(String nombre) {
		this.nombre = nombre;
	}

	public Proyecto(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public void addEmpleado(Empleado e) {
		e.getProyecto().add(this);
		this.getEmpleados().add(e);
	}

	public void removeEmpleado(Empleado e) {
		this.getEmpleados().remove(e);
	}

	@Override
	public String toString() {
//		List<String> emps = empleados.stream().map(e -> e.getNombre()).sorted().toList();
		return String.format("Proyecto [ID: %d, Nombre: %s]", id, nombre);
	}


}
