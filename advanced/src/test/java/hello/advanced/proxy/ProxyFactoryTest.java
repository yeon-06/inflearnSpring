package hello.advanced.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스를 구현한 클래스의 프록시는 JDK 다이내믹 프록시로 생성된다.")
    void jdkProxy() {
        ProxyFactory proxyFactory = new ProxyFactory(new AbstractClass());
        proxyFactory.addAdvice(new TimeAdvice());
        InterfaceClass proxy = (InterfaceClass) proxyFactory.getProxy();

        System.out.println(proxy.getClass()); // class hello.advanced.proxy.$Proxy12
        proxy.test();
    }

    static class AbstractClass implements InterfaceClass {

        @Override
        public void test() {
            System.out.println("test");
        }
    }

    interface InterfaceClass {

        void test();
    }

    @Test
    @DisplayName("인터페이스를 구현하지 않은 클래스의 프록시는 CGLIB으로 생성된다.")
    void cglibProxy() {
        ProxyFactory proxyFactory = new ProxyFactory(new NoInterfaceClass());
        proxyFactory.addAdvice(new TimeAdvice());
        NoInterfaceClass proxy = (NoInterfaceClass) proxyFactory.getProxy();

        System.out.println(proxy.getClass()); // class hello.advanced.proxy.ProxyFactoryTest$NoInterfaceClass$$SpringCGLIB$$0
        proxy.test();
    }

    static class NoInterfaceClass {

        public void test() {
            System.out.println("test");
        }
    }

    static class TimeAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            long startTime = System.currentTimeMillis();
            Object result = invocation.proceed();
            long endTime = System.currentTimeMillis();
            System.out.printf("duration = %d\n", endTime - startTime);
            return result;
        }
    }
}
