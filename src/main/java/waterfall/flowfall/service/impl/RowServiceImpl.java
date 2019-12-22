package waterfall.flowfall.service.impl;

import org.springframework.stereotype.Service;
import waterfall.flowfall.model.Row;
import waterfall.flowfall.repository.RowRepository;
import waterfall.flowfall.service.RowService;

import java.util.Optional;

@Service
public class RowServiceImpl implements RowService {

    private RowRepository rowRepository;

    public RowServiceImpl(RowRepository rowRepository) {
        this.rowRepository = rowRepository;
    }

    @Override
    public Iterable<Row> findAll() {
        return rowRepository.findAll();
    }

    @Override
    public Optional<Row> findById(Long id) {
        return rowRepository.findById(id);
    }

    @Override
    public Row update(Row row) {
        return rowRepository.findById(row.getId())
                .map(storedRow -> {
                    storedRow.setIndex(row.getIndex());
                    storedRow.setContent(row.getContent());

                    return rowRepository.save(storedRow);
                }).orElse(null);
    }

    @Override
    public Row save(Row row) {
        return rowRepository.save(row);
    }

    @Override
    public void delete(Row row) {
        rowRepository.delete(row);
    }
}
