/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package red.zyc.desensitization.boot.autoconfigure;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import red.zyc.desensitization.resolver.TypeResolver;

import java.lang.reflect.AnnotatedParameterizedType;
import java.util.Optional;

/**
 * @author zyc
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(DesensitizationProperties.class)
public class DesensitizationAutoConfiguration {

    private static final String DESENSITIZATION_ADVISOR = "desensitizationAdvisor";
    private final DesensitizationProperties desensitizationProperties;
    static final ThreadLocal<SpringApplication> SPRING_APPLICATION_HOLDER = new ThreadLocal<>();

    public DesensitizationAutoConfiguration(DesensitizationProperties desensitizationProperties) {
        this.desensitizationProperties = desensitizationProperties;
    }

    @Bean
    @ConditionalOnMissingBean(name = DESENSITIZATION_ADVISOR)
    public Advisor desensitizationAdvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setAdvice(new MethodDesensitizationInterceptor());
        advisor.setExpression(pointcutExpression());
        return advisor;
    }

    @Bean
    public TypeResolver<ResponseEntity<Object>, AnnotatedParameterizedType> responseEntityResolver() {
        return new ResponseEntityTypeResolver();
    }

    /**
     * @return 切点表达式字符串
     */
    private String pointcutExpression() {
        SpringApplication springApplication = SPRING_APPLICATION_HOLDER.get();
        Assert.notNull(springApplication, () -> "无法获取SpringApplication！当前应用可能不是spring-boot工程。");
        return Optional.ofNullable(desensitizationProperties.getPointcutExpression())
                .orElse("execution(* "
                        + springApplication.getMainApplicationClass().getPackage().getName()
                        + "..*.*(..))");
    }

}
