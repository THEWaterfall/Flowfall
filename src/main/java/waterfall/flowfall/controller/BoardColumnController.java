package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.model.BoardColumn;
import waterfall.flowfall.service.BoardColumnService;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
@RequestMapping(value = "/api/v1/boards/{boardId}/columns")
public class BoardColumnController {

    private BoardColumnService boardColumnService;

    @Autowired
    public BoardColumnController(BoardColumnService boardColumnService) {
        this.boardColumnService = boardColumnService;
    }

    @PreAuthorize("@access.require('COLUMN', 'READ', #boardId)")
    @GetMapping(value = "")
    public ResponseEntity<Iterable<BoardColumn>> getColumns(@PathVariable Long boardId) {
        return new ResponseEntity<>(boardColumnService.findAllByBoardId(boardId), HttpStatus.OK);
    }

    @PreAuthorize("@access.require('COLUMN', 'READ', #boardId)")
    @GetMapping(value = "/{id}")
    public ResponseEntity<BoardColumn> getColumnById(@PathVariable Long boardId, @PathVariable Long id) {
        return boardColumnService.findById(id)
                .map(column -> new ResponseEntity<>(column, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("@access.require('COLUMN', 'CREATE', #boardId)")
    @PostMapping(value = "")
    public ResponseEntity<BoardColumn> addColumn(@PathVariable Long boardId, @RequestBody BoardColumn column) {
        return new ResponseEntity<>(boardColumnService.save(column), HttpStatus.OK);
    }

    @PreAuthorize("@access.require('COLUMN', 'UPDATE', #boardId)")
    @PutMapping(value = "")
    public ResponseEntity<BoardColumn> updateColumn(@PathVariable Long boardId, @RequestBody BoardColumn column) {
        return new ResponseEntity<>(boardColumnService.update(column), HttpStatus.OK);
    }

    @PreAuthorize("@access.require('COLUMN', 'DELETE', #boardId)")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long boardId, @PathVariable Long id) {
        return boardColumnService.findById(id)
                .map(boardColumn -> {
                    boardColumnService.delete(boardColumn);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
