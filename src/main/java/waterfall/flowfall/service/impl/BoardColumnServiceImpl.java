package waterfall.flowfall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waterfall.flowfall.model.BoardColumn;
import waterfall.flowfall.repository.BoardColumnRepository;
import waterfall.flowfall.service.BoardColumnService;

import java.util.Optional;

@Service
public class BoardColumnServiceImpl implements BoardColumnService {

    private BoardColumnRepository boardColumnRepository;

    @Autowired
    public BoardColumnServiceImpl(BoardColumnRepository boardColumnRepository) {
        this.boardColumnRepository = boardColumnRepository;
    }

    @Override
    public Iterable<BoardColumn> findAll() {
        return boardColumnRepository.findAll();
    }

    @Override
    public Optional<BoardColumn> findById(Long id) {
        return boardColumnRepository.findById(id);
    }

    @Override
    public BoardColumn update(BoardColumn boardColumn) {
        return boardColumnRepository.findById(boardColumn.getId())
                .map(storedBoardColumn -> {
                    storedBoardColumn.setIndex(boardColumn.getIndex());
                    storedBoardColumn.setName(boardColumn.getName());
                    storedBoardColumn.setRows(boardColumn.getRows());

                    if(boardColumn.getBoard() != null) {
                        storedBoardColumn.setBoard(boardColumn.getBoard());
                    }

                    return boardColumnRepository.save(storedBoardColumn);
                }).orElse(null);
    }

    @Override
    public BoardColumn save(BoardColumn boardColumn) {
        return boardColumnRepository.save(boardColumn);
    }

    @Override
    public void delete(BoardColumn boardColumn) {
        boardColumnRepository.delete(boardColumn);
    }
}
