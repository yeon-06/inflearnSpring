package hello.advanced.ex;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class MdcInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MdcLogInterceptor())
            .addPathPatterns("/test/**");
    }

    private static class MdcLogInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            MDC.put("Url", String.valueOf(request.getRequestURL()));
            MDC.put("Controller", controllerInfo(handler));
            return true;
        }

        private String controllerInfo(Object handler) {
            try {
                if (handler instanceof HandlerMethod handlerMethod) {
                    return handlerMethod.getBeanType().getSimpleName() + "#" + handlerMethod.getMethod().getName();
                }
            } catch (Exception e) {
                log.warn("MdcLogInterceptor : {}", e.getMessage(), e);
            }
            return "";
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            MDC.clear();
        }
    }
}
