package waterfall.flowfall.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT collabs FROM Board b JOIN b.collaborators collabs WHERE b.id = :boardId")
    Iterable<User> findCollaboratorsByBoardId(Long boardId);
}
