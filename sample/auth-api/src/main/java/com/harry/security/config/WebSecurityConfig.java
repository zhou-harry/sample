package com.harry.security.config;

import com.harry.database.config.DynamicDataSourceConfig;
import com.harry.database.config.StaticDataSourceConfig;
import com.harry.security.filter.ValidateCodeFilter;
import com.harry.security.properties.SecurityProperties;
import com.harry.security.util.SecurityCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@ComponentScan({"com.harry.security"})
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
@AutoConfigureAfter({StaticDataSourceConfig.class, DynamicDataSourceConfig.class})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private UserDetailsService dbUserDetailsService;

    private final DataSource defaultDataSource;

    private final SecurityProperties securityProperties;
    private final AuthenticationSuccessHandler baseAuthenticationSuccessHandler;
    private final AuthenticationFailureHandler baseAuthenticationFailureHandler;

    public WebSecurityConfig(@Qualifier("defaultDataSource")DataSource defaultDataSource, SecurityProperties securityProperties, AuthenticationSuccessHandler baseAuthenticationSuccessHandler, AuthenticationFailureHandler baseAuthenticationFailureHandler) {
        this.defaultDataSource = defaultDataSource;
        this.securityProperties = securityProperties;
        this.baseAuthenticationSuccessHandler = baseAuthenticationSuccessHandler;
        this.baseAuthenticationFailureHandler = baseAuthenticationFailureHandler;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(defaultDataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("认证授权配置");
        //自定义验证码校验
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(baseAuthenticationFailureHandler, securityProperties);
        validateCodeFilter.afterPropertiesSet();
        http
            //处理校验码校验的逻辑放在UsernamePasswordAuthenticationFilter之前
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                //表单认证相关配置
                .formLogin()
                .loginPage(SecurityCode.LOGIN_PAGE)
                .loginProcessingUrl(SecurityCode.LOGIN_PROCESSINGURL)
                .successHandler(baseAuthenticationSuccessHandler)
                .failureHandler(baseAuthenticationFailureHandler)
                //Remember me 相关配置
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(dbUserDetailsService)
                //授权相关的配置
                .and().authorizeRequests()
                .antMatchers(SecurityCode.MATCHERS).permitAll()
                .antMatchers(securityProperties.getBrowser().getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable()
        ;
    }
}
