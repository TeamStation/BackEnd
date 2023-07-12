package com.teamstation.teamstation.entity;

import com.teamstation.teamstation.constant.TodoState;
import com.teamstation.teamstation.dto.TodoDto;
import com.teamstation.teamstation.dto.TodoFormDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
@Getter @Setter
@ToString
public class Todo {
    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String todoName;

    private LocalDateTime todoRegDate;

    private LocalDateTime todoUpdateDate;

    private String todoDeadLine;

    @Enumerated(EnumType.STRING)
    private TodoState todoState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Todo createTodo(TodoDto todoDto, Member member) {
        Todo todo = new Todo();
        todo.todoName = todoDto.getTodoName();
        todo.todoRegDate = todoDto.getTodoRegDate();
        todo.todoUpdateDate = todoDto.getTodoUpdateDate();
        todo.todoDeadLine = todoDto.getTodoDeadLine();
        todo.todoState = todoDto.getTodoState();
        todo.member = member;
        return todo;
    }

    public void updateTodo(TodoFormDto todoFormDto){
        this.todoName = todoFormDto.getTodoName();
        this.todoUpdateDate = LocalDateTime.now();
        this.todoDeadLine = todoFormDto.getTodoDeadLine();
        this.todoState = todoFormDto.getTodoState();
    }
}
