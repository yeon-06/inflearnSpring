package hello.advanced.proxy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService service;

    @Override
    public String orderItem(String itemId) {
        service.orderItem(itemId);
        return "ok";
    }
}
