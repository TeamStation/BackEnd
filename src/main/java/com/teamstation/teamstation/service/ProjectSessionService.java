package com.teamstation.teamstation.service;


import com.teamstation.teamstation.repository.ProjectSessionRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectSessionService {
    private final ProjectSessionRepository projectSessionRepository;

    public ProjectSessionService(ProjectSessionRepository projectSessionRepository) {
        this.projectSessionRepository = projectSessionRepository;
    }
}
