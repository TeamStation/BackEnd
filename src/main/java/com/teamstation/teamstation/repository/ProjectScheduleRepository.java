package com.teamstation.teamstation.repository;


import com.teamstation.teamstation.entity.Project;
import com.teamstation.teamstation.entity.ProjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectScheduleRepository extends JpaRepository<ProjectSchedule, Long> {
    List<ProjectSchedule> findByProject(Project project);
}
