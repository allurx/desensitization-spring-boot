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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import red.zyc.desensitization.boot.sample.web.model.Person;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author zyc
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResponseEntityDesensitizationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * String参数脱敏
     */
    @Test
    void desensitizeStringParameter() {
        String body = restTemplate.getForObject("/responseEntityDesensitization/stringParameter?email={?}", String.class, "123456@qq.com");
        assertEquals("1*****@qq.com", body);
    }

    /**
     * String返回值脱敏
     */
    @Test
    void desensitizeStringReturnValue() {
        String body = restTemplate.getForObject("/responseEntityDesensitization/stringReturnValue?email={?}", String.class, "123456@qq.com");
        assertEquals("1*****@qq.com", body);
    }

    /**
     * Collection参数脱敏
     */
    @Test
    void desensitizeCollectionParameter() throws URISyntaxException {
        List<String> body = restTemplate.exchange(RequestEntity.post(new URI("/responseEntityDesensitization/collectionParameter")).body(Stream.of("123456@qq.com", "123456@qq.com", "123456@qq.com").collect(Collectors.toList())), new ParameterizedTypeReference<List<String>>() {
        }).getBody();
        assert body != null;
        body.forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Collection返回值脱敏
     */
    @Test
    void desensitizeCollectionReturnValue() throws URISyntaxException {
        List<String> body = restTemplate.exchange(RequestEntity.post(new URI("/responseEntityDesensitization/collectionReturnValue")).body(Stream.of("123456@qq.com", "123456@qq.com", "123456@qq.com").collect(Collectors.toList())), new ParameterizedTypeReference<List<String>>() {
        }).getBody();
        assert body != null;
        body.forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Map参数脱敏
     */
    @Test
    void desensitizeMapParameter() throws URISyntaxException {
        Map<String, Person> map = Stream.of("张三").collect(Collectors.toMap(s -> s, o -> new Person("12345678910", "123456@qq.com")));
        Map<String, Person> body = restTemplate.exchange(RequestEntity.post(new URI("/responseEntityDesensitization/mapParameter")).body(map), new ParameterizedTypeReference<Map<String, Person>>() {
        }).getBody();
        assert body != null;
        body.forEach((s, person) -> {
            assertEquals("张*", s);
            assertEquals("123****8910", person.getPhoneNumber());
            assertEquals("1*****@qq.com", person.getEmail());
        });
    }

    /**
     * Map返回值脱敏
     */
    @Test
    void desensitizeMapReturnValue() throws URISyntaxException {
        Map<String, Person> map = Stream.of("张三").collect(Collectors.toMap(s -> s, o -> new Person("12345678910", "123456@qq.com")));
        Map<String, Person> body = restTemplate.exchange(RequestEntity.post(new URI("/responseEntityDesensitization/mapReturnValue")).body(map), new ParameterizedTypeReference<Map<String, Person>>() {
        }).getBody();
        assert body != null;
        body.forEach((s, person) -> {
            assertEquals("张*", s);
            assertEquals("123****8910", person.getPhoneNumber());
            assertEquals("1*****@qq.com", person.getEmail());
        });
    }

    /**
     * Array参数脱敏
     */
    @Test
    void desensitizeArrayParameter() {
        String[] body = restTemplate.postForObject("/responseEntityDesensitization/arrayParameter", new String[]{"123456@qq.com", "123456@qq.com"}, String[].class);
        Arrays.stream(body).forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Array返回值脱敏
     */
    @Test
    void desensitizeArrayReturnValue() {
        String[] body = restTemplate.postForObject("/responseEntityDesensitization/arrayReturnValue", new String[]{"123456@qq.com", "123456@qq.com"}, String[].class);
        Arrays.stream(body).forEach(s -> assertEquals("1*****@qq.com", s));
    }

    /**
     * Object参数脱敏
     */
    @Test
    void desensitizeObjectParameter() {
        var person = restTemplate.postForObject("/responseEntityDesensitization/objectParameter", new Person("12345678910", "123456@qq.com"), Person.class);
        assertEquals("123****8910", person.getPhoneNumber());
        assertEquals("1*****@qq.com", person.getEmail());
    }

    /**
     * Object返回值脱敏
     */
    @Test
    void desensitizeObjectReturnValue() {
        var person = restTemplate.postForObject("/responseEntityDesensitization/objectReturnValue", new Person("12345678910", "123456@qq.com"), Person.class);
        assertEquals("123****8910", person.getPhoneNumber());
        assertEquals("1*****@qq.com", person.getEmail());
    }

}
