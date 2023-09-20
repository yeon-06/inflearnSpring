package hello.advanced.proxy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    @Override
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
