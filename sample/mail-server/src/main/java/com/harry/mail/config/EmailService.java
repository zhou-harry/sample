package com.harry.mail.config;

import org.simplejavamail.springsupport.SimpleJavaMailSpringSupport;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author harry
 * @version 1.0
 * @title: EmailService
 * @description: 邮件服务
 * @date 2019/5/5 10:04
 */
@Configuration
@Import(SimpleJavaMailSpringSupport.class)
public class EmailService {


}
