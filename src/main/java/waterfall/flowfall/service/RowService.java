package waterfall.flowfall.service;

import waterfall.flowfall.model.Row;

import java.util.Optional;

public interface RowService {
    Iterable<Row> findAll();
    Optional<Row> findById(Long id);

    Row update(Row row);

    Row save(Row row);

    void delete(Row row);
}
