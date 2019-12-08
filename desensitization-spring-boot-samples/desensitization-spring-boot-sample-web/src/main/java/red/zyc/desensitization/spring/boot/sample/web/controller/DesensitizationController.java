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

package red.zyc.desensitization.spring.boot.sample.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import red.zyc.desensitization.annotation.EmailSensitive;

import java.util.List;

/**
 * @author zyc
 */

@RestController
@RequestMapping("/desensitization")
public class DesensitizationController {

    @GetMapping("/stringParameter")
    public ResponseEntity<String> desensitizationStringParameter(@RequestParam @EmailSensitive String email) {
        return ResponseEntity.ok(email);
    }

    @GetMapping("/stringReturnValue")
    public ResponseEntity<@EmailSensitive String> desensitizationStringReturnValue(@RequestParam String email) {
        return ResponseEntity.ok(email);
    }

    @PostMapping("/collectionParameter")
    public ResponseEntity<List<String>> desensitizationCollectionParameter(@RequestBody List<@EmailSensitive String> emails) {
        return ResponseEntity.ok(emails);
    }

    @PostMapping("/collectionReturnValue")
    public ResponseEntity<List<@EmailSensitive String>> desensitizationCollectionReturnValue(@RequestBody List<String> emails) {
        return ResponseEntity.ok(emails);
    }
}
