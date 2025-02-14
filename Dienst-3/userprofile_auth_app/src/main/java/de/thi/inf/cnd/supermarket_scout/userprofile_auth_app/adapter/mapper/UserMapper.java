package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.mapper;

import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.jpa.entities.UserEntity;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.rest.dto.UserDTO;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.domain.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getLocation(),
                entity.getPreferredCategory()
        );
    }

    public UserDTO toDTO(User user) {
        if (user == null) return null;
        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                user.getLocation(),
                user.getPreferredCategory()
        );
    }
}
