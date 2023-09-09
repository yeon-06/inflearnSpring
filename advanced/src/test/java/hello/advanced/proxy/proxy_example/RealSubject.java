package hello.advanced.proxy.proxy_example;

public class RealSubject implements Subject {

    @Override
    public String operation() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        return "RealSubject";
    }
}
