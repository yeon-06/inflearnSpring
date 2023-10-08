package hello.advanced.bean;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

class BeanTest {

    @Test
    void basicConfig() {
        var applicationContext = new AnnotationConfigApplicationContext(A.class);
        var aClass = applicationContext.getBean("A", A.class);
        aClass.hello();
    }

    @Component("A") // bean 이름 지정
    static class A {

        public void hello() {
            System.out.println("A");
        }
    }
}
