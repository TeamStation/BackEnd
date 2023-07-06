package com.teamstation.teamstation.entity;

import com.teamstation.teamstation.constant.SessionState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionName;

    private String sessionDateTime;

    @Enumerated(EnumType.STRING)
    private SessionState sessionState;

}

