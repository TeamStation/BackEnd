package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectSessionRepository extends JpaRepository<ProjectSession, Long> {
    List<ProjectSession> findByProject(Project project);
}
