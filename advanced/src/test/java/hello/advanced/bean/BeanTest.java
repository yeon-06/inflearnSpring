package hello.advanced.bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

class BeanTest {

    @Test
    void basicConfig() {
        var applicationContext = new AnnotationConfigApplicationContext(A.class);
        var aClass = applicationContext.getBean(A.class);
        aClass.hello();

        assertThat(aClass).isNotNull();
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(B.class));
    }

    @Component
    static class A {

        public void hello() {
            System.out.println("A");
        }
    }

    @Test
    void postProcessorConfig() {
        var applicationContext = new AnnotationConfigApplicationContext(A.class, AToBPostProcessor.class);
        var bClass = applicationContext.getBean(B.class);
        bClass.hello();

        assertThat(bClass).isNotNull();
        assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(A.class));
    }

    @Component
    static class AToBPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) {
            if (bean instanceof A) {
                System.out.println("A -> B 클래스 변경");
                return new B();
            }
            return bean;
        }
    }

    static class B {

        public void hello() {
            System.out.println("B");
        }
    }
}
