package com.snsIntegrationFeedService.user.repository;

import com.snsIntegrationFeedService.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByAccount(String account);
}
