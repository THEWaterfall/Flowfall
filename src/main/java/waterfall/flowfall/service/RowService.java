package waterfall.flowfall.service;

import waterfall.flowfall.model.Row;

import java.util.Optional;

public interface RowService {
    Iterable<Row> findAll();
    Iterable<Row> findAllByBoardColumnId(Long boardColumnId);
    Optional<Row> findById(Long id);

    Row update(Row row);

    Row save(Row row);

    void delete(Row row);
}
