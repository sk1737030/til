package my.book.bumgun.tdd.chap07;

public class StubAutoDebitInfoRepository implements AutoDebitInfoRepository {

    @Override
    public void save(AutoDebitInfo info) {

    }

    @Override
    public AutoDebitInfo findOne(String userId) {
        return null;
    }
}
