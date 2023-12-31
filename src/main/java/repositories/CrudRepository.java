package repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
	List<T> findAll();
	Optional<T> findById(ID id);
	Boolean save (T entity);
	Boolean delete(T entity);
	Boolean update(T entity);
}
