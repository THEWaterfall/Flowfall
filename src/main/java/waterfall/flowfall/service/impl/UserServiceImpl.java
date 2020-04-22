package waterfall.flowfall.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import waterfall.flowfall.model.User;
import waterfall.flowfall.repository.UserRepository;
import waterfall.flowfall.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bcrypt;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcrypt) {
        this.userRepository = userRepository;
        this.bcrypt = bcrypt;
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Iterable<User> findCollaboratorsByBoardId(Long boardId) {
        return userRepository.findCollaboratorsByBoardId(boardId);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        user.setPassword(bcrypt.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.findById(user.getId())
                .map(savedUser -> {
                    BeanUtils.copyProperties(user, savedUser, "id");
                    return userRepository.save(savedUser);
                })
                .orElse(null);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
