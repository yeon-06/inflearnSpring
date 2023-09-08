package hello.advanced.proxy;

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
}
