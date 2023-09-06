package hello.advanced.v5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV5 {

    private final OrderRepositoryV5 repository;
    private final TraceTemplate traceTemplate;

    public void orderItem(String itemId) {
        traceTemplate.execute("OrderServiceV5.orderItem()", () -> {
            repository.save(itemId);
            return null;
        });
    }
}
