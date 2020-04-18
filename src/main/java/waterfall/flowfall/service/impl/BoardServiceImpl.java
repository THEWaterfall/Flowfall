package waterfall.flowfall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waterfall.flowfall.model.Board;
import waterfall.flowfall.model.enums.RoleType;
import waterfall.flowfall.repository.BoardRepository;
import waterfall.flowfall.service.BoardService;
import waterfall.flowfall.service.UserRoleService;

import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    private BoardRepository boardRepository;
    private UserRoleService userRoleService;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, UserRoleService userRoleService) {
        this.boardRepository = boardRepository;
        this.userRoleService = userRoleService;
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
        Board savedBoard = boardRepository.save(board);
        userRoleService.addRole(savedBoard.getId(), board.getUser().getId(), RoleType.BOARD_OWNER);
        return savedBoard;
    }

    @Override
    public Board update(Board board) {
        return boardRepository.findById(board.getId())
                .map(storedBoard -> {
                    storedBoard.setName(board.getName());
                    storedBoard.setBoardColumns(board.getBoardColumns());
                    storedBoard.setCollaborators(board.getCollaborators());
                    boardRepository.save(storedBoard);

                    return storedBoard;
                }).orElse(null);
    }

    @Override
    public void delete(Board board) {
        userRoleService.deleteRole(board.getId());
        boardRepository.delete(board);
    }

    @Override
    public Iterable<Board> findAllByUserId(Long id) {
        return boardRepository.findAllByUserId(id);
    }

    @Override
    public Iterable<Board> findAllCollabBoardsByUserId(Long id) {
        return boardRepository.findAllCollabBoardsByUserId(id);
    }
}
