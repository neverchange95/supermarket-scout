package de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.jpa;

import de.thi.inf.cnd.supermarket_scout.userprofile_auth_app.adapter.jpa.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
