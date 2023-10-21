package hello.advanced.aspect;

import hello.advanced.bean.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "OrderControllerAspect") // bean 이름 충돌을 회피하기 위해 지정
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping("/aspect/request")
    public String orderItem(String itemId) {
        service.orderItem(itemId);
        return "ok";
    }
}
