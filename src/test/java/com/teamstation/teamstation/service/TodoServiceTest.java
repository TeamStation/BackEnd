package com.teamstation.teamstation.service;

import com.teamstation.teamstation.constant.TodoState;
import com.teamstation.teamstation.dto.TodoDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.entity.Todo;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        todoDto.setTodoUpdateDate(LocalDateTime.now());
        LocalDate todoDeadLine = LocalDate.parse(todoDto.getTodoDeadLine(), DateTimeFormatter.ISO_DATE);
        if (todoDeadLine.isBefore(LocalDate.now())){
            todoDto.setTodoState(TodoState.Done);
        } else {
            todoDto.setTodoState(TodoState.Proceeding);
        }
        return todoDto;
    }

    public TodoDto saveTodoDto(int number){
        TodoDto todoDto = new TodoDto();
        todoDto.setTodoName("테스트"+number);
        todoDto.setTodoDeadLine("2023-07-"+String.format("%02d", number));
        LocalDate localDateTime = LocalDate.now();
        LocalDate todoDate = LocalDate.parse(todoDto.getTodoDeadLine(), DateTimeFormatter.ISO_DATE);
        if (todoDate.isBefore(localDateTime)){
            todoDto.setTodoState(TodoState.Done);
        }else{
            todoDto.setTodoState(TodoState.Proceeding);
        }
        return todoDto;
    }

    @Test
    @DisplayName("개인 할 일 등록 테스트")
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

    @Test
    @DisplayName("개인 할 일 조회 테스트(현재 진행중인 할 일만 추가한 순서대로 조회)")
    public void getTodoList(){
        List<TodoDto> todoDtoList = new ArrayList<>();
        List<Long> todoIdList = new ArrayList<>();
        Member member = savedMember();

        for(int i=5; i<15; i++){
            TodoDto todoDto = saveTodoDto(i);
            Long todoId = todoService.saveTodo(todoDto, member.getEmail());
            if (StringUtils.equals(TodoState.Proceeding.toString(), todoDto.getTodoState().toString())){
                todoIdList.add(todoId);
            }
        }

        todoDtoList = todoService.getProceedingTodoList(member.getEmail());
        for(TodoDto todoDto:todoDtoList){
            System.out.println(todoDto.getTodoName());
            System.out.println(todoDto.getTodoDeadLine());
            System.out.println(todoDto.getTodoState().toString());
        }

        assertEquals(todoDtoList.size(), todoIdList.size());
    }

    @Test
    @DisplayName("개인 할 일 수정 테스트")
    public void updateTodo(){
        Member member = savedMember();
        TodoDto todoDto = savedTodoDto();
        Long todoId = todoService.saveTodo(todoDto, member.getEmail());

        Todo savedTodo = todoRepository.findById(todoId).orElseThrow(EntityNotFoundException::new);
        System.out.println(savedTodo);

        todoDto.setTodoName("할 일 수정 테스트");
        todoDto.setTodoUpdateDate(LocalDateTime.now());
        todoDto.setTodoState(TodoState.Done);

//        Long updatedTodoId = todoService.updateTodo(todoId, todoFormDto, member.getEmail());
//
//        Todo updatedTodo = todoRepository.findById(updatedTodoId).orElseThrow(EntityNotFoundException::new);
//        System.out.println(updatedTodo);
    }

    @Test
    @DisplayName("개인 할 일 삭제 테스트")
    public void deleteTodo() {
        Member member = savedMember();
        TodoDto todoDto = savedTodoDto();
        Long todoId = todoService.saveTodo(todoDto, member.getEmail());

        Long deleteTodoId = todoService.deleteTodo(todoId, member.getEmail());

        assertEquals(todoId, deleteTodoId);
    }

}