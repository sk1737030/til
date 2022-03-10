package til.generic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MainTest {

    @DisplayName("공변")
    @Test
    void covariantTest() {
        // 공변
        Sitter covariant = new Sitter();
        House<Pug> pug = new House<>();
        House<Siba> siba = new House<>();
        covariant.giveSnack(pug, new Snack());
        covariant.giveSnack(siba, new Snack());
    }


    @DisplayName("공변의 문제점")
    @Test
    void problemCovariantTest() {
        // 공변
        House<Pug> pug = new House<>();
        House<? extends Dog> dogHouse = pug;
        // dogHouse.push(new Pug()); // Compile Error
    }

    @DisplayName("반공변")
    @Test
    void contravariant() {
        // 반공변
        House<Pug> pug = new House<>();
        House<? super Pug> pugh = pug;
        pugh.push(new Pug());

        House<Dog> dog = new House<>();
        House<? super Pug> pughh = dog;
        pughh.push(new Pug());
    }

    @DisplayName("반공변 문제")
    @Test
    void problemOfContravariant() {
        // 반공변
        House<Pug> pug = new House<>();
        House<? super Pug> pugh = pug;
        pugh.push(new Pug());

        // House<Pug> hosuePug = pugh.get(0); // Compile Error

    }

}