package com.teamstation.teamstation.service;

import com.teamstation.teamstation.constant.TodoState;
import com.teamstation.teamstation.dto.TodoDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.entity.Todo;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class TodoServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TodoService todoService;

    @Autowired
    TodoRepository todoRepository;

    public Member savedMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    public TodoDto savedTodoDto(){
        TodoDto todoDto = new TodoDto();
        todoDto.setTodoName("테스트");
        todoDto.setTodoDeadLine("2023-07-12");
        todoDto.setTodoState(TodoState.Proceeding);
        return todoDto;
    }

    @Test
    public void saveTodo() {
        TodoDto todoDto = savedTodoDto();
        Member member = savedMember();

        Long todoId = todoService.saveTodo(todoDto, member.getEmail());

        String todoDtoName = todoDto.getTodoName();
        Todo savedTodo = todoRepository.findById(todoId).orElseThrow(EntityNotFoundException::new);
        System.out.println(todoId);
        System.out.println(savedTodo.getId());
        System.out.println(todoDtoName);
        String savedTodoName = savedTodo.getTodoName();
        System.out.println(savedTodoName);

        assertEquals(todoDtoName, savedTodoName);
    }
}