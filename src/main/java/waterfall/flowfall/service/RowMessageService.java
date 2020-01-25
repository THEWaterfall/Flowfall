package waterfall.flowfall.service;

import waterfall.flowfall.model.RowMessage;

import java.util.Optional;

public interface RowMessageService {
    Iterable<RowMessage> findAll();
    Optional<RowMessage> findById(Long id);
    Optional<RowMessage> findByRowId(Long rowId);

    RowMessage update(RowMessage rowMessage);

    RowMessage save(RowMessage rowMessage);

    void delete(RowMessage rowMessage);
}
