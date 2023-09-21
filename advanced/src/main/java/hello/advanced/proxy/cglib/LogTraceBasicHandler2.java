package hello.advanced.proxy.cglib;

import hello.advanced.trace.LogTrace;
import hello.advanced.trace.TraceStatus;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

@RequiredArgsConstructor
public class LogTraceBasicHandler2 implements MethodInterceptor {

    private final Object target;
    private final LogTrace logTrace;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        TraceStatus traceStatus = null;
        try {
            traceStatus = logTrace.begin(method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()");
            Object result = method.invoke(target, args);
            logTrace.end(traceStatus);
            return result;
        } catch (Exception e) {
            logTrace.exception(traceStatus, e);
            throw e;
        }
    }
}
