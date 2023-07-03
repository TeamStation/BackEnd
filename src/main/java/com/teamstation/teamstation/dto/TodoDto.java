package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.constant.TodoState;

import java.time.LocalDateTime;

import com.teamstation.teamstation.entity.Todo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 할 일 데이터 정보를 전달하는 DTO
public class TodoDto {
    private Long id;
    private String todoName;
    private LocalDateTime todoRegDate;
    private LocalDateTime todoUpdateDate;
    private LocalDateTime todoDeadLine;
    private TodoState todoState;

    public Todo toEntity(TodoDto todoDTO) {
        Todo todo = new Todo();
        todo.setId(todoDTO.getId());
        todo.setTodoName(todoDTO.getTodoName());
        todo.setTodoRegDate(todoDTO.getTodoRegDate());
        todo.setTodoUpdateDate(todoDTO.getTodoUpdateDate());
        todo.setTodoDeadLine(todoDTO.getTodoDeadLine());
        todo.setTodoState(todoDTO.getTodoState());

        return todo;
    }

    public static TodoDto toDTO(Todo todo) {
        TodoDto todoDTO = new TodoDto();
        todoDTO.setId(todo.getId());
        todoDTO.setTodoName(todo.getTodoName());
        todoDTO.setTodoRegDate(todo.getTodoRegDate());
        todoDTO.setTodoUpdateDate(todo.getTodoUpdateDate());
        todoDTO.setTodoDeadLine(todo.getTodoDeadLine());
        todoDTO.setTodoState(todo.getTodoState());
        return todoDTO;
    }
}