package dongle.til.transactional;

import dongle.til.transactional.service.Order;
import dongle.til.transactional.service.ParentService;
import dongle.til.transactional.service.Repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TransactionalApplicationTests {

  @Autowired
  private ParentService parentService;
  @Autowired
  private Repository repository;

  @Test
  void contextLoads() {
    try {
      parentService.parentExecute();
    } catch (Exception e) {

    }

    List<Order> all = repository.findAll();
    System.out.println("-----------------------------");
    all.forEach(order -> System.out.println(order.getName()));
  }

}
