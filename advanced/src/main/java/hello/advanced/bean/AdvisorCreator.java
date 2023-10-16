package hello.advanced.bean;

import hello.advanced.trace.LogTrace;
import hello.advanced.trace.TraceStatus;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class AdvisorCreator {

    public static Advisor create(LogTrace logTrace) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.advanced.bean..*(..))");

        var advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    private record LogTraceAdvice(LogTrace logTrace) implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            TraceStatus status = null;
            try {
                Method method = invocation.getMethod();
                String message = method.getDeclaringClass().getSimpleName() + "."
                    + method.getName() + "()";
                status = logTrace.begin(message);
                Object result = invocation.proceed();
                logTrace.end(status);
                return result;
            } catch (Exception e) {
                logTrace.exception(status, e);
                throw e;
            }
        }
    }
}
