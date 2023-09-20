package hello.advanced.proxy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    @Override
    public void orderItem(String itemId) {
        repository.save(itemId);
    }
}
