package dongle.til.transactional.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChildService {

  private final Repository repository;

  public ChildService(Repository repository) {
    this.repository = repository;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void childExecute() {
    repository.save(new Order("child"));
    throw new RuntimeException();
  }

  //  @Transactional
  public void childExecute2() {
    repository.save(new Order("child2"));
    throw new RuntimeException();
  }


}
