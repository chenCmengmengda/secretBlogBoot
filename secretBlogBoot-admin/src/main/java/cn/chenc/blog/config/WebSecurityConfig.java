package cn.chenc.blog.config;


import cn.chenc.blog.business.service.SysUserService;
import cn.chenc.blog.security.MyAuthenticationFailHandler;
import cn.chenc.blog.security.MySuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true,jsr250Enabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysUserService userService;
    @Autowired
    private MySuccessHandler authenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailHandler authenticationFailHandler;
    //注入数据源
    @Autowired
    private DataSource dataSource;



    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //指定认证对象的来源
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.userDetailsService(sysUserService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(authenticationProvider());
    }

    //记住我的功能
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl=new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        //启动时创建一张表，这个参数到第二次启动时必须注释掉，因为已经创建了一张表
    //    jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
        return jdbcTokenRepositoryImpl;
    }

    //SpringSecurity配置信息
    public void configure(HttpSecurity http) throws Exception {
        http      //禁用baisc和form认证，在AuthController中自己实现认证逻辑
                .httpBasic().disable()
                .formLogin().disable()

                .logout().disable()

                .authorizeRequests()
                .antMatchers("/pages/login","/getKaptcha","/welcome","/sysUser/login", "/pages/error/403","/pages/error/404","/static/**","/favicon.ico","/css/**","/plugins/**","/js/**","/images/**","/lib/**").permitAll()
                .antMatchers("/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/welcome")
         //       .loginProcessingUrl("/user/login")
        //        .successForwardUrl("/index")
        //        .failureForwardUrl("/403")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/pages/login")
                .and()
                .csrf()
                .disable()
                .headers().frameOptions().sameOrigin()// 设置可以iframe访问
                .and()
                .rememberMe()
                .tokenRepository(getPersistentTokenRepository())
                .tokenValiditySeconds(3600)//Token过期时间为一个小时;
                .userDetailsService(userService)
                .key("INTERNAL_SECRET_KEY");


    }

    @Override
    @Bean
    //注入authenticationManager
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    protected RememberMeServices rememberMeServices(){
        PersistentTokenBasedRememberMeServices rememberMeServices =
                new PersistentTokenBasedRememberMeServices("INTERNAL_SECRET_KEY", userService,getPersistentTokenRepository());

        // 该参数不是必须的，默认值为 "remember-me", 但如果设置必须和页面复选框的 name 一致
        rememberMeServices.setParameter("remember-me");
        return rememberMeServices;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }



}
