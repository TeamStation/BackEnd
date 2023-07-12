package com.teamstation.teamstation.dto;

import com.teamstation.teamstation.constant.ProjectState;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ProjectDto {
    private String name;
    private String detail;
//    private LocalDateTime regDate;
//
//    private LocalDateTime updateDate;
    private String deadLine;
    private ProjectState projectState;
    private List<String> projectMemberEmailList = new ArrayList<>();

}
