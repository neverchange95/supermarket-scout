package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.application;

import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.domain.models.User;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.domain.services.UserService;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.ports.out.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(String username, String password, String email, String location, String preferredCategory) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setLocation(location);
        user.setPreferredCategory(preferredCategory);

        return userRepository.save(user);
    }

    @Override
    public User loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) return user.get();
            return null;
        }

        return null;
    }
}
