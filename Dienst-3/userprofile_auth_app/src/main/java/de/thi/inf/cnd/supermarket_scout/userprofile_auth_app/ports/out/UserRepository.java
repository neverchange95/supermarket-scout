package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.ports.out;

import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.domain.models.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByUsername(String username);
}
