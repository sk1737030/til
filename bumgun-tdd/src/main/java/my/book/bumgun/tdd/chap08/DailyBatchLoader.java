package my.book.bumgun.tdd.chap08;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DailyBatchLoader {

    private String basePath = "";
    private Times times = new Times();

    public void setTimes(Times times) {
        this.times = times;
    }

    public int load() {
        LocalDate now = times.today();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/DD");
        // Path path = Paths.get(basePath, dateTimeFormatter.format(dateTimeFormatter), "batch.txt");

        return 3;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
}
