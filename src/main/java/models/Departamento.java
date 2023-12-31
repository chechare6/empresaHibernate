package models;

import java.util.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hib_departamento")
@NamedQuery(name="Departamento.findAll", query = "SELECT d FROM Departamento d")
@NamedQuery(name="Departamento.findByJefe", query = "SELECT d FROM Departamento d WHERE d.jefe = :jefe")
public class Departamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nombre;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "jefe")
    private Empleado jefe;

	@OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER)
	private Set<Empleado> empleados = new HashSet<>();

	public Departamento(Integer id) {
		this.id = id;
	}

	public Departamento(String nombre) {
		this.nombre = nombre;
	}

	public Departamento(String nombre, Empleado jefe) {
		this.nombre = nombre;
		this.jefe = jefe;
	}
	
	public void addEmpleado(Empleado e) {
		this.getEmpleados().add(e);
		e.setDepartamento(this);
	}
	
	public void addJefe(Empleado e) {
		this.setJefe(e);
	}

	@Override
	public String toString() {
		return String.format("Departamento [ID:%d, Nombre: %s, Jefe: %s]", id, nombre, (jefe != null) ? jefe.getNombre() : "No hay jefe asignado");
	}

	public Departamento(Integer id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Departamento(Integer id, String nombre, Optional<Empleado> newJefe) {
		this.id = id;
		this.nombre = nombre;
		if(newJefe.isPresent())
			this.jefe = newJefe.get();
		else
			this.jefe = null;
	}
}
