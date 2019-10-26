package waterfall.flowfall.service;

import waterfall.flowfall.model.User;

import java.util.Optional;

public interface UserService {
    Iterable<User> findAll();
    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user);
}
