package til.generic;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // 문제의 코드
        List<Object> before = new ArrayList<>();
        before.add("pub");
        before.add("dog");
        before.add(1);

        for (Object o : before) {
            String substring = ((String) o).substring(0, 1);
            System.out.println(substring);
        }

        List<String> list = new ArrayList<>();
        list.add("pub");
        list.add("dog");
        // list.add(1);

        for (String s : list) {
            String substring = s.substring(0, 1);
            System.out.println(substring);
        }


    }
}
