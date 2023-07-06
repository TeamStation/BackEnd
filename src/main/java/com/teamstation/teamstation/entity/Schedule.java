package com.teamstation.teamstation.entity;


import com.teamstation.teamstation.constant.ScheduleColor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Schedule {
    @Id
    @Column(name = "schedule_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String scheduleName;
    private String scheduleStartDate;
    private String scheduleEndDate;

    @Enumerated(EnumType.STRING)
    private ScheduleColor scheduleColor;


}
