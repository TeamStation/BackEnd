package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.constant.TodoState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class TodoFormDto {

    private String todoName;
    private String todoDeadLine;
    private TodoState todoState;
}
