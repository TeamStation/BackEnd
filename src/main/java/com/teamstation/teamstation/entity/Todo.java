package com.teamstation.teamstation.entity;

import com.teamstation.teamstation.constant.TodoState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Todo {
    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String todoName;

    private LocalDateTime todoRegDate;

    private LocalDateTime todoUpdateDate;

    private LocalDateTime todoDeadLine;

    @Enumerated(EnumType.STRING)
    private TodoState todoState;

    @Builder
    public Todo(Long id, String todoName, LocalDateTime todoRegDate, LocalDateTime todoUpdateDate, LocalDateTime todoDeadLine, TodoState todoState) {
        this.id = id;
        this.todoName = todoName;
        this.todoRegDate = todoRegDate;
        this.todoUpdateDate = todoUpdateDate;
        this.todoDeadLine = todoDeadLine;
        this.todoState = todoState;
    }
}
