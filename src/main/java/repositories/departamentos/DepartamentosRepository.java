package repositories.departamentos;

import java.util.Optional;

import models.Departamento;
import models.Empleado;
import repositories.CrudRepository;

public interface DepartamentosRepository extends CrudRepository<Departamento, Integer> {
	Optional<Departamento> findByJefe(Empleado jefe);
}
