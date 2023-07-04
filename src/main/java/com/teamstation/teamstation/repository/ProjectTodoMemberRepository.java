package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.entity.ProjectTodo;
import com.teamstation.teamstation.entity.ProjectTodoMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTodoMemberRepository extends JpaRepository<ProjectTodoMember, Long> {
    List<ProjectTodoMember> findByProjectTodoId(Long projectTodoId);
    List<ProjectTodoMember> findByProjectTodo(ProjectTodo projectTodo);
    void deleteByProjectTodoId(@Param("projectTodoId") Long projectTodoId);
}
