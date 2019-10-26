package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.UserProfile;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {

}
