package com.teamstation.teamstation.service;

import com.teamstation.teamstation.entity.PersonalTodo;
import com.teamstation.teamstation.repository.MemberRepository;
import com.teamstation.teamstation.repository.PersonalTodoRepository;
import com.teamstation.teamstation.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PersonalTodoService {

    private final TodoRepository todoRepository;
    private final PersonalTodoRepository personalTodoRepository;
    private final MemberRepository memberRepository;

}
