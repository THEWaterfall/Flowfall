package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.model.User;
import waterfall.flowfall.service.BoardService;
import waterfall.flowfall.service.UserService;

import java.util.stream.Collectors;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
public class CollaboratorController {

    private BoardService boardService;
    private UserService userService;

    @Autowired
    public CollaboratorController(BoardService boardService, UserService userService) {
        this.boardService = boardService;
        this.userService = userService;
    }

    @PostAuthorize("@userPermissions.isOwner(#userId)")
    @GetMapping(value = "/boards/u/{userId}/collab")
    public ResponseEntity getBoardsByCollaborator(@PathVariable Long userId) {
        return new ResponseEntity(boardService.findAllCollabBoardsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/users/b/{boardId}/owner")
    public ResponseEntity<User> getOwnerByBoardId(@PathVariable Long boardId) {
        return this.boardService.findById(boardId)
                .map(board -> new ResponseEntity<>(board.getUser(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/users/b/{boardId}/collab")
    public ResponseEntity getCollaboratorsByBoardId(@PathVariable Long boardId) {
        return new ResponseEntity<>(userService.findCollaboratorsByBoardId(boardId), HttpStatus.OK);
    }

    @PreAuthorize("@boardPermissions.isOwner(#id)")
    @PostMapping(value = "/boards/{id}/invite")
    public ResponseEntity<Void> inviteCollaborator(@PathVariable Long id, @RequestParam String collabEmail) {
        return boardService.findById(id)
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
    @DeleteMapping(value = "/boards/{boardId}/u/{collabId}")
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
