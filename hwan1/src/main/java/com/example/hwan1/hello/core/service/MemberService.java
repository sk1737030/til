package com.example.hwan1.hello.core.service;

import java.lang.reflect.Member;

public interface MemberService {


    void join(Member member);

    Member findMember(Long memberId);
}
