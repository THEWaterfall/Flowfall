package waterfall.flowfall.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.Board;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {
    @Query("FROM Board b WHERE b.user.id = :id")
    Iterable<Board> findAllByUserId(Long id);

    @Query("FROM Board b JOIN b.collaborators collabs WHERE collabs.id = :id")
    Iterable<Board> findAllCollabBoardsByUserId(Long id);
}
