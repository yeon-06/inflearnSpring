package hello.advanced.proxy;

public class OrderServiceImpl2 {

    private OrderRepositoryImpl2 repository;

    public OrderServiceImpl2() {
    }

    public OrderServiceImpl2(OrderRepositoryImpl2 repository) {
        this.repository = repository;
    }

    public void orderItem(String itemId) {
        repository.save(itemId);
    }
}
