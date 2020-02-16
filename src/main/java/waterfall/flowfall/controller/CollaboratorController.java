package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.model.Board;
import waterfall.flowfall.model.User;
import waterfall.flowfall.service.BoardService;
import waterfall.flowfall.service.UserService;

import java.util.stream.Collectors;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
@RequestMapping(value = "/api/v1/boards/{boardId}/collab")
public class CollaboratorController {

    private BoardService boardService;
    private UserService userService;

    @Autowired
    public CollaboratorController(BoardService boardService, UserService userService) {
        this.boardService = boardService;
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<Iterable<User>> getCollaboratorsByBoardId(@PathVariable Long boardId) {
        return new ResponseEntity<>(userService.findCollaboratorsByBoardId(boardId), HttpStatus.OK);
    }

    @GetMapping(value = "/owner")
    public ResponseEntity<User> getOwnerByBoardId(@PathVariable Long boardId) {
        return this.boardService.findById(boardId)
                .map(board -> new ResponseEntity<>(board.getUser(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostAuthorize("@userPermissions.isOwner(#collabId)")
    @GetMapping(value = "/{collabId}")
    public ResponseEntity<Iterable<Board>> getBoardsByCollaborator(@PathVariable Long collabId) {
        return new ResponseEntity<>(boardService.findAllCollabBoardsByUserId(collabId), HttpStatus.OK);
    }

    @PreAuthorize("@boardPermissions.isOwner(#boardId)")
    @PostMapping(value = "/invite")
    public ResponseEntity<Void> inviteCollaborator(@PathVariable Long boardId, @RequestParam String collabEmail) {
        return boardService.findById(boardId)
                .map(board -> {
                    User user = userService.findByEmail(collabEmail).orElse(null);
                    if (user == null) {
                        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                    }

                    if(board.getCollaborators().contains(user)) {
                        return new ResponseEntity<Void>(HttpStatus.OK);
                    }

                    board.addCollaborator(user);
                    boardService.update(board);

                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("@boardPermissions.isOwner(#boardId)")
    @DeleteMapping(value = "/{collabId}")
    public ResponseEntity<Void> deleteCollaborator(@PathVariable Long collabId, @PathVariable Long boardId) {
        return boardService.findById(boardId)
                .map(board -> {
                    board.setCollaborators(board.getCollaborators().stream()
                            .filter(collab -> !collab.getId().equals(collabId))
                            .collect(Collectors.toSet())
                    );
                    boardService.update(board);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
