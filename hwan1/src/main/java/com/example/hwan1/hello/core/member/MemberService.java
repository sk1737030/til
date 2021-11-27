package com.example.hwan1.hello.core.member;


public interface MemberService {
    void join(Member member);

    Member findMember(Long memberId);
}
