package waterfall.flowfall.service;

import waterfall.flowfall.model.Board;

import java.util.Optional;

public interface BoardService {
    Iterable<Board> findAll();
    Optional<Board> findById(Long id);

    Board save(Board board);

    void delete(Board board);
}
