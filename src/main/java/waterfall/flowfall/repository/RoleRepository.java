package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
