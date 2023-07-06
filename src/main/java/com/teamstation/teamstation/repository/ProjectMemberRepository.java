package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findByProject(Project project);
}
