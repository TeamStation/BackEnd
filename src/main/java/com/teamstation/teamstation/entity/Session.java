package com.teamstation.teamstation.entity;

import com.teamstation.teamstation.constant.SessionState;

import javax.persistence.*;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionName;

    private String sessionDateTime;

    @Enumerated(EnumType.STRING)
    private SessionState sessionState;

}

