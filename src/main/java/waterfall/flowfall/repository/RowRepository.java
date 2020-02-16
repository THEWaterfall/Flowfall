package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.Row;

@Repository
public interface RowRepository extends CrudRepository<Row, Long> {
    Iterable<Row> findAllByBoardColumnId(Long boardColumnId);
}
