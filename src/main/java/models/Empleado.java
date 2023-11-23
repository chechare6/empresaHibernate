	package models;
	
	import java.util.Date;
	import java.util.HashSet;
	import java.util.Set;
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
		@Column(nullable = false)
		private String nombre;
		@Column(nullable = false)
		private Double salario;
	
		@Column(nullable = false)
		@Temporal(TemporalType.DATE)
		private Date nacimiento;
	
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "departamento")
	    private Departamento departamento;
	
		@ManyToMany
		@JoinTable(name = "empleado_proyecto", joinColumns = @JoinColumn(name = "empleado_id"), inverseJoinColumns = @JoinColumn(name = "proyecto_id"))
		private Set<Proyecto> proyecto = new HashSet<>();
	
		public void addDepartamento(Departamento d) {
			this.setDepartamento(d);
			d.getEmpleados().add(this);
		}
	
		public void addProyecto(Proyecto p) {
			this.getProyecto().add(p);
			p.getEmpleados().add(this);
		}
	
		@Override
		public String toString() {
			String dep = departamento == null ? "¿?" : departamento.getNombre();
			return String.format("Empleado     [%-2d %-25s %s]", id, nombre, salario, nacimiento, dep);
		}
	
		@Override
		public int hashCode() {
			return id;
		}
	
		public boolean equals(Empleado e) {
			return e != null && e.getId() != null && e.getId() == id;
		}
	}
