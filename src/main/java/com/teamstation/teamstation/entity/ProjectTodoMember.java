package com.teamstation.teamstation.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.*;

@Entity
public class ProjectTodoMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private ProjectTodo projectTodo;
}
