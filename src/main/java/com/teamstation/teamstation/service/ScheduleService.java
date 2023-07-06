package com.teamstation.teamstation.service;

import com.teamstation.teamstation.entity.Schedule;
import com.teamstation.teamstation.entity.Session;
import com.teamstation.teamstation.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
}
