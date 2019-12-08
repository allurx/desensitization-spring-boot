package red.zyc.desensitization.spring.boot.autoconfigure;/*
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

import org.aspectj.weaver.tools.PointcutPrimitive;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zyc
 */
@ConfigurationProperties(prefix = "desensitization")
public class DesensitizationProperties {

    /**
     * 切点表达式，只支持以下几种AspectJ切点表达式原语：
     * <ul>
     *     {@link PointcutPrimitive#EXECUTION}
     *     {@link PointcutPrimitive#ARGS}
     *     {@link PointcutPrimitive#REFERENCE}
     *     {@link PointcutPrimitive#THIS}
     *     {@link PointcutPrimitive#TARGET}
     *     {@link PointcutPrimitive#WITHIN}
     *     {@link PointcutPrimitive#AT_ANNOTATION}
     *     {@link PointcutPrimitive#AT_WITHIN}
     *     {@link PointcutPrimitive#AT_ARGS}
     *     {@link PointcutPrimitive#AT_TARGET}
     * </ul>
     * 默认的切点表达式是当前spring-boot工程启动类所在的包及其子包下的所有方法
     */
    private String pointcutExpression;

    public String getPointcutExpression() {
        return pointcutExpression;
    }

    public void setPointcutExpression(String pointcutExpression) {
        this.pointcutExpression = pointcutExpression;
    }
}
