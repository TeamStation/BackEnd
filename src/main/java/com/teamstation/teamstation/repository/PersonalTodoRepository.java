package com.teamstation.teamstation.repository;

import com.teamstation.teamstation.entity.PersonalTodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalTodoRepository extends JpaRepository<PersonalTodo, Long> {
    
}
