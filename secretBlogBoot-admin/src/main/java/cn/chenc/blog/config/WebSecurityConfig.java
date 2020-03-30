package cn.chenc.blog.config;


import cn.chenc.blog.business.service.SysUserService;
import cn.chenc.blog.security.MyAuthenticationFailHandler;
import cn.chenc.blog.security.MySuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //指定认证对象的来源
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.userDetailsService(sysUserService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(authenticationProvider());
    }

    //SpringSecurity配置信息
    public void configure(HttpSecurity http) throws Exception {
        http      //禁用baisc和form认证，在AuthController中自己实现认证逻辑
                .httpBasic().disable()
                .formLogin().disable()

                .logout().disable()

                .authorizeRequests()
                .antMatchers("/pages/login","/getKaptcha","/welcome","/sysUser/login", "/pages/error/403","/pages/error/404","/static/**","/favicon.ico","/css/**","/plugins/**","/js/**","/images/**").permitAll()
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
                .headers().frameOptions().sameOrigin();// 设置可以iframe访问

    }

    @Override
    @Bean
    //注入authenticationManager
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
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
