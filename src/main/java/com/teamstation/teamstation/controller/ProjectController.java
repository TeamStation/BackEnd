package com.teamstation.teamstation.controller;

import com.teamstation.teamstation.dto.ProjectDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final MemberRepository memberRepository;
    private final ProjectService projectService;

    @PostMapping("/{userId}/new")
    public @ResponseBody ResponseEntity saveProject(@RequestBody ProjectDto projectDto, BindingResult bindingResult,
                                                    @PathVariable("userId") Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError: fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = member.getEmail();
        Long projectId;

        try{
            projectId = projectService.saveProject(projectDto, email);
        }catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(projectId, HttpStatus.OK);
    }

    @GetMapping("/projects/{userId}")
    public List<Long> getProjectList(@PathVariable("userId") Long userId) {
        Member member = memberRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return projectService.getProjectIdList(member.getEmail());
    }

    @GetMapping("/projects/{projectId}")
    public ProjectDto getProjectDtl(@PathVariable("projectId") Long projectId){
        return projectService.getProjectDtl(projectId);
    }

    @GetMapping("/projects/proceeding-count")
    public int getProjectProceedingCount(Principal principal){
        return projectService.getProceedingProjectSize(principal.getName());
    }

    @GetMapping("/projects/done-count")
    public int getProjectDoneCount(Principal principal){
        return projectService.getDoneProjectSize(principal.getName());
    }
}
