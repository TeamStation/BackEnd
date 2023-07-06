package com.teamstation.teamstation.repository;


import com.teamstation.teamstation.entity.ProjectSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectScheduleRepository extends JpaRepository<ProjectSchedule, Long> {

}
