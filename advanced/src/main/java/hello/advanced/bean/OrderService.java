package hello.advanced.bean;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public void orderItem(String itemId) {
        repository.save(itemId);
    }
}
