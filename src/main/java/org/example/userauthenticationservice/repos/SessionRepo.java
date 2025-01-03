package org.example.userauthenticationservice.repos;

import org.example.userauthenticationservice.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepo extends JpaRepository<UserSession,Long> {
}
