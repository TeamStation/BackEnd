package com.teamstation.teamstation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ProjectSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Session session;

    @ManyToOne
    private Project project;

    public void setProject(Project project) {
        this.project = project;
    }

}
