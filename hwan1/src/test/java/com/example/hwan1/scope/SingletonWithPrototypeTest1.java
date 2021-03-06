package com.example.hwan1.scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();

        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();

        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUserPrototype() {
        AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int logic1 = clientBean1.logic();
        assertThat(logic1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int logic2 = clientBean2.logic();
        assertThat(logic2).isEqualTo(2);
    }

    @Test
    void singletonClientUserPrototype2() {
        AnnotationConfigApplicationContext ac
            = new AnnotationConfigApplicationContext(ClientBean2.class, PrototypeBean.class);
        ClientBean2 clientBean1 = ac.getBean(ClientBean2.class);
        int logic1 = clientBean1.logic();
        assertThat(logic1).isEqualTo(1);

        ClientBean2 clientBean2 = ac.getBean(ClientBean2.class);
        int logic2 = clientBean2.logic();
        assertThat(logic2).isEqualTo(1);
    }

    // SignleTon 내부에서 Prototype을 요청하며 안된다.
    @Scope("singleton")
    static class ClientBean {

        private final PrototypeBean prototypeBean;

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    // 쓰려면 따로 빈을 주입받아서 써야한다.
    @Scope("singleton")
    static class ClientBean2 {

        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeansProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeansProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destory() {
            System.out.println("Destory.init " + this);
        }
    }

}
