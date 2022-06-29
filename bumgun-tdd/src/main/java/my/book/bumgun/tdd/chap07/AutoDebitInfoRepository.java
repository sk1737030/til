package my.book.bumgun.tdd.chap07;


public interface AutoDebitInfoRepository {

    void save(AutoDebitInfo info);

    AutoDebitInfo findOne(String userId);
}