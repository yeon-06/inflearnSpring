package hello.advanced.bean;

import hello.advanced.trace.LogTrace;
import hello.advanced.trace.TraceStatus;
import hello.advanced.v4.FieldLogTraceV4;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class LogTraceBeanPostProcessor implements BeanPostProcessor {

    private final Advisor advisor;

    public LogTraceBeanPostProcessor(FieldLogTraceV4 logTrace) {
        this.advisor = generateAdvisor(logTrace);
    }

    private Advisor generateAdvisor(LogTrace logTrace) {
        var pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        var advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(advice);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        // 예제를 간단하게 만들기 위해 패키지명 기준으로 지정했지만, 포인트컷을 적용하면 보다 디테일한 설정이 가능하다.
        String packageName = bean.getClass().getPackageName();
        if (!packageName.startsWith("hello.advanced.bean")) {
            return bean;
        }

        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);
        return proxyFactory.getProxy();
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
