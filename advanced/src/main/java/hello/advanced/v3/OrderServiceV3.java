package hello.advanced.v3;

import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 repository;
    private final FieldLogTrace trace;

    public void orderItem(String itemId) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = trace.begin("OrderServiceV3.orderItem()");
            repository.save(itemId);
            trace.end(traceStatus);
        } catch (Exception e) {
            trace.exception(traceStatus, e);
            throw e;
        }
    }
}
