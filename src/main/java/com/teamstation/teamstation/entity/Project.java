package com.teamstation.teamstation.entity;

import com.teamstation.teamstation.constant.ProjectState;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectName;
    private String projectDetail;

    private LocalDateTime projectRegDate;

    private LocalDateTime projectUpdateDate;

    @Enumerated(EnumType.STRING)
    private ProjectState projectState;
}
