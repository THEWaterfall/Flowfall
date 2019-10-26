package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import waterfall.flowfall.model.Board;

public interface BoardRepository extends CrudRepository<Board, Long> {

}
