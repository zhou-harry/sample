package com.harry.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * AuthenticationManager 认证时候需要用的一个Provider
 * @author harry
 * @version 1.0
 * @title: SmsCodeAuthenticationProvider
 * @description: 提供手机号认证校验逻辑
 * @date 2019/5/11 16:56
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public SmsCodeAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken)authentication;

        UserDetails userDetails = userDetailsService.loadUserByUsername((String) authentication.getPrincipal());

        if(userDetails == null){
            throw new InternalAuthenticationServiceException("手机号认证失败");
        }
        //认证通过
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        //把认证之前得token里存的用户信息赋值给认证后的token对象
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    /**
     * 告诉AuthenticationManager，申明能处理的Token对象是SmsCodeAuthenticationToken
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
