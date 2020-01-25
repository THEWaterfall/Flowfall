package waterfall.flowfall.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import waterfall.flowfall.model.RowMessage;

import java.util.Optional;

@Repository
public interface RowMessageRepository extends CrudRepository<RowMessage, Long> {
    Optional<RowMessage> findByRowId(Long rowId);
}
