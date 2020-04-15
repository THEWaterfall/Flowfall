package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.model.Row;
import waterfall.flowfall.service.RowService;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
@RequestMapping("/api/v1/boards/{boardId}/columns/{colId}/rows")
public class RowController {

    private RowService rowService;

    @Autowired
    public RowController(RowService rowService) {
        this.rowService = rowService;
    }

    @PreAuthorize("@access.require('ROW', 'READ', #boardId)")
    @GetMapping(value = "")
    public ResponseEntity<Iterable<Row>> getRows(@PathVariable Long boardId, @PathVariable Long colId) {
        return new ResponseEntity<>(rowService.findAllByBoardColumnId(colId), HttpStatus.OK);
    }

    @PreAuthorize("@access.require('ROW', 'READ', #boardId)")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Row> getRowById(@PathVariable Long boardId, @PathVariable Long id) {
        return rowService.findById(id)
                .map(row -> new ResponseEntity<>(row, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("@access.require('ROW', 'CREATE', #boardId)")
    @PostMapping(value = "")
    public ResponseEntity<Row> addRow(@PathVariable Long boardId, @RequestBody Row row) {
        return new ResponseEntity<>(rowService.save(row), HttpStatus.OK);
    }

    @PreAuthorize("@access.require('ROW', 'UPDATE', #boardId)")
    @PutMapping(value = "")
    public ResponseEntity<Row> updateRow(@PathVariable Long boardId, @RequestBody Row row) {
        return new ResponseEntity<>(rowService.update(row), HttpStatus.OK);
    }

    @PreAuthorize("@access.require('ROW', 'DELETE', #boardId)")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRow(@PathVariable Long boardId, @PathVariable Long id) {
        return rowService.findById(id)
                .map(row -> {
                    rowService.delete(row);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
