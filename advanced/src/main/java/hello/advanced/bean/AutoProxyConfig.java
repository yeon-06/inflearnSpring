package hello.advanced.bean;

import hello.advanced.v4.FieldLogTraceV4;
import org.springframework.aop.Advisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoProxyConfig {

    @Bean
    public Advisor advisor(FieldLogTraceV4 logTrace) {
        return AdvisorCreator.create(logTrace);
    }
}
