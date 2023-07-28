package com.sujith.project.configuration;

import com.sujith.project.interceptor.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
//@Component
public class InterceptorConfiguration implements WebMvcConfigurer {
    private final ApiInterceptor apiInterceptor;

    @Autowired
    public InterceptorConfiguration(ApiInterceptor apiInterceptor) {
        this.apiInterceptor = apiInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor);
    }

}
