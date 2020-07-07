package cn.chenc.blog.web.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 　@description: TODO
 * 　@author 陈_C
 * 　@date 2020/3/21 21:56
 *
 */
@Configuration
@EnableTransactionManagement
public class MyBatisPlusConfig {

    /**
     * 分页插件
     * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page=new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

}
