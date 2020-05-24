package cn.chenc.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/3/13 22:24
 *
 */

//@ServletComponentScan(basePackages = "cn.chenc.blog")
@SpringBootApplication
@ServletComponentScan
public class BlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }

}
