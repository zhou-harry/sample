package com.harry.mail.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author harry
 * @version 1.0
 * @title: MailDomain
 * @description: 邮件数据对象
 * @date 2019/5/5 15:55
 */
@Getter
@Setter
@AllArgsConstructor
public class MailDomain implements Serializable {

    private String from;
    private String to;
    private String subject;
    private String content;
}
