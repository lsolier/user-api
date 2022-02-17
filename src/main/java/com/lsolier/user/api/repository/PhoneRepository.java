package com.lsolier.user.api.repository;

import com.lsolier.user.api.model.entity.PhoneEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {

}
