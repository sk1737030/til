package my.book.bumgun.tdd.chap07.register;

public interface UserRepository {

    void save(User user);

    User findById(String id);
}
