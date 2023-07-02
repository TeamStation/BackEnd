package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
