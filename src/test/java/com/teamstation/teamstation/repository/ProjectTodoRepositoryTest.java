package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.constant.TodoState;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectTodo;
import com.teamstation.teamstation.entity.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@PropertySource("classpath:/env.properties")
class ProjectTodoRepositoryTest {

    @Autowired
    private ProjectTodoRepository projectTodoRepository;

    @BeforeEach
    public void setUp() {
        projectTodoRepository.deleteAll();

        ProjectTodo projectTodo1 = ProjectTodo.builder()
                .category("디자인")
                .build();
        ProjectTodo projectTodo2 = ProjectTodo.builder()
                .category("디자인")
                .build();
        ProjectTodo projectTodo3 = ProjectTodo.builder()
                .category("개발")
                .build();
        projectTodoRepository.saveAll(List.of(projectTodo1, projectTodo2, projectTodo3));
    }

    @Test
    public void testFindByCategoryContaining() {
        // 특정 카테고리를 포함하는 ProjectTodo 엔티티를 검색
        List<ProjectTodo> foundProjectTodos = projectTodoRepository.findByCategoryContaining("디자인");

        // 검색 결과 확인
        assertEquals(2, foundProjectTodos.size());
    }
}