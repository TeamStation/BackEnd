package com.teamstation.teamstation.entity;

import javax.persistence.*;

@Entity
public class ProjectSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Session session;

    @ManyToOne
    private Project project;

}
