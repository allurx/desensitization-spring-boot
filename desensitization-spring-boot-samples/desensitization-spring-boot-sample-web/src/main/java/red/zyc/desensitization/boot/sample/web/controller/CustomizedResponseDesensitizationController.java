package red.zyc.desensitization.boot.sample.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.zyc.desensitization.annotation.ChineseName;
import red.zyc.desensitization.annotation.Email;
import red.zyc.desensitization.boot.sample.web.model.CustomizedResponse;
import red.zyc.desensitization.boot.sample.web.model.Person;
import red.zyc.parser.type.Cascade;

import java.util.List;
import java.util.Map;

import static red.zyc.desensitization.boot.sample.web.model.CustomizedResponse.ok;

/**
 * 脱敏{@link CustomizedResponse}类型的数据
 *
 * @author zyc
 */
@RestController
@RequestMapping("/customizedResponseDesensitization")
public class CustomizedResponseDesensitizationController {

    @GetMapping("/stringParameter")
    public CustomizedResponse<String> desensitizeStringParameter(@RequestParam @Email String email) {
        return ok(email);
    }

    @GetMapping("/stringReturnValue")
    public CustomizedResponse<@Email String> desensitizeStringReturnValue(@RequestParam String email) {
        return ok(email);
    }

    @PostMapping("/collectionParameter")
    public CustomizedResponse<List<String>> desensitizeCollectionParameter(@RequestBody List<@Email String> emails) {
        return ok(emails);
    }

    @PostMapping("/collectionReturnValue")
    public CustomizedResponse<List<@Email String>> desensitizeCollectionReturnValue(@RequestBody List<String> emails) {
        return ok(emails);
    }

    @PostMapping("/mapParameter")
    public CustomizedResponse<Map<String, Person>> desensitizeMapParameter(@RequestBody Map<@ChineseName String, @Cascade Person> map) {
        return ok(map);
    }

    @PostMapping("/mapReturnValue")
    public CustomizedResponse<Map<@ChineseName String, @Cascade Person>> desensitizeMapReturnValue(@RequestBody Map<String, Person> map) {
        return ok(map);
    }

    @PostMapping("/arrayParameter")
    public CustomizedResponse<String[]> desensitizeArrayParameter(@RequestBody @Email String[] array) {
        return ok(array);
    }

    @PostMapping("/arrayReturnValue")
    public CustomizedResponse<@Email String[]> desensitizeArrayReturnValue(@RequestBody String[] array) {
        return ok(array);
    }

    @PostMapping("/objectParameter")
    public CustomizedResponse<Person> desensitizeObjectParameter(@RequestBody @Cascade Person person) {
        return ok(person);
    }

    @PostMapping("/objectReturnValue")
    public CustomizedResponse<@Cascade Person> desensitizeObjectReturnValue(@RequestBody Person person) {
        return ok(person);
    }
}
