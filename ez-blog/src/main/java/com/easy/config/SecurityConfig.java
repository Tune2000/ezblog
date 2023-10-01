package com.easy.config;

import com.easy.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author wxx
 * @create 08--07--10:49
 * @description:
 */
@Configuration
//WebSecurityConfigurerAdapter是Security官方提供的类
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 在SecurityConfig中配置把AuthenticationManager注入容器
    // 让BlogLoginService通过authenticate方法来进行用户认证
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    //注入我们在ez-blog工程写的JwtAuthenticationTokenFilter过滤器
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    //注入官方的认证失败的处理器。注意不用写private，并且不是注入我们自定义的认证失败处理器。理由:符合开闭原则
    //虽然我们注入的不是自己写的认证失败处理器，但是最终用的实际上就是我们写的，Security会自己去找我们写的
    AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    //注入官方的授权失败的处理器。注意不用写private，并且不是注入我们自定义的授权失败处理器。理由:符合开闭原则
    //虽然我们注入的不是自己写的授权失败处理器，但是最终用的实际上就是我们写的，Security会自己去找我们写的
    AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/login").anonymous()
                //注销接口需要认证才能访问
                .antMatchers("/logout").authenticated()
                .antMatchers("/user/userInfo").authenticated()
                // .antMatchers("/upload").authenticated()
                // 除上面外的所有请求全部不需要认证即可访问
                .anyRequest().permitAll();

        //配置异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);


        http.logout().disable();
        //把我们在ez-blog工程写的JwtAuthenticationTokenFilter过滤器添加到Security的过滤器链中
        //第一个参数是你要添加的过滤器，第二个参数是你想把你的过滤器添加到哪个security官方过滤器之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //允许跨域
        http.cors();
    }

    @Bean
    //把官方的PasswordEncoder密码加密方式替换成BCryptPasswordEncoder
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
