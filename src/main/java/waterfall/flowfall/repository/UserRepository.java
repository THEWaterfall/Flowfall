package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import waterfall.flowfall.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
