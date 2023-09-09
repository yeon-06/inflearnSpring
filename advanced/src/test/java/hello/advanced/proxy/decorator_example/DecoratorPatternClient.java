package hello.advanced.proxy.decorator_example;

public class DecoratorPatternClient {

    private final Component component;

    public DecoratorPatternClient(Component component) {
        this.component = component;
    }

    public void execute() {
        System.out.printf("result: %s%n", component.operation());
    }
}
