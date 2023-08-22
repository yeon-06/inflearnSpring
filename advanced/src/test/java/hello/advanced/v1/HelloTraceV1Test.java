package hello.advanced.v1;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class HelloTraceV1Test {

    // 콘솔에 출력되는 결과를 확인하기 위해 작성한 코드일뿐, 유의미한 테스트가 아니다.

    @Test
    void begin() {
        HelloTraceV1 trace = new HelloTraceV1();
        trace.begin("hello");
    }

    @Test
    void end() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus hello = trace.begin("hello");
        trace.end(hello);
    }

    @Test
    void exception() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus hello = trace.begin("hello");
        trace.exception(hello, new IllegalStateException());
    }
}
