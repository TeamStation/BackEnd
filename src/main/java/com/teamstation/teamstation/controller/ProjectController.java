package com.teamstation.teamstation.controller;

import com.teamstation.teamstation.dto.ProjectDto;
import com.teamstation.teamstation.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @PostMapping("/new")
    public @ResponseBody ResponseEntity saveProject(@RequestBody ProjectDto projectDto, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError: fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long projectId;

        try{
            projectId = projectService.saveProject(projectDto, email);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(projectId, HttpStatus.OK);
    }

    @GetMapping("/projects")
    public List<Long> getProjectList(Principal principal) {
        return projectService.getProjectIdList(principal.getName());
    }

    @GetMapping("/projects/{projectId}")
    public ProjectDto getProjectDtl(@PathVariable("projectId") Long projectId){
        return projectService.getProjectDtl(projectId);
    }

    @GetMapping("/projects/proceeding-count")
    public int getProjectProceedingCount(Principal principal){
        return projectService.getProceedingProjectSize(principal.getName());
    }

    @PostMapping("/projects/proceeding-count")
    public int getProjectDoneCount(Principal principal){
        return projectService.getDoneProjectSize(principal.getName());
    }
}
