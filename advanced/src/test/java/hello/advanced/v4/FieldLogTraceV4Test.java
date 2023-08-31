package hello.advanced.v4;

import hello.advanced.trace.TraceStatus;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class FieldLogTraceV4Test {

    // 콘솔에 출력되는 결과를 확인하기 위해 작성한 코드일뿐, 유의미한 테스트가 아니다.

    @Test
    void begin_end() {
        FieldLogTraceV4 trace = new FieldLogTraceV4();
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception() {
        FieldLogTraceV4 trace = new FieldLogTraceV4();
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

    // 실행해보면 A와 B가 실행이 잘 구분되는 것을 볼 수 있다.
    @Test
    void concurrency_problem() throws InterruptedException {
        FieldLogTraceV4 trace = new FieldLogTraceV4();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(2);

        executor.submit(() -> execute("A", trace, countDownLatch));
        executor.submit(() -> execute("B", trace, countDownLatch));
        countDownLatch.await();
    }

    private void execute(String executeId, FieldLogTraceV4 trace, CountDownLatch countDownLatch) {
        try {
            TraceStatus status1 = trace.begin(executeId + "1");
            TraceStatus status2 = trace.begin(executeId + "2");
            trace.end(status2);
            trace.end(status1);
        } finally {
            countDownLatch.countDown();
        }
    }
}
