package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.model.Board;
import waterfall.flowfall.model.BoardColumn;
import waterfall.flowfall.model.Row;
import waterfall.flowfall.service.BoardService;

import java.util.Comparator;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
public class BoardController {

    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/boards")
    public ResponseEntity getBoards() {
        return new ResponseEntity(boardService.findAll(), HttpStatus.OK);
    }

    @PostAuthorize("@boardPermissions.hasRights(returnObject.getBody())")
    @GetMapping(value = "/boards/u/{id}")
    public ResponseEntity getBoardsByUserId(@PathVariable Long id) {
        return new ResponseEntity(boardService.findAllByUserId(id), HttpStatus.OK);
    }

    @PostAuthorize("@boardPermissions.hasRights(returnObject.getBody())")
    @GetMapping(value = "/boards/u/{id}/collab")
    public ResponseEntity getBoardsByCollaborator(@PathVariable Long id) {
        return new ResponseEntity(boardService.findAllCollabBoardsByUserId(id), HttpStatus.OK);
    }

    @PostAuthorize("@boardPermissions.hasRights(returnObject.getBody())")
    @GetMapping(value = "/boards/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
        return boardService.findById(id)
                .map(board -> new ResponseEntity<>(sortByIndex(board), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.OK));
    }

    @PostMapping(value = "/boards")
    public ResponseEntity<Board> addBoard(@RequestBody Board board) {
        return new ResponseEntity<>(boardService.save(board), HttpStatus.OK);
    }

    @PreAuthorize("@boardPermissions.isOwner(#board)")
    @PutMapping(value = "/boards")
    public ResponseEntity<Board> updateBoard(@RequestBody Board board) {
        return new ResponseEntity<>(boardService.update(board), HttpStatus.OK);
    }

    @PreAuthorize("@boardPermissions.isOwner(#id)")
    @DeleteMapping(value = "/boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        return boardService.findById(id)
                .map(board -> {
                    boardService.delete(board);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    private Board sortByIndex(Board board) {
        board.getBoardColumns().sort(Comparator.comparingInt(BoardColumn::getIndex));
        board.getBoardColumns()
                .forEach(boardColumn -> boardColumn.getRows().sort(Comparator.comparingLong(Row::getIndex)));

        return board;
    }
}
