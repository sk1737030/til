package com.example.hwan1.hello.core.member;

import com.example.hwan1.hello.core.grade.Grade;

public class Member {

    private Long id;
    private String userName;
    private Grade grade;

    public Member(Long id, String userName, Grade grade) {
        this.id = id;
        this.userName = userName;
        this.grade = grade;
    }

    public Grade getGrade() {
        return grade;
    }
}
