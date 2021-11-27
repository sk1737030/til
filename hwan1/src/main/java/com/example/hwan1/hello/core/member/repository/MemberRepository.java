package com.example.hwan1.hello.core.member.repository;


import com.example.hwan1.hello.core.member.Member;

public interface MemberRepository {

    Member findById(Long memberId);

    void save(Member member);
}
