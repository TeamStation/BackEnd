package com.teamstation.teamstation.entity;

import com.teamstation.teamstation.constant.TodoState;
import com.teamstation.teamstation.dto.TodoDto;
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

    public Todo createTodo(String todoName, LocalDateTime todoUpdateDate, String todoDeadLine, TodoState todoState, Member member) {
        Todo todo = new Todo();
        todo.todoName = todoName;
//        todo.todoRegDate = todoRegDate;
        todo.todoUpdateDate = todoUpdateDate;
        todo.todoDeadLine = todoDeadLine;
        todo.todoState = todoState;
        todo.member = member;
        return todo;
    }

    public void updateTodo(TodoDto todoDto){
        this.todoName = todoDto.getTodoName();
        this.todoUpdateDate = todoDto.getTodoUpdateDate();
        this.todoDeadLine = todoDto.getTodoDeadLine();
        this.todoState = todoDto.getTodoState();
    }
}
