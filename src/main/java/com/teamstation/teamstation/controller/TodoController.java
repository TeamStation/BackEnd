package com.teamstation.teamstation.controller;

import com.teamstation.teamstation.dto.TodoDto;
import com.teamstation.teamstation.service.TodoService;
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
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/new")
    public @ResponseBody ResponseEntity saveTodo(@RequestBody TodoDto todoDto, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError: fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long todoId;

        try{
            todoId = todoService.saveTodo(todoDto, email);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(todoId, HttpStatus.OK);
    }

    @GetMapping("/todos")
    public List<TodoDto> getTodoList(Principal principal){
        String email = principal.getName();
        return todoService.getProceedingTodoList(email);
    }

    @GetMapping("/todos/{personalTodoId}")
    public TodoDto getTodoDtl(@PathVariable("personalTodoId") Long todoId){
        return todoService.getTodoDtl(todoId);
    }

    @PostMapping("/{personalTodoId}")
    public @ResponseBody ResponseEntity updateTodo(@Validated TodoDto todoDto,
                                                   @RequestParam("personalTodo") Long todoId, Principal principal) {
        if(!todoService.validateTodo(todoId, principal.getName())){
            return new ResponseEntity<String>("수정할 수 없습니다.", HttpStatus.FORBIDDEN);
        }

        if(todoDto.getTodoName().isEmpty()){
            return new ResponseEntity<String>("할 일 제목 입력은 필수입니다.", HttpStatus.BAD_REQUEST);
        }

        if(todoDto.getTodoDeadLine().isEmpty()) {
            return new ResponseEntity<String>("마감 기한을 선택해주세요.", HttpStatus.BAD_REQUEST);
        }

        Long updatedTodoId;

        try{
            updatedTodoId = todoService.updateTodo(todoDto, principal.getName());
        }catch(Exception e) {
            return new ResponseEntity<String>("할 일 수정 중 에러가 발생했습니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(updatedTodoId, HttpStatus.OK);
    }

    @DeleteMapping("/{personalTodoId}")
    public @ResponseBody ResponseEntity deleteTodo(@RequestParam("personalTodo") Long todoId, BindingResult bindingResult, Principal principal) {
        if(!todoService.validateTodo(todoId, principal.getName())){
            return new ResponseEntity<String>("삭제 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        todoService.deleteTodo(todoId, principal.getName());
        return new ResponseEntity<Long>(todoId, HttpStatus.OK);
    }

}
