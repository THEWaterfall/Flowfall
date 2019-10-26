package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.model.Board;
import waterfall.flowfall.repository.BoardRepository;

@RestController
public class BoardController {

    private BoardRepository boardRepository;

    @Autowired
    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping(value = "/boards")
    public ResponseEntity getBoards() {
        return new ResponseEntity(boardRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/boards/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
        return boardRepository.findById(id)
                .map(board -> new ResponseEntity<>(board, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.OK));
    }

    @PostMapping(value = "/boards")
    public ResponseEntity<Board> addBoard(@RequestBody Board board) {
        return new ResponseEntity<>(boardRepository.save(board), HttpStatus.OK);
    }

    @PutMapping(value = "/boards")
    public ResponseEntity<Board> updateBoard(@RequestBody Board board) {
        return new ResponseEntity<>(boardRepository.save(board), HttpStatus.OK);
    }

    @DeleteMapping(value = "/boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        return boardRepository.findById(id)
                .map(board -> {
                    boardRepository.delete(board);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
