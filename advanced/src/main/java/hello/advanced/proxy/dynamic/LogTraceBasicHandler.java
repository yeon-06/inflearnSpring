package hello.advanced.proxy.dynamic;

import hello.advanced.trace.LogTrace;
import hello.advanced.trace.TraceStatus;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogTraceBasicHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
