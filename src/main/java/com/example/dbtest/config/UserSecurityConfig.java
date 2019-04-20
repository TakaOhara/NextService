package com.example.dbtest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.dbtest.domain.service.UserInfoService;

/**
 * Web Security設定ファイル
 */
@EnableWebSecurity
//@Order(1)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    //@Qualifier("UserInfoService")
    private UserInfoService userInfoService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(createAuthProvider());
    }

    private AuthenticationProvider createAuthProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userInfoService);
        authProvider.setPasswordEncoder(passwordEncoder); // Beanを使う

        return authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
//                .antMatchers("/", "/register/**").permitAll()
        

                // antMatchers mvcMatcherを使う
                .mvcMatchers("/", "/register/**").permitAll()  // **はそれより下の階層全て
                .anyRequest().authenticated()//それ以外は認証無しでのアクセス不可
                .and()
                .formLogin()
                .loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .defaultSuccessUrl("/task", true)
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //セキュリティ設定を無視
//        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/bootstrap/**");
        web.ignoring().mvcMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/bootstrap/**", "/webjars/**", "/favicon.ico");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
