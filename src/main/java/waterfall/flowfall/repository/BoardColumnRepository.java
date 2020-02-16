package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.BoardColumn;

@Repository
public interface BoardColumnRepository extends CrudRepository<BoardColumn, Long> {
    Iterable<BoardColumn> findAllByBoardId(Long boardId);
}
