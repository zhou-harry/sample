package com.harry.mail.command;

import com.harry.mail.domain.MailDomain;
import com.harry.mail.response.MailResponseData;
import com.harry.mail.response.ResultCode;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;

/**
 * @author harry
 * @version 1.0
 * @title: MailCommand
 * @description: TODO
 * @date 2019/5/5 15:49
 */
public class MailCommand extends HystrixCommand<MailResponseData> {

    private final Mailer mailer;
    private final MailDomain domain;

    public MailCommand(String group, Mailer mailer, MailDomain info) {
        super(HystrixCommandGroupKey.Factory.asKey(group),1000);
        this.mailer=mailer;
        this.domain=info;
    }

    @Override
    protected MailResponseData run() throws Exception {
        Email email = EmailBuilder.startingBlank()
                .to(domain.getTo())
                .withSubject(domain.getSubject())
                .withPlainText(domain.getContent())
                .buildEmail();

        mailer.sendMail(email,true);

        return new MailResponseData(ResultCode.SUCCESS);
    }

    @Override
    protected MailResponseData getFallback() {
        return new MailResponseData(ResultCode.TIMEOUT);
    }

}
