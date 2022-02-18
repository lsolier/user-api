package com.lsolier.user.api.usermanager.repository;

import com.lsolier.user.api.usermanager.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

}
