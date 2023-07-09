package com.teamstation.teamstation.controller;

import com.teamstation.teamstation.dto.ProjectDto;
import com.teamstation.teamstation.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private ProjectService projectService;

//    @PostMapping("/new")
//    public @ResponseBody ResponseEntity saveProject(@RequestBody ProjectDto projectDto, BindingResult bindingResult, Principal principal) {
//
//    }
//
//    @GetMapping("/projects")
//    public List<ProjectDto> getProjectList(Principal principal) {
//
//    }
//
//    @GetMapping("/projects/{projectId}")
//    public ProjectDto getProjectDtl(@PathVariable("projectId") Long projectId){
//
//    }
}
