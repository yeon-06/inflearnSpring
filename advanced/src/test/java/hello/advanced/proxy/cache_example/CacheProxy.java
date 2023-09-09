package hello.advanced.proxy.cache_example;

import hello.advanced.proxy.proxy_example.Subject;

public class CacheProxy implements Subject {

    private final Subject target;
    private String cache;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        if (cache == null) {
            cache = target.operation();
        }
        return cache;
    }

}
