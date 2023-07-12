package com.teamstation.teamstation.controller;

import com.teamstation.teamstation.dto.TodoDto;
import com.teamstation.teamstation.dto.TodoFormDto;
import com.teamstation.teamstation.entity.Member;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;
    private final MemberRepository memberRepository;

    @PostMapping("/{userId}/new")
    public @ResponseBody ResponseEntity saveTodo(@RequestBody TodoFormDto todoFormDto, BindingResult bindingResult,
                                                 @PathVariable("userId") Long userId) {
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError: fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

//        log.info("user: ", user);
//        log.info("user name: ", user.getUsername());
        Member member = memberRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        String email = member.getEmail();
        Long todoId;

        try{
            TodoDto todoDto = new TodoDto();
            todoDto = todoDto.createTodo(todoFormDto);
            todoId = todoService.saveTodo(todoDto, email);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(todoId, HttpStatus.OK);
    }

    @GetMapping("/todos/{userId}")
    public List<TodoDto> getTodoList(@PathVariable("userId") Long userId){
        log.info("path variable userId: "+userId.toString());
        Member member = memberRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        log.info("member: "+member.getMemberName());
        String email = member.getEmail();
        log.info("controller email: "+email);
        return todoService.getProceedingTodoList(email);
    }

    @GetMapping("/todos/{userId}/{personalTodoId}")
    public TodoDto getTodoDtl(@PathVariable("personalTodoId") Long todoId){
        return todoService.getTodoDtl(todoId);
    }

    @PostMapping("/{userId}/{personalTodoId}")
    public @ResponseBody ResponseEntity updateTodo(@RequestBody TodoFormDto todoFormDto,
                                                   @PathVariable("userId") Long userId,
                                                   @PathVariable("personalTodoId") Long todoId) {
        Member member = memberRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        if(!todoService.validateTodo(todoId, member.getEmail())){
            return new ResponseEntity<String>("수정할 수 없습니다.", HttpStatus.FORBIDDEN);
        }

        if(todoFormDto.getTodoName().isEmpty()){
            return new ResponseEntity<String>("할 일 제목 입력은 필수입니다.", HttpStatus.BAD_REQUEST);
        }

        if(todoFormDto.getTodoDeadLine().isEmpty()) {
            return new ResponseEntity<String>("마감 기한을 선택해주세요.", HttpStatus.BAD_REQUEST);
        }

        Long updatedTodoId;

        try{
            updatedTodoId = todoService.updateTodo(todoId, todoFormDto, member.getEmail());
        }catch(Exception e) {
            return new ResponseEntity<String>("할 일 수정 중 에러가 발생했습니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(updatedTodoId, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{personalTodoId}")
    public @ResponseBody ResponseEntity deleteTodo(@PathVariable("userId") Long userId,
                                                   @PathVariable("personalTodoId") Long todoId) {
        Member member = memberRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        if(!todoService.validateTodo(todoId, member.getEmail())){
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        todoService.deleteTodo(todoId, member.getEmail());
        return new ResponseEntity<Long>(todoId, HttpStatus.OK);
    }

}
