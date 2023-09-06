package hello.advanced.v5;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV5 {

    private final OrderServiceV5 service;
    private final TraceTemplate traceTemplate;

    @GetMapping("/v5/request")
    public String orderItem(String itemId) {
        return traceTemplate.execute("OrderControllerV5.orderItem()", () -> {
            service.orderItem(itemId);
            return "ok";
        });
    }
}
