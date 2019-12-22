package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.model.BoardColumn;
import waterfall.flowfall.service.BoardColumnService;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
public class BoardColumnController {

    private BoardColumnService boardColumnService;

    @Autowired
    public BoardColumnController(BoardColumnService boardColumnService) {
        this.boardColumnService = boardColumnService;
    }

    @PutMapping(value = "/boardColumns")
    public ResponseEntity<BoardColumn> updateColumn(@RequestBody BoardColumn boardColumn) {
        return new ResponseEntity<>(boardColumnService.update(boardColumn), HttpStatus.OK);
    }

    @PostMapping(value = "/boardColumns")
    public ResponseEntity<BoardColumn> addColumn(@RequestBody BoardColumn boardColumn) {
        return new ResponseEntity<>(boardColumnService.save(boardColumn), HttpStatus.OK);
    }

    @DeleteMapping(value = "/boardColumns/{id}")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long id) {
        return boardColumnService.findById(id)
                .map(boardColumn -> {
                    boardColumnService.delete(boardColumn);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
