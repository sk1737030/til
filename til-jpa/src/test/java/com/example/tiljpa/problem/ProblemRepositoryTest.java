package com.example.tiljpa.problem;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProblemRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    private ProblemRepository problemRepository;

    @Test
    void save() {
        for (int i = 0; i < 2; i++) {
            Member memFixture = new Member( i + "@naver.com");
            Order orderFixture = new Order(i + "delivery");
            memFixture.addOrder(orderFixture);
            problemRepository.save(memFixture);
            em.flush();
            em.clear();
        }

        // Given
        List<Member> members = problemRepository.findAllFetch();

        for (Member member : members) {
            for (Order order : member.getOrders()) {
                System.out.println(order.getOrderStatus());
            }
        }
    }

}