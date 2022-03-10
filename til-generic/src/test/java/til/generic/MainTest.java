package til.generic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        House<Dog> dog = new House<>();
        House<? super Dog> pugh = dog;
        pugh.push(new Pug());
        pugh.push(new Dog());
        // Pug housePug = pugh.get(0); // Compile Error

        House<Pug> pug = new House<>();
        House<? super Pug> pugh2 = pug;
        pugh2.push(new Pug());
        // pugh2.push(new Dog()); // Compile Error
        // Pug housePug = pugh2.get(0); // Compile Error

    }

    @DisplayName("자바에서는 어디서 쓰일까")
    @Test
    void commonExample() {
        List<Integer> src = new ArrayList<>();
        List<Number> dest = new ArrayList<>();

        Collections.copy(dest, src);
    }

}