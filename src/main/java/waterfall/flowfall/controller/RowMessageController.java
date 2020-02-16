package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.model.RowMessage;
import waterfall.flowfall.service.RowMessageService;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
@RequestMapping("/api/v1/boards/{boardId}/columns/{colId}/rows/{rowId}/rowMessages")
public class RowMessageController {

    private RowMessageService rowMessageService;

    @Autowired
    public RowMessageController(RowMessageService rowMessageService) {
        this.rowMessageService = rowMessageService;
    }

    @GetMapping(value = "")
    public ResponseEntity<Iterable<RowMessage>> getRowMessagesByRowId(@PathVariable Long rowId) {
        return new ResponseEntity<>(rowMessageService.findAllByRowIdOrderByCreatedDesc(rowId), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RowMessage> getRowMessageById(@PathVariable Long id) {
        return rowMessageService.findById(id)
                .map(rowMessage -> new ResponseEntity<>(rowMessage, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "")
    public ResponseEntity<RowMessage> addRowMessage(@RequestBody RowMessage rowMessage) {
        return new ResponseEntity<>(rowMessageService.save(rowMessage), HttpStatus.OK);
    }

    @PutMapping(value = "")
    public ResponseEntity<RowMessage> updateRowMessage(@RequestBody RowMessage message) {
        return new ResponseEntity<>(this.rowMessageService.update(message), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRowMessage(@PathVariable Long id) {
        return rowMessageService.findById(id)
                .map(rowMessage -> {
                    rowMessageService.delete(rowMessage);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
