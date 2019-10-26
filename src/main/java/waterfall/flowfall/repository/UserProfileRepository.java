package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import waterfall.flowfall.model.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {

}
