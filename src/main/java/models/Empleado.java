package models;

import IO.IO;
import dao.HibernateManager;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hib_empleado")
@NamedQuery(name = "Empleado.noDepartamento", query = "SELECT e FROM Empleado e WHERE e.departamento IS NULL")
public class Empleado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nombre;

	private Double salario;

	@ManyToOne()
	@JoinColumn(name = "departamento")
	private Departamento departamento;
	
	public Empleado(int id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}
	
	public Empleado addEmpleados() {
		IO.print("Nombre del nuevo empleado: ");
		String nombre = IO.readString();
		IO.print("Salario del nuevo empleado: ");
		Double salario = IO.readDouble();
		IO.print("¿Está asignado a un departamento? (S/N): ");
		switch (IO.readString().toUpperCase().charAt(0)) {
		case 'S':
			IO.print("ID del Departamento: ");
			Integer idDep = IO.readInt();
			Departamento d = HibernateManager.em.getReference(Departamento.class, idDep);
			Empleado empDep = new Empleado();
			empDep.setNombre(nombre);
			empDep.setSalario(salario);
			empDep.setDepartamento(d);
			IO.println("Empleado añadido con éxito");
			return empDep;
		case 'N':
			Empleado empSDep = new Empleado();
			empSDep.setNombre(nombre);
			empSDep.setSalario(salario);			
			IO.println("Empleado añadido con éxito");
			return empSDep;
		default:
			IO.println("No se pudo añadir al empleado");
			return null;
		}
	}

	public boolean removeEmpleado(Integer id) {
		Empleado emp = HibernateManager.em.find(Empleado.class, id);		
		//BUSCAR DEP EN LOS QUE SEA JEFE Y HACER SET NULL
		HibernateManager.em.remove(emp);
		return false;
	}
	
	@Override
	public String toString() {
		String dep = departamento == null ? "¿?" : departamento.getNombre();
		return String.format("Empleado     [%-2d %-25s %s]", id, nombre, dep);
	}

	@Override
	public int hashCode() {
		return id;
	}

	public boolean equals(Empleado e) {
		return e != null && e.getId() != null && e.getId() == id;
	}
}
