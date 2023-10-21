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

package red.zyc.desensitization.boot.sample.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.zyc.desensitization.annotation.ChineseName;
import red.zyc.desensitization.annotation.Email;
import red.zyc.desensitization.boot.sample.web.model.Person;
import red.zyc.parser.type.Cascade;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

/**
 * 脱敏{@link ResponseEntity}类型的数据
 *
 * @author zyc
 */
@RestController
@RequestMapping("/responseEntityDesensitization")
public class ResponseEntityDesensitizationController {

    @GetMapping("/stringParameter")
    public ResponseEntity<String> desensitizeStringParameter(@RequestParam @Email String email) {
        return ok(email);
    }

    @GetMapping("/stringReturnValue")
    public ResponseEntity<@Email String> desensitizeStringReturnValue(@RequestParam String email) {
        return ok(email);
    }

    @PostMapping("/collectionParameter")
    public ResponseEntity<List<String>> desensitizeCollectionParameter(@RequestBody List<@Email String> emails) {
        return ok(emails);
    }

    @PostMapping("/collectionReturnValue")
    public ResponseEntity<List<@Email String>> desensitizeCollectionReturnValue(@RequestBody List<String> emails) {
        return ok(emails);
    }

    @PostMapping("/mapParameter")
    public ResponseEntity<Map<String, Person>> desensitizeMapParameter(@RequestBody Map<@ChineseName String, @Cascade Person> map) {
        return ok(map);
    }

    @PostMapping("/mapReturnValue")
    public ResponseEntity<Map<@ChineseName String, @Cascade Person>> desensitizeMapReturnValue(@RequestBody Map<String, Person> map) {
        return ok(map);
    }

    @PostMapping("/arrayParameter")
    public ResponseEntity<String[]> desensitizeArrayParameter(@RequestBody @Email String[] array) {
        return ok(array);
    }

    @PostMapping("/arrayReturnValue")
    public ResponseEntity<@Email String[]> desensitizeArrayReturnValue(@RequestBody String[] array) {
        return ok(array);
    }

    @PostMapping("/objectParameter")
    public ResponseEntity<Person> desensitizeObjectParameter(@RequestBody @Cascade Person person) {
        return ok(person);
    }

    @PostMapping("/objectReturnValue")
    public ResponseEntity<@Cascade Person> desensitizeObjectReturnValue(@RequestBody Person person) {
        return ok(person);
    }

}
