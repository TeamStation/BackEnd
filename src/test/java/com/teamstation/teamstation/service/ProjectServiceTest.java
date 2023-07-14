package com.teamstation.teamstation.service;

import com.teamstation.teamstation.constant.TodoState;
import com.teamstation.teamstation.dto.TodoDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ProjectServiceTest {

//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    ProjectService projectService;
//
//    @Autowired
//    ProjectRepository projectRepository;
//
//    public Member savedMember(){
//        Member member = new Member();
//        member.setEmail("test@test.com");
//        return memberRepository.save(member);
//    }
//
//    public TodoDto savedTodoDto(){
//        TodoDto todoDto = new TodoDto();
//        todoDto.setTodoName("테스트");
//        todoDto.setTodoDeadLine("2023-07-12");
//        todoDto.setTodoUpdateDate(LocalDateTime.now());
//        LocalDate todoDeadLine = LocalDate.parse(todoDto.getTodoDeadLine(), DateTimeFormatter.ISO_DATE);
//        if (todoDeadLine.isBefore(LocalDate.now())){
//            todoDto.setTodoState(TodoState.Done);
//        } else {
//            todoDto.setTodoState(TodoState.Proceeding);
//        }
//        return todoDto;
//    }
//
//    @Test
//    void getProjectById() {
//    }
//
//    @Test
//    void isUserInProject() {
//    }
//
//    @Test
//    void saveProject() {
//    }
//
//    @Test
//    void getProjectIdList() {
//    }
//
//    @Test
//    void getProceedingProjectSize() {
//    }
//
//    @Test
//    void getDoneProjectSize() {
//    }
//
//    @Test
//    void getProjectDtl() {
//    }
//
//    @Test
//    void validateProject() {
//    }
//
//    @Test
//    void updateProject() {
//    }
//
//    @Test
//    void deleteProject() {
//    }
}