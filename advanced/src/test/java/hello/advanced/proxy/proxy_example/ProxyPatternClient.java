package hello.advanced.proxy.proxy_example;

public class ProxyPatternClient {

    private final Subject subject;

    public ProxyPatternClient(Subject subject) {
        this.subject = subject;
    }

    public void execute() {
        System.out.println(subject.operation());
    }

}
