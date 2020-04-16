package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.GlobalRole;
import waterfall.flowfall.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
