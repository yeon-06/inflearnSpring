package hello.advanced.aspect;

import hello.advanced.trace.LogTrace;
import hello.advanced.trace.TraceStatus;
import hello.advanced.v4.FieldLogTraceV4;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(FieldLogTraceV4 logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* hello.advanced.aspect..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);
            Object result = joinPoint.proceed();
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
