package com.example.hwan1.hello.core.repository;

import java.lang.reflect.Member;

public interface MemberRepository {

    Member findById(Long memberId);

    void save(Member member);
}
