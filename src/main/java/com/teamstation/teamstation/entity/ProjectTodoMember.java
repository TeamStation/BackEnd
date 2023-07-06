package com.teamstation.teamstation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class ProjectTodoMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_todo_id")
    private ProjectTodo projectTodo;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setProjectTodo(ProjectTodo projectTodo) {
        this.projectTodo = projectTodo;
    }

    public Member getMember() {
        return member;
    }

    public ProjectTodo getProjectTodo() {
        return projectTodo;
    }
    public ProjectTodoMember() {
    }
    public ProjectTodoMember(Member member, ProjectTodo projectTodo) {
        this.member = member;
        this.projectTodo = projectTodo;
    }
}
