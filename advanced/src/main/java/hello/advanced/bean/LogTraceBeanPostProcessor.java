package hello.advanced.bean;

import static hello.advanced.bean.AdvisorCreator.create;

import hello.advanced.v4.FieldLogTraceV4;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;

//@Component
public class LogTraceBeanPostProcessor implements BeanPostProcessor {

    private final Advisor advisor;

    public LogTraceBeanPostProcessor(FieldLogTraceV4 logTrace) {
        this.advisor = create(logTrace);
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
}
