package com.teamstation.teamstation.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "personaltodo")
@Getter @Setter
@ToString
public class PersonalTodo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String category;

    public static PersonalTodo createPersonalTodo(Todo todo, Member member, String category){
        PersonalTodo personalTodo = new PersonalTodo();
        personalTodo.todo = todo;
        personalTodo.member = member;
        personalTodo.category = category;

        return  personalTodo;
    }

}
