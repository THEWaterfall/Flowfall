package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
