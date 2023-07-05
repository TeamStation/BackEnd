package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.entity.Session;
import com.teamstation.teamstation.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Session save(Session session);
}
