package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.model.Row;
import waterfall.flowfall.service.RowService;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
public class RowController {

    private RowService rowService;

    @Autowired
    public RowController(RowService rowService) {
        this.rowService = rowService;
    }

    @PutMapping(value = "/rows")
    public ResponseEntity<Row> updateRow(@RequestBody Row Row) {
        return new ResponseEntity<>(rowService.update(Row), HttpStatus.OK);
    }

    @PostMapping(value = "/rows")
    public ResponseEntity<Row> addRow(@RequestBody Row Row) {
        return new ResponseEntity<>(rowService.save(Row), HttpStatus.OK);
    }

    @DeleteMapping(value = "/rows/{id}")
    public ResponseEntity<Void> deleteRow(@PathVariable Long id) {
        return rowService.findById(id)
                .map(row -> {
                    rowService.delete(row);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
