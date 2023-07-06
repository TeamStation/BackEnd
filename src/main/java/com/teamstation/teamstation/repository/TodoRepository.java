package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Todo save(Todo todo);

    @Query("select t from Todo t " +
            "where t.member.email = :email " +
            "order by t.todoRegDate asc")
    List<Todo> findTodoList(@Param("email") String email);
}
