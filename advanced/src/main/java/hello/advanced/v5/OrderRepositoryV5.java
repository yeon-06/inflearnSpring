package hello.advanced.v5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV5 {

    private final TraceTemplate traceTemplate;

    public void save(String itemId) {
        traceTemplate.execute("OrderRepositoryV5.save()", () -> {
            if ("ex".equals(itemId)) {
                throw new IllegalStateException("예외 발생");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return null;
        });
    }
}
