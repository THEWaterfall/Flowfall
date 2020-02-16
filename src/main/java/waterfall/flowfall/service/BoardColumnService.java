package waterfall.flowfall.service;

import waterfall.flowfall.model.BoardColumn;

import java.util.Optional;

public interface BoardColumnService {
    Iterable<BoardColumn> findAll();
    Iterable<BoardColumn> findAllByBoardId(Long boardId);
    Optional<BoardColumn> findById(Long id);

    BoardColumn update(BoardColumn boardColumn);

    BoardColumn save(BoardColumn boardColumn);

    void delete(BoardColumn boardColumn);
}
