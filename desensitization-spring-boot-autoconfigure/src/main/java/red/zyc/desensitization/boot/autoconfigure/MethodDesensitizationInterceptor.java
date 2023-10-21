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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import red.zyc.desensitization.Sensitive;
import red.zyc.parser.handler.Parse;
import red.zyc.parser.type.AnnotatedTypeToken;
import red.zyc.parser.type.Cascade;

import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.AnnotatedTypeVariable;
import java.lang.reflect.AnnotatedWildcardType;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
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
                .filter(i -> needDesensitized(parameters[i].getAnnotatedType()))
                .forEach(i -> arguments[i] = Sensitive.desensitize(arguments[i], AnnotatedTypeToken.of(parameters[i].getAnnotatedType())));
        Object proceed = invocation.proceed();
        AnnotatedType returnType = method.getAnnotatedReturnType();
        return needDesensitized(returnType) ? Sensitive.desensitize(proceed, AnnotatedTypeToken.of(returnType)) : proceed;
    }

    /**
     * 通过对象的{@link AnnotatedType}判断其是否需要被脱敏，因为经过脱敏后
     * 会返回一个新构造的对象，对于那些没有标注敏感注解或者不需要级联脱敏的对象，
     * 我们没有必要对其进行脱敏，以便提高程序性能。虽然我们可以定义一个类似
     * {@code @Sensitive}这样的注解放置在需要被脱敏的对象上以便我们能够方便的
     * 查找那些需要被脱敏的对象，但是为了减少对象上的注解个数我们并没有这样做，
     * 例如参数上标注了一个敏感注解，然后再标注一个{@code @Sensitive}注解，
     * 这样做有点画蛇添足。因此我们通过反射的方式来判断对象是否需要脱敏。
     * 虽然可能有一点性能上的牺牲，但是相对于每一个对象都被脱敏器处理一遍，
     * （甚至是被重新构造了一个新对象）这样做是可取的。
     *
     * @param annotatedType 对象的{@link AnnotatedType}
     * @return 该对象是否需要被脱敏
     */
    private boolean needDesensitized(AnnotatedType annotatedType) {
        if (Arrays.stream(annotatedType.getDeclaredAnnotations()).anyMatch(annotation -> annotation.annotationType().isAnnotationPresent(Parse.class))) {
            return true;
        }
        if (annotatedType.getDeclaredAnnotation(Cascade.class) != null) {
            return true;
        }
        if (annotatedType instanceof AnnotatedTypeVariable annotatedTypeVariable) {
            AnnotatedType[] annotatedBounds = annotatedTypeVariable.getAnnotatedBounds();
            return Arrays.stream(annotatedBounds).anyMatch(this::needDesensitized);
        }
        if (annotatedType instanceof AnnotatedWildcardType annotatedWildcardType) {
            AnnotatedType[] annotatedUpperBounds = annotatedWildcardType.getAnnotatedUpperBounds();
            AnnotatedType[] annotatedBounds = annotatedUpperBounds.length == 0 ? annotatedWildcardType.getAnnotatedLowerBounds() : annotatedUpperBounds;
            return Arrays.stream(annotatedBounds).anyMatch(this::needDesensitized);
        }
        if (annotatedType instanceof AnnotatedParameterizedType annotatedParameterizedType) {
            AnnotatedType[] annotatedActualTypeArguments = annotatedParameterizedType.getAnnotatedActualTypeArguments();
            return Arrays.stream(annotatedActualTypeArguments).anyMatch(this::needDesensitized);
        }
        if (annotatedType instanceof AnnotatedArrayType annotatedArrayType) {
            return needDesensitized(annotatedArrayType.getAnnotatedGenericComponentType());
        }
        return false;
    }

}
