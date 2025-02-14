package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.jpa;

import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.jpa.entities.UserEntity;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.mapper.UserMapper;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.domain.models.User;
import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.ports.out.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserRepositoryImpl(JpaUserRepository jpaUserRepository, UserMapper userMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        Optional<UserEntity> existingUserEntityOpt = jpaUserRepository.findByUsername(user.getUsername());
        if (existingUserEntityOpt.isPresent()) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setEmail(user.getEmail());
        userEntity.setLocation(user.getLocation());
        userEntity.setPreferredCategory(user.getPreferredCategory());

        userEntity = jpaUserRepository.save(userEntity);
        return userMapper.toDomain(userEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> userEntity = jpaUserRepository.findByUsername(username);

        return userEntity.map(userMapper::toDomain);

    }
}
