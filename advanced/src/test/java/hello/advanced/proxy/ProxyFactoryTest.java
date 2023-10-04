package hello.advanced.proxy;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

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

    @DisplayName("advisor 사용 예제")
    @Test
    void advisor() {
        ProxyFactory proxyFactory = new ProxyFactory(new NoInterfaceClass());
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        NoInterfaceClass proxy = (NoInterfaceClass) proxyFactory.getProxy();

        proxy.test();
    }


    @DisplayName("pointcut 직접 구현 예제")
    @Test
    void customPointcut() {
        ProxyFactory proxyFactory = new ProxyFactory(new NoInterfaceClass());
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        NoInterfaceClass proxy = (NoInterfaceClass) proxyFactory.getProxy();

        proxy.test();
    }

    // 스프링이 잘 만들어둔 포인트컷이 많으므로 실제로 구현할 일은 거의 없다.
    static class MyPointcut implements Pointcut {

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    static class MyMethodMatcher implements MethodMatcher {

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            System.out.printf("method = %s, targetClass = %s\n", method.getName(), targetClass.getName());
            return method.getName().equals("test");
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            throw new UnsupportedOperationException();
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
