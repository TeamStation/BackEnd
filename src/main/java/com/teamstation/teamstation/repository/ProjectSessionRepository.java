package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.entity.ProjectSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectSessionRepository extends JpaRepository<ProjectSession, Long> {

}
