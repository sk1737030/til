package com.example.hwan1.hello.core.member.repository;

import com.example.hwan1.hello.core.member.Member;
import org.springframework.stereotype.Component;

@Component
public class MemoryMemberRepository implements MemberRepository {

    @Override
    public Member findById(Long memberId) {
        return null;
    }

    @Override
    public void save(Member member) {

    }
}
