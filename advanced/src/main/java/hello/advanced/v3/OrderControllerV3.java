package hello.advanced.v3;

import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 service;
    private final FieldLogTrace trace;

    @GetMapping("/v3/request")
    public String orderItem(String itemId) {
        TraceStatus traceStatus = null;
        try {
            traceStatus = trace.begin("OrderControllerV3.orderItem()");
            service.orderItem(itemId);
            trace.end(traceStatus);
        } catch (Exception e) {
            trace.exception(traceStatus, e);
            throw e;
        }
        return "ok";
    }
}
