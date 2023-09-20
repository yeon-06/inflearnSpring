package hello.advanced.proxy.dynamic;

import hello.advanced.proxy.OrderController;
import hello.advanced.proxy.OrderControllerImpl;
import hello.advanced.proxy.OrderRepository;
import hello.advanced.proxy.OrderRepositoryImpl;
import hello.advanced.proxy.OrderService;
import hello.advanced.proxy.OrderServiceImpl;
import hello.advanced.v4.FieldLogTraceV4;
import java.lang.reflect.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyConfig {

    @Bean
    public OrderRepository dynamicOrderRepository(FieldLogTraceV4 logTrace) {
        OrderRepository repository = new OrderRepositoryImpl();
        return (OrderRepository) Proxy.newProxyInstance(
            OrderRepository.class.getClassLoader(),
            new Class[]{OrderRepository.class},
            new LogTraceBasicHandler(repository, logTrace)
        );
    }

    @Bean
    public OrderService dynamicOrderService(OrderRepository repository, FieldLogTraceV4 logTrace) {
        OrderService service = new OrderServiceImpl(repository);
        return (OrderService) Proxy.newProxyInstance(
            OrderService.class.getClassLoader(),
            new Class[]{OrderService.class},
            new LogTraceBasicHandler(service, logTrace)
        );
    }

    @Bean
    public OrderController dynamicOrderController(OrderService orderService, FieldLogTraceV4 logTrace) {
        OrderController controller = new OrderControllerImpl(orderService);
        return (OrderController) Proxy.newProxyInstance(
            OrderController.class.getClassLoader(),
            new Class[]{OrderController.class},
            new LogTraceBasicHandler(controller, logTrace)
        );
    }
}
