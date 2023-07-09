package com.teamstation.teamstation.entity;

import com.teamstation.teamstation.constant.ProjectState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@Getter
@Setter
@ToString
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String projectName;
    private String projectDetail;

    private LocalDateTime projectRegDate;

    private LocalDateTime projectUpdateDate;

    private String projectDeadLine;

    @Enumerated(EnumType.STRING)
    private ProjectState projectState;
}
