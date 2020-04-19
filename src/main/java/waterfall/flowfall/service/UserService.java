package waterfall.flowfall.service;

import waterfall.flowfall.model.User;

import java.util.Optional;

public interface UserService {
    Iterable<User> findAll();
    Iterable<User> findCollaboratorsByBoardId(Long boardId);

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);

    User save(User user);
    User update(User user);

    void delete(User user);

    boolean existsByEmail(String email);
}
