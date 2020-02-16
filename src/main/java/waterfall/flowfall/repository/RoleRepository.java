package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.GlobalRole;

@Repository
public interface RoleRepository extends CrudRepository<GlobalRole, Long> {

}
