package hello.advanced.proxy.decorator_example;

public class RealComponent implements Component{

    @Override
    public String operation() {
        System.out.println("RealComponent operation");
        return "data";
    }
}
