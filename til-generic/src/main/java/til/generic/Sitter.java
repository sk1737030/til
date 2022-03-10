package til.generic;

import java.util.List;

public class Sitter {

    public void giveSnack(House<? extends Dog> dog, Snack snack) {
        List<? extends Dog> all = dog.getAll();
    }
}
