package com.teamstation.teamstation.service;

import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectSchedule;
import com.teamstation.teamstation.entity.ProjectSession;
import com.teamstation.teamstation.repository.ProjectScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectScheduleService {
    private final ProjectScheduleRepository projectScheduleRepository;

    public ProjectScheduleService(ProjectScheduleRepository projectScheduleRepository) {
        this.projectScheduleRepository = projectScheduleRepository;
    }

    public ProjectSchedule createProjectSchedule(ProjectSchedule projectSchedule) {
        return projectScheduleRepository.save(projectSchedule);
    }

    public List<ProjectSchedule> getProjectSchedulesByProject(Project project) {
        return projectScheduleRepository.findByProject(project);
    }
}
