package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.entity.ProjectTodo;
import com.teamstation.teamstation.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProjectTodoRepository extends JpaRepository<ProjectTodo, Long> {
    // ProjectTodo에서 category 속성을 포함하는 값을 검색함
    List<ProjectTodo> findByCategoryContaining(String category);

    List<ProjectTodo> findByProjectId(Long projectId);

}
