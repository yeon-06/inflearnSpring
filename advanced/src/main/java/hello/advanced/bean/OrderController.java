package hello.advanced.bean;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping("/bean/request")
    public String orderItem(String itemId) {
        service.orderItem(itemId);
        return "ok";
    }
}
