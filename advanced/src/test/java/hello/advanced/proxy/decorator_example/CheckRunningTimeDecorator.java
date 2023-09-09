package hello.advanced.proxy.decorator_example;

public class CheckRunningTimeDecorator implements Component{

    private final Component component;

    public CheckRunningTimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        long startTime = System.currentTimeMillis();
        String result = component.operation();
        long endTime = System.currentTimeMillis();
        System.out.printf("수행 시간: %d%n", endTime - startTime);
        return result;
    }
}
