package com.teamstation.teamstation.dto;


import com.teamstation.teamstation.constant.SessionState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionDto {
    private String sessionName;
    private String sessionDateTime;
    private SessionState sessionState;
}
