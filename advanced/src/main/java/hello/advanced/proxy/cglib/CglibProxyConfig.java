package hello.advanced.proxy.cglib;

import hello.advanced.proxy.OrderControllerImpl2;
import hello.advanced.proxy.OrderRepositoryImpl2;
import hello.advanced.proxy.OrderServiceImpl2;
import hello.advanced.v4.FieldLogTraceV4;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CglibProxyConfig {

    @Bean
    public OrderRepositoryImpl2 cglibOrderRepository(FieldLogTraceV4 logTrace) {
        OrderRepositoryImpl2 repository = new OrderRepositoryImpl2();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(OrderRepositoryImpl2.class);
        enhancer.setCallback(new LogTraceBasicHandler2(repository, logTrace));
        return (OrderRepositoryImpl2) enhancer.create();
    }

    @Bean
    public OrderServiceImpl2 cglibOrderService(OrderRepositoryImpl2 repository, FieldLogTraceV4 logTrace) {
        OrderServiceImpl2 service = new OrderServiceImpl2(repository);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(OrderServiceImpl2.class);
        enhancer.setCallback(new LogTraceBasicHandler2(service, logTrace));
        return (OrderServiceImpl2) enhancer.create();
    }

    @Bean
    public OrderControllerImpl2 cglibOrderController(OrderServiceImpl2 orderService, FieldLogTraceV4 logTrace) {
        OrderControllerImpl2 controller = new OrderControllerImpl2(orderService);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(OrderControllerImpl2.class);
        enhancer.setCallback(new LogTraceBasicHandler2(controller, logTrace));
        return (OrderControllerImpl2) enhancer.create();
    }
}
