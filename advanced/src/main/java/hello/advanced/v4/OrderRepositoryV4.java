package hello.advanced.v4;

import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {

    private final FieldLogTraceV4 trace;

    public void save(String itemId) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = trace.begin("OrderRepositoryV4.save()");
            if ("ex".equals(itemId)) {
                throw new IllegalStateException("예외 발생");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            trace.end(traceStatus);
        } catch (Exception e) {
            trace.exception(traceStatus, e);
            throw e;
        }
    }
}
