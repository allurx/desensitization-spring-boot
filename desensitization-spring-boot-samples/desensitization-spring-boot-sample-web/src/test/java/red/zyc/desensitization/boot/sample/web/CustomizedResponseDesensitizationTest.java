/*
 * Copyright 2020 the original author or authors.
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.web.util.UriTemplate;
import red.zyc.desensitization.boot.sample.web.model.CustomizedResponse;
import red.zyc.desensitization.boot.sample.web.model.Person;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;

/**
 * @author zyc
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomizedResponseDesensitizationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedResponseDesensitizationTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * String参数脱敏
     */
    @Test
    void desensitizeStringParameter() {
        CustomizedResponse<String> body = restTemplate.exchange(RequestEntity.get(new UriTemplate("/customizedResponseDesensitization/stringParameter?email={?}").expand("123456@qq.com")).build(), new ParameterizedTypeReference<CustomizedResponse<String>>() {
        }).getBody();
        assertNotNull(body);
        LOGGER.info(body.getData());
    }

    /**
     * String返回值脱敏
     */
    @Test
    void desensitizeStringReturnValue() {
        CustomizedResponse<String> body = restTemplate.exchange(RequestEntity.get(new UriTemplate("/customizedResponseDesensitization/stringReturnValue?email={?}").expand("123456@qq.com")).build(), new ParameterizedTypeReference<CustomizedResponse<String>>() {
        }).getBody();
        assertNotNull(body);
        LOGGER.info(body.getData());
    }

    /**
     * Collection参数脱敏
     */
    @Test
    void desensitizeCollectionParameter() throws URISyntaxException {
        CustomizedResponse<List<String>> body = restTemplate.exchange(RequestEntity.post(new URI("/customizedResponseDesensitization/collectionParameter")).body(Stream.of("123456@qq.com", "1234567@qq.com", "1234568@qq.com").collect(Collectors.toList())), new ParameterizedTypeReference<CustomizedResponse<List<String>>>() {
        }).getBody();
        assertNotNull(body);
        LOGGER.info(body.getData().toString());
    }

    /**
     * Collection返回值脱敏
     */
    @Test
    void desensitizeCollectionReturnValue() throws URISyntaxException {
        CustomizedResponse<List<String>> body = restTemplate.exchange(RequestEntity.post(new URI("/customizedResponseDesensitization/collectionReturnValue")).body(Stream.of("123456@qq.com", "1234567@qq.com", "1234568@qq.com").collect(Collectors.toList())), new ParameterizedTypeReference<CustomizedResponse<List<String>>>() {
        }).getBody();
        assertNotNull(body);
        LOGGER.info(body.getData().toString());
    }

    /**
     * Map参数脱敏
     */
    @Test
    void desensitizeMapParameter() throws URISyntaxException {
        Map<String, Person> map = Stream.of("张三", "李四").collect(Collectors.toMap(s -> s, o -> new Person("12345678910", "123456@qq.com")));
        CustomizedResponse<Map<String, Person>> body = restTemplate.exchange(RequestEntity.post(new URI("/customizedResponseDesensitization/mapParameter")).body(map), new ParameterizedTypeReference<CustomizedResponse<Map<String, Person>>>() {
        }).getBody();
        assertNotNull(body);
        LOGGER.info(body.getData().toString());
    }

    /**
     * Map返回值脱敏
     */
    @Test
    void desensitizeMapReturnValue() throws URISyntaxException {
        Map<String, Person> map = Stream.of("张三", "李四").collect(Collectors.toMap(s -> s, o -> new Person("12345678910", "123456@qq.com")));
        CustomizedResponse<Map<String, Person>> body = restTemplate.exchange(RequestEntity.post(new URI("/customizedResponseDesensitization/mapReturnValue")).body(map), new ParameterizedTypeReference<CustomizedResponse<Map<String, Person>>>() {
        }).getBody();
        assertNotNull(body);
        LOGGER.info(body.getData().toString());
    }

    /**
     * Array参数脱敏
     */
    @Test
    void desensitizeArrayParameter() throws URISyntaxException {
        CustomizedResponse<String[]> body = restTemplate.exchange(RequestEntity.post(new URI("/customizedResponseDesensitization/arrayParameter")).body(new String[]{"123456@qq.com", "1234567@qq.com"}), new ParameterizedTypeReference<CustomizedResponse<String[]>>() {
        }).getBody();
        assertNotNull(body);
        LOGGER.info(Arrays.toString(body.getData()));
    }

    /**
     * Array返回值脱敏
     */
    @Test
    void desensitizeArrayReturnValue() throws URISyntaxException {
        CustomizedResponse<String[]> body = restTemplate.exchange(RequestEntity.post(new URI("/customizedResponseDesensitization/arrayReturnValue")).body(new String[]{"123456@qq.com", "1234567@qq.com"}), new ParameterizedTypeReference<CustomizedResponse<String[]>>() {
        }).getBody();
        assertNotNull(body);
        LOGGER.info(Arrays.toString(body.getData()));
    }

    /**
     * Object参数脱敏
     */
    @Test
    void desensitizeObjectParameter() throws URISyntaxException {
        CustomizedResponse<Person> body = restTemplate.exchange(RequestEntity.post(new URI("/customizedResponseDesensitization/objectParameter")).body(new Person("12345678910", "123456@qq.com")), new ParameterizedTypeReference<CustomizedResponse<Person>>() {
        }).getBody();
        assertNotNull(body);
        LOGGER.info(body.getData().toString());
    }

    /**
     * Object返回值脱敏
     */
    @Test
    void desensitizeObjectReturnValue() throws URISyntaxException {
        CustomizedResponse<Person> body = restTemplate.exchange(RequestEntity.post(new URI("/customizedResponseDesensitization/objectReturnValue")).body(new Person("12345678910", "123456@qq.com")), new ParameterizedTypeReference<CustomizedResponse<Person>>() {
        }).getBody();
        assertNotNull(body);
        LOGGER.info(body.getData().toString());
    }
}
