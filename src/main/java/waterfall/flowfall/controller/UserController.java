package waterfall.flowfall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import waterfall.flowfall.model.User;
import waterfall.flowfall.service.BoardService;
import waterfall.flowfall.service.UserService;

@RestController
@CrossOrigin(value="*", maxAge = 3600)
public class UserController {

    private UserService userService;
    private BoardService boardService;

    @Autowired
    public UserController(UserService userService, BoardService boardService) {
        this.userService = userService;
        this.boardService = boardService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

    @PostMapping(value = "/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @PutMapping(value = "/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> {
                    userService.delete(user);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
