package com.teamstation.teamstation.dto;

public class ProjectDto {
    private Long id;
    private String name;

    public ProjectDto() {
        // 기본 생성자 추가
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
