package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.UserProfile;

@Repository
public abstract class UserProfileRepository implements CrudRepository<UserProfile, Long> {

}
