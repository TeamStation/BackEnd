package com.teamstation.teamstation.service;

import com.teamstation.teamstation.dto.TodoDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.entity.Todo;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Long saveTodo(TodoDto todoDto, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Todo todo = new Todo();
        todo = todo.createTodo(todoDto.getTodoName(), todoDto.getTodoUpdateDate(), todoDto.getTodoDeadLine(), todoDto.getTodoState(), member);
        todoRepository.save(todo);
        return todo.getId();
    }

    public List<TodoDto> getTodoList(String email){
        List<Todo> todoList = todoRepository.findTodoList(email);
        List<TodoDto> todoDtoList = new ArrayList<>();
        for (Todo todo : todoList) {
            TodoDto todoDto = new TodoDto(todo);
            todoDtoList.add(todoDto);
        }
        return todoDtoList;
    }

    public boolean validateTodo(Long todoId, String email){
        Member curMember = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        Todo curTodo = todoRepository.findById(todoId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = curTodo.getMember();

        return StringUtils.equals(curMember.getEmail(), savedMember.getEmail());
    }

    public Long updateTodo(TodoDto todoDto, String email){
        Todo todo = todoRepository.findById(todoDto.getId()).orElseThrow(EntityNotFoundException::new);
        if (validateTodo(todo.getId(), email)){
            todo.updateTodo(todoDto);
        }
        return todo.getId();
    }

    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(EntityNotFoundException::new);
        todoRepository.delete(todo);
    }

}

