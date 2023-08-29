package hello.advanced.v3;

import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV3 {

    private final FieldLogTrace trace;

    public void save(String itemId) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = trace.begin("OrderRepositoryV3.save()");
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
