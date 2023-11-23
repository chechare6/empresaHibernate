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
public class Departamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nombre;

	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jefe")
    private Empleado jefe;

	@OneToMany(mappedBy = "departamento", fetch = FetchType.EAGER)
	private Set<Empleado> empleados = new HashSet<>();

	public void addEmpleado(Empleado e) {
		this.getEmpleados().add(e);
		e.setDepartamento(this);
	}
	
	public void addJefe(Empleado e) {
		this.setJefe(e);
	}
	
	@Override
	public String toString() {
//		List<String> emps = empleados.stream().map(e -> e.getNombre()).sorted().toList();
		return String.format("Departamento [Id:%d, Nombre: %s, Jefe: %s]", id, nombre, (jefe != null) ? jefe.getNombre() : null);
	}
}
