package hello.advanced.proxy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface OrderController {

    @GetMapping("/dynamic/request")
    String orderItem(@RequestParam("itemId") String itemId);
}
