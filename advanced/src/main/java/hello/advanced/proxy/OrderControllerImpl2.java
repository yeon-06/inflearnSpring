package hello.advanced.proxy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping
@ResponseBody
public class OrderControllerImpl2 implements OrderController {

    private OrderServiceImpl2 service;

    OrderControllerImpl2() {
    }

    public OrderControllerImpl2(OrderServiceImpl2 service) {
        this.service = service;
    }

    @GetMapping("/cglib/request")
    public String orderItem(String itemId) {
        service.orderItem(itemId);
        return "ok";
    }
}
