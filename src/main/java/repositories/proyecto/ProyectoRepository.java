package repositories.proyecto;

import repositories.CrudRepository;
import models.Empleado;
import models.Proyecto;

public interface ProyectoRepository extends CrudRepository<Proyecto, Integer>{

	boolean addEmpleado(Empleado e, Proyecto p);

	boolean deleteEmpleado(Empleado e, Proyecto p);

}
