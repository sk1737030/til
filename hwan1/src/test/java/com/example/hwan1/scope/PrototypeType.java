package com.example.hwan1.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PrototypeType {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototypeBean1");
        PrototypeBean prototypeType1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeType2 = ac.getBean(PrototypeBean.class);
        System.out.println("prototypeType1 = " + prototypeType1);
        System.out.println("prototypeType2 = " + prototypeType2);

        assertThat(prototypeType1).isNotSameAs(prototypeType2);

        // bean 을 close해도 destory가 호출안된다.
        ac.close();
    }


    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("Prototype.init");
        }

        @PreDestroy
        public void destory() {
            System.out.println("Prototype.destory");
        }
    }

}
