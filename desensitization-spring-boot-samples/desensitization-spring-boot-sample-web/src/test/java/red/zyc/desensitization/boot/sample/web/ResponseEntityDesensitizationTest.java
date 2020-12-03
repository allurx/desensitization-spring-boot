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

package red.zyc.desensitization.boot.sample.web;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import red.zyc.desensitization.boot.sample.web.model.Person;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * @author zyc
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResponseEntityDesensitizationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseEntityDesensitizationTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * String参数脱敏
     */
    @Test
    void desensitizeStringParameter() {
        String body = restTemplate.getForObject("/responseEntityDesensitization/stringParameter?email={?}", String.class, "123456@qq.com");
        assertNotNull(body);
        LOGGER.info(body);
    }

    /**
     * String返回值脱敏
     */
    @Test
    void desensitizeStringReturnValue() {
        String body = restTemplate.getForObject("/responseEntityDesensitization/stringReturnValue?email={?}", String.class, "123456@qq.com");
        assertNotNull(body);
        LOGGER.info(body);
    }

    /**
     * Collection参数脱敏
     */
    @Test
    void desensitizeCollectionParameter() {
        List<?> body = restTemplate.postForObject("/responseEntityDesensitization/collectionParameter", Stream.of("123456@qq.com", "1234567@qq.com", "1234568@qq.com").collect(Collectors.toList()), List.class);
        assertNotNull(body);
        LOGGER.info(body.toString());
    }

    /**
     * Collection返回值脱敏
     */
    @Test
    void desensitizeCollectionReturnValue() {
        List<?> body = restTemplate.postForObject("/responseEntityDesensitization/collectionReturnValue", Stream.of("123456@qq.com", "1234567@qq.com", "1234568@qq.com").collect(Collectors.toList()), List.class);
        assertNotNull(body);
        LOGGER.info(body.toString());
    }

    /**
     * Map参数脱敏
     */
    @Test
    void desensitizeMapParameter() {
        Map<String, Person> map = Stream.of("张三", "李四").collect(Collectors.toMap(s -> s, o -> new Person("12345678910", "123456@qq.com")));
        Map<?, ?> body = restTemplate.postForObject("/responseEntityDesensitization/mapParameter", map, Map.class);
        assertNotNull(body);
        LOGGER.info(body.toString());
    }

    /**
     * Map返回值脱敏
     */
    @Test
    void desensitizeMapReturnValue() {
        Map<String, Person> map = Stream.of("张三", "李四").collect(Collectors.toMap(s -> s, o -> new Person("12345678910", "123456@qq.com")));
        Map<?, ?> body = restTemplate.postForObject("/responseEntityDesensitization/mapReturnValue", map, Map.class);
        assertNotNull(body);
        LOGGER.info(body.toString());
    }

    /**
     * Array参数脱敏
     */
    @Test
    void desensitizeArrayParameter() {
        String[] body = restTemplate.postForObject("/responseEntityDesensitization/arrayParameter", new String[]{"123456@qq.com", "1234567@qq.com"}, String[].class);
        assertNotNull(body);
        LOGGER.info(Arrays.toString(body));
    }

    /**
     * Array返回值脱敏
     */
    @Test
    void desensitizeArrayReturnValue() {
        String[] body = restTemplate.postForObject("/responseEntityDesensitization/arrayReturnValue", new String[]{"123456@qq.com", "1234567@qq.com"}, String[].class);
        assertNotNull(body);
        LOGGER.info(Arrays.toString(body));
    }

    /**
     * Object参数脱敏
     */
    @Test
    void desensitizeObjectParameter() {
        Person body = restTemplate.postForObject("/responseEntityDesensitization/objectParameter", new Person("12345678910", "123456@qq.com"), Person.class);
        assertNotNull(body);
        LOGGER.info(body.toString());
    }

    /**
     * Object返回值脱敏
     */
    @Test
    void desensitizeObjectReturnValue() {
        Person body = restTemplate.postForObject("/responseEntityDesensitization/objectReturnValue", new Person("12345678910", "123456@qq.com"), Person.class);
        assertNotNull(body);
        LOGGER.info(body.toString());
    }

}
