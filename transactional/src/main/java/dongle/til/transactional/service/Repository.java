package dongle.til.transactional.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Order, Long> {

}
