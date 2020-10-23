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

import org.springframework.http.ResponseEntity;
import red.zyc.desensitization.resolver.TypeResolver;
import red.zyc.desensitization.resolver.TypeResolvers;

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;

/**
 * 用来解析返回值类型为{@link ResponseEntity}的类型解析器
 *
 * @author zyc
 */
public class ResponseEntityTypeResolver implements TypeResolver<ResponseEntity<Object>, AnnotatedParameterizedType> {

    private final int order = TypeResolvers.randomOrder();

    @Override
    public ResponseEntity<Object> resolve(ResponseEntity<Object> responseEntity, AnnotatedParameterizedType annotatedParameterizedType) {
        AnnotatedType typeArgument = annotatedParameterizedType.getAnnotatedActualTypeArguments()[0];
        Object erased = TypeResolvers.resolve(responseEntity.getBody(), typeArgument);
        return new ResponseEntity<>(erased, responseEntity.getHeaders(), responseEntity.getStatusCode());
    }

    @Override
    public boolean support(Object value, AnnotatedType annotatedType) {
        return value instanceof ResponseEntity && annotatedType instanceof AnnotatedParameterizedType;
    }

    @Override
    public int order() {
        return order;
    }
}
