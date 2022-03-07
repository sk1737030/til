package generic.animal;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        Pug pug = new Pug();
        Dog dog = pug;

        System.out.println(pug.getClass()); // class generic.animal.Pug
        System.out.println(dog.getClass()); // class generic.animal.Pug

        Sitter sitter = new Sitter();
        House<Pug> pugHouse = new House<>();
        //sitter.giveSnack(pugHouse, new Snack());
    }
}
