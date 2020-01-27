package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.RowMessage;


@Repository
public interface RowMessageRepository extends CrudRepository<RowMessage, Long> {
    Iterable<RowMessage> findAllByRowIdOrderByCreatedDesc(Long rowId);
}
