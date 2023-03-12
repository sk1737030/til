package dongle.til.transactional.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParentService {

  private final ChildService childService;
  private final Repository repository;

  public ParentService(ChildService childService, Repository repository) {
    this.childService = childService;
    this.repository = repository;
  }

  @Transactional
  void parentExecute() {
    repository.save(new Order("test"));
    childService.childExecute();
    repository.save(new Order("test2"));
  }

}
