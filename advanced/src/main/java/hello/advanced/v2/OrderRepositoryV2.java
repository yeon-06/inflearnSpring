package hello.advanced.v2;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

    private final HelloTraceV2 trace;

    public void save(String itemId, TraceId traceId) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = trace.begin(traceId, "OrderRepositoryV2.save()");
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
