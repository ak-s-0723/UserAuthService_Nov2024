package org.example.userauthenticationservice.repos;

import org.example.userauthenticationservice.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepo extends JpaRepository<UserSession,Long> {
    Optional<UserSession> findByTokenAndUser_Id(String token, Long userId);
}
