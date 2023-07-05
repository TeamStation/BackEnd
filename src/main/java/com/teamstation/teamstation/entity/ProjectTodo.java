package com.teamstation.teamstation.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ProjectTodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Todo todo;

    @ManyToOne
    private Project project;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Member> members;

    private String category;

    @Builder
    public ProjectTodo(Todo todo, Project project, List<Member> members, String category) {
        this.todo = todo;
        this.project = project;
        this.members = members;
        this.category = category;
    }
}
