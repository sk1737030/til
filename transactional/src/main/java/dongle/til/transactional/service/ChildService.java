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
  void childExecute() {
    throw new RuntimeException();
  }

}
