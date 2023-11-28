package repositories.empleados;

import models.Empleado;
import models.Proyecto;
import repositories.CrudRepository;

public interface EmpleadosRepository extends CrudRepository<Empleado, Integer> {

	boolean addProyect(Proyecto p, Empleado e);

	boolean deleteProyect(Proyecto p, Empleado e);
}
