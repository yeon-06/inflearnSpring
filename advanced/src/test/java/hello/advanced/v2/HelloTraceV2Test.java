package hello.advanced.v2;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class HelloTraceV2Test {

    // 콘솔에 출력되는 결과를 확인하기 위해 작성한 코드일뿐, 유의미한 테스트가 아니다.

    @Test
    void begin_end() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus traceStatus1 = trace.begin("hello1");
        TraceStatus traceStatus2 = trace.begin(traceStatus1.traceId(), "hello2");
        trace.end(traceStatus2);
        trace.end(traceStatus1);
    }

    @Test
    void begin_exception() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus traceStatus1 = trace.begin("hello1");
        TraceStatus traceStatus2 = trace.begin(traceStatus1.traceId(), "hello2");
        trace.exception(traceStatus2, new IllegalStateException());
        trace.exception(traceStatus1, new IllegalStateException());
    }
}
