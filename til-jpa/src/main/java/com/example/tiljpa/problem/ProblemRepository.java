package com.example.tiljpa.problem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Member, Long> {

    @Query("SELECT distinct m FROM Member m join fetch m.orders")
    List<Member> findAllFetch();

    Member findByMemberEmail(String memberEmail);
}
