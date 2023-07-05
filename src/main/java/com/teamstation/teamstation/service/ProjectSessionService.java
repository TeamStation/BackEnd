package com.teamstation.teamstation.service;


import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectSession;
import com.teamstation.teamstation.repository.ProjectSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectSessionService {
    private final ProjectSessionRepository projectSessionRepository;

    public ProjectSessionService(ProjectSessionRepository projectSessionRepository) {
        this.projectSessionRepository = projectSessionRepository;
    }

    public ProjectSession createProjectSession(ProjectSession projectSession) {
        return projectSessionRepository.save(projectSession);
    }

    public List<ProjectSession> getProjectSessionsByProject(Project project) {
        return projectSessionRepository.findByProject(project);
    }

    public ProjectSession getProjectSessionById(Long sessionId) {
        return projectSessionRepository.findById(sessionId).orElse(null);
    }

    public void deleteProjectSession(ProjectSession projectSession) {
        projectSessionRepository.delete(projectSession);
    }

    public ProjectSession updateProjectSession(ProjectSession projectSession) {
        return projectSessionRepository.save(projectSession);
    }

}
