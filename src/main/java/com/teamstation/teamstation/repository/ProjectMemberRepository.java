package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findByProject(Project project);

    @Query("select p from ProjectMember p where p.member.email like %:email% order by p.project.regDate asc ")
    List<ProjectMember> findByEmail(String email);

    List<ProjectMember> findByProjectId(Long projectId);

    ProjectMember findByMemberIdAndProjectId(Long memberId, Long projectId);
}
