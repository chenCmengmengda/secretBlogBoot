package cn.chenc.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/3/13 22:24
 *
 */
@SpringBootApplication
@ServletComponentScan
public class BlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }

}
