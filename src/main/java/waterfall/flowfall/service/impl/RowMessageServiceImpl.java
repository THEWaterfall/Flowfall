package waterfall.flowfall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waterfall.flowfall.model.RowMessage;
import waterfall.flowfall.repository.RowMessageRepository;
import waterfall.flowfall.service.RowMessageService;

import java.util.Date;
import java.util.Optional;

@Service
public class RowMessageServiceImpl implements RowMessageService {

    private RowMessageRepository rowMessageRepository;

    @Autowired
    public RowMessageServiceImpl(RowMessageRepository rowMessageRepository) {
        this.rowMessageRepository = rowMessageRepository;
    }

    @Override
    public Iterable<RowMessage> findAll() {
        return rowMessageRepository.findAll();
    }

    @Override
    public Optional<RowMessage> findById(Long id) {
        return rowMessageRepository.findById(id);
    }

    public Iterable<RowMessage> findAllByRowIdOrderByCreatedDesc(Long rowId) {
        return rowMessageRepository.findAllByRowIdOrderByCreatedDesc(rowId);
    }

    @Override
    public RowMessage update(RowMessage rowMessage) {
        return rowMessageRepository.save(rowMessage);
    }

    @Override
    public RowMessage save(RowMessage rowMessage) {
        rowMessage.setCreated(new Date());
        return rowMessageRepository.save(rowMessage);
    }

    @Override
    public void delete(RowMessage rowMessage) {
        rowMessageRepository.delete(rowMessage);
    }
}
