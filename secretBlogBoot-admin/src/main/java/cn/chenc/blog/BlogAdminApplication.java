package cn.chenc.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/3/13 22:24
 *
 */

//@ServletComponentScan(basePackages = "cn.chenc.blog")
@SpringBootApplication
@ServletComponentScan
@EnableSwagger2
public class BlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }

}
