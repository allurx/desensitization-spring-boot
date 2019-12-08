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

package red.zyc.desensitization.spring.boot.sample.web;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zyc
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DesensitizationSpringBootSampleWebApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(DesensitizationSpringBootSampleWebApplicationTests.class);

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * 脱敏字符串参数
     */
    @Test
    public void desensitizationStringParameter() {
        LOGGER.info(restTemplate.getForObject("/desensitization/stringParameter?email={?}", String.class, "123456@qq.com"));
    }

    /**
     * 脱敏字符串返回值
     */
    @Test
    public void desensitizationStringReturnValue() {
        LOGGER.info(restTemplate.getForObject("/desensitization/stringReturnValue?email={?}", String.class, "123456@qq.com"));
    }

    /**
     * 脱敏集合参数
     */
    @Test
    public void desensitizationCollectionParameter() {
        LOGGER.info(restTemplate.postForObject("/desensitization/collectionParameter", Stream.of("123456@qq.com", "1234567@qq.com", "1234568@qq.com").collect(Collectors.toList()), List.class).toString());
    }

    /**
     * 脱敏集合返回值
     */
    @Test
    public void desensitizationCollectionReturnValue() {
        LOGGER.info(restTemplate.postForObject("/desensitization/collectionReturnValue", Stream.of("123456@qq.com", "1234567@qq.com", "1234568@qq.com").collect(Collectors.toList()), List.class).toString());
    }
}
