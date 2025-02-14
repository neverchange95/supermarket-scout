package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.domain.services;

import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.domain.models.User;

public interface UserService {
    User registerUser(String username, String password, String email, String location, String preferredCategory);
    User loginUser(String username, String password);
}
