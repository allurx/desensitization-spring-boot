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

package red.zyc.desensitization.spring.boot.autoconfigure;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import red.zyc.desensitization.Sensitive;
import red.zyc.desensitization.resolver.TypeToken;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.IntStream;

/**
 * 全局方法advice
 *
 * @author zyc
 */
public class MethodDesensitizationInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        Parameter[] parameters = method.getParameters();
        IntStream.range(0, parameters.length)
                .forEach(i -> arguments[i] = Sensitive.desensitize(arguments[i], TypeToken.of(parameters[i].getAnnotatedType())));
        return Sensitive.desensitize(invocation.proceed(), TypeToken.of(method.getAnnotatedReturnType()));
    }
}
