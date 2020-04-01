# desensitization-spring-boot
在[desensitization](https://github.com/Allurx/desensitization)库的基础上适配spring-boot，基于spring-aop对全局方法进行拦截脱敏处理，默认会对当前spring-boot工程启动类所在的包及其子包下所有**需要**脱敏处理的方法进行拦截。当然你也可以在spring的配置文件中通过desensitization开头的配置参数编写自己的切点表达式以便更好地控制脱敏。
# 用法
## jdk版本
大于等于1.8
## maven依赖
```xml
<dependency>
  <groupId>red.zyc</groupId>
  <artifactId>desensitization-spring-boot-starter</artifactId>
  <version>1.0.1</version>
</dependency>
```
# 测试用例
1. [需要对返回值或参数脱敏的方法](https://github.com/Allurx/desensitization-spring-boot/blob/master/desensitization-spring-boot-samples/desensitization-spring-boot-sample-web/src/main/java/red/zyc/desensitization/spring/boot/sample/web/controller/DesensitizationController.java)
2. [测试用例](https://github.com/Allurx/desensitization-spring-boot/blob/master/desensitization-spring-boot-samples/desensitization-spring-boot-sample-web/src/test/java/red/zyc/desensitization/spring/boot/sample/web/DesensitizationSpringBootSampleWebApplicationTests.java)
# License
[Apache License 2.0](https://github.com/Allurx/desensitization-spring-boot/blob/master/LICENSE.txt)
