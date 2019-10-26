package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.Board;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long> {

}
