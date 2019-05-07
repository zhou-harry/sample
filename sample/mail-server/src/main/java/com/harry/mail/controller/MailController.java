package com.harry.mail.controller;

import com.harry.mail.command.MailCommand;
import com.harry.mail.domain.MailDomain;
import com.harry.mail.response.MailResponseData;
import org.simplejavamail.mailer.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author harry
 * @version 1.0
 * @title: MailController
 * @description: TODO
 * @date 2019/5/5 10:08
 */
@RestController()
@RequestMapping("mail")
public class MailController {

    @Value("${spring.application.name}")
    private String serviceAppName;

    @Autowired // or roll your own, as long as SimpleJavaMailSpringSupport is processed first
    private Mailer mailer;

    @PostMapping("send")
    public MailResponseData sendMail(String toEmail,String subject,String content){

        return new MailCommand(
                serviceAppName,
                mailer,
                new MailDomain(null,toEmail,subject,content)
                ).execute();
    }

}
