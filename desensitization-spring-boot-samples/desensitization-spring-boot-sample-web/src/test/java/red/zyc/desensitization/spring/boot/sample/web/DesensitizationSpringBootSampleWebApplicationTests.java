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
import red.zyc.desensitization.spring.boot.sample.web.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
     * String参数脱敏
     */
    @Test
    public void desensitizeStringParameter() {
        LOGGER.info(restTemplate.getForObject("/desensitization/stringParameter?email={?}", String.class, "123456@qq.com"));
    }

    /**
     * String返回值脱敏
     */
    @Test
    public void desensitizeStringReturnValue() {
        LOGGER.info(restTemplate.getForObject("/desensitization/stringReturnValue?email={?}", String.class, "123456@qq.com"));
    }

    /**
     * Collection参数脱敏
     */
    @Test
    public void desensitizeCollectionParameter() {
        LOGGER.info(restTemplate.postForObject("/desensitization/collectionParameter", Stream.of("123456@qq.com", "1234567@qq.com", "1234568@qq.com").collect(Collectors.toList()), List.class).toString());
    }

    /**
     * Collection返回值脱敏
     */
    @Test
    public void desensitizeCollectionReturnValue() {
        LOGGER.info(restTemplate.postForObject("/desensitization/collectionReturnValue", Stream.of("123456@qq.com", "1234567@qq.com", "1234568@qq.com").collect(Collectors.toList()), List.class).toString());
    }

    /**
     * Map参数脱敏
     */
    @Test
    public void desensitizeMapParameter() {
        Map<String, Person> map = Stream.of("张三", "李四")
                .collect(Collectors.toMap(s -> s, o -> new Person("12345678910", "123456@qq.com")));
        LOGGER.info(restTemplate.postForObject("/desensitization/mapParameter", map, Map.class).toString());
    }

    /**
     * Map返回值脱敏
     */
    @Test
    public void desensitizeMapReturnValue() {
        Map<String, Person> map = Stream.of("张三", "李四")
                .collect(Collectors.toMap(s -> s, o -> new Person("12345678910", "123456@qq.com")));
        LOGGER.info(restTemplate.postForObject("/desensitization/mapReturnValue", map, Map.class).toString());
    }

    /**
     * Array参数脱敏
     */
    @Test
    public void desensitizeArrayParameter() {
        LOGGER.info(Arrays.toString(restTemplate.postForObject("/desensitization/arrayParameter", new String[]{"123456@qq.com", "1234567@qq.com"}, String[].class)));
    }

    /**
     * Array返回值脱敏
     */
    @Test
    public void desensitizeArrayReturnValue() {
        LOGGER.info(Arrays.toString(restTemplate.postForObject("/desensitization/arrayReturnValue", new String[]{"123456@qq.com", "1234567@qq.com"}, String[].class)));
    }

    /**
     * Object参数脱敏
     */
    @Test
    public void desensitizeObjectParameter() {
        LOGGER.info(restTemplate.postForObject("/desensitization/objectParameter", new Person("12345678910", "123456@qq.com"), Person.class).toString());
    }

    /**
     * Object返回值脱敏
     */
    @Test
    public void desensitizeObjectReturnValue() {
        LOGGER.info(restTemplate.postForObject("/desensitization/objectReturnValue", new Person("12345678910", "123456@qq.com"), Person.class).toString());
    }

}
