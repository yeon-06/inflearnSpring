package hello.advanced.proxy;

public class OrderRepositoryImpl2 {

    public OrderRepositoryImpl2() {
    }

    public void save(String itemId) {
        if ("ex".equals(itemId)) {
            throw new IllegalStateException("예외 발생");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
