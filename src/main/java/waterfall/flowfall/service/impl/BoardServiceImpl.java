package waterfall.flowfall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waterfall.flowfall.model.Board;
import waterfall.flowfall.repository.BoardRepository;
import waterfall.flowfall.service.BoardService;

import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Iterable<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Optional<Board> findById(Long id) {
        return boardRepository.findById(id);
    }

    @Override
    public Board save(Board board) {
        return boardRepository.findById(board.getId())
                .map(storedBoard -> {
                    storedBoard.setName(board.getName());
                    storedBoard.setBoardColumns(board.getBoardColumns());
                    boardRepository.save(storedBoard);

                    return storedBoard;
                }).orElse(null);
    }

    @Override
    public void delete(Board board) {
        boardRepository.delete(board);
    }

    @Override
    public Iterable<Board> findAllByUserId(Long id) {
        return boardRepository.findAllByUserId(id);
    }
}
