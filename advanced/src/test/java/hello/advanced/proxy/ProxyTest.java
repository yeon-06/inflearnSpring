package hello.advanced.proxy;

import hello.advanced.proxy.cache_example.CacheProxy;
import hello.advanced.proxy.decorator_example.CheckRunningTimeDecorator;
import hello.advanced.proxy.decorator_example.Component;
import hello.advanced.proxy.decorator_example.DecoratorPatternClient;
import hello.advanced.proxy.decorator_example.RealComponent;
import hello.advanced.proxy.proxy_example.ProxyPatternClient;
import hello.advanced.proxy.proxy_example.RealSubject;
import hello.advanced.proxy.proxy_example.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProxyTest {

    @DisplayName("프록시가 적용되지 않는 일반 호출")
    @Test
    void noProxy() {
        Subject subject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(subject);
        client.execute();
        client.execute();
        client.execute();   // 1초나 걸리는 로직을 3번 호출 -> 3초가 걸린다.
    }

    @DisplayName("접근 제어(캐시 적용)를 위한 프록시 예제")
    @Test
    void cacheProxy() {
        Subject subject = new RealSubject();
        Subject proxy = new CacheProxy(subject);
        ProxyPatternClient client = new ProxyPatternClient(proxy);
        client.execute();
        client.execute();
        client.execute();   // 캐싱을 통해 훨씬 빨리 호출된다.
    }

    @DisplayName("부가 기능(수행 시간 및 결과 출력)을 위한 프록시 예제")
    @Test
    void decorateProxy() {
        Component component = new RealComponent();
        Component decorator = new CheckRunningTimeDecorator(component);
        DecoratorPatternClient client = new DecoratorPatternClient(decorator);
        client.execute();
    }
}
