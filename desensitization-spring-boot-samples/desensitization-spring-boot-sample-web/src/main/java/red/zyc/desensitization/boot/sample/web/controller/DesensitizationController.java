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
import org.springframework.web.bind.annotation.*;
import red.zyc.desensitization.annotation.CascadeSensitive;
import red.zyc.desensitization.annotation.ChineseNameSensitive;
import red.zyc.desensitization.annotation.EmailSensitive;
import red.zyc.desensitization.boot.sample.web.model.Person;

import java.util.List;
import java.util.Map;

/**
 * @author zyc
 */
@RestController
@RequestMapping("/desensitization")
public class DesensitizationController {

    @GetMapping("/stringParameter")
    public ResponseEntity<String> desensitizeStringParameter(@RequestParam @EmailSensitive String email) {
        return ResponseEntity.ok(email);
    }

    @GetMapping("/stringReturnValue")
    public ResponseEntity<@EmailSensitive String> desensitizeStringReturnValue(@RequestParam String email) {
        return ResponseEntity.ok(email);
    }

    @PostMapping("/collectionParameter")
    public ResponseEntity<List<String>> desensitizeCollectionParameter(@RequestBody List<@EmailSensitive String> emails) {
        return ResponseEntity.ok(emails);
    }

    @PostMapping("/collectionReturnValue")
    public ResponseEntity<List<@EmailSensitive String>> desensitizeCollectionReturnValue(@RequestBody List<String> emails) {
        return ResponseEntity.ok(emails);
    }

    @PostMapping("/mapParameter")
    public ResponseEntity<Map<String, Person>> desensitizeMapParameter(@RequestBody Map<@ChineseNameSensitive String, @CascadeSensitive Person> map) {
        return ResponseEntity.ok(map);
    }

    @PostMapping("/mapReturnValue")
    public ResponseEntity<Map<@ChineseNameSensitive String, @CascadeSensitive Person>> desensitizeMapReturnValue(@RequestBody Map<String, Person> map) {
        return ResponseEntity.ok(map);
    }

    @PostMapping("/arrayParameter")
    public ResponseEntity<String[]> desensitizeArrayParameter(@RequestBody @EmailSensitive String[] array) {
        return ResponseEntity.ok(array);
    }

    @PostMapping("/arrayReturnValue")
    public ResponseEntity<@EmailSensitive String[]> desensitizeArrayReturnValue(@RequestBody String[] array) {
        return ResponseEntity.ok(array);
    }

    @PostMapping("/objectParameter")
    public ResponseEntity<Person> desensitizeObjectParameter(@RequestBody @CascadeSensitive Person person) {
        return ResponseEntity.ok(person);
    }

    @PostMapping("/objectReturnValue")
    public ResponseEntity<@CascadeSensitive Person> desensitizeObjectReturnValue(@RequestBody Person person) {
        return ResponseEntity.ok(person);
    }

}
