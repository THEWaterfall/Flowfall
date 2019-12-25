package waterfall.flowfall.service;

import waterfall.flowfall.model.Board;

import java.util.Optional;

public interface BoardService {
    Iterable<Board> findAll();
    Iterable<Board> findAllByUserId(Long id);
    Iterable<Board> findAllCollabBoardsByUserId(Long id);

    Optional<Board> findById(Long id);

    Board update(Board board);

    Board save(Board board);

    void delete(Board board);
}
