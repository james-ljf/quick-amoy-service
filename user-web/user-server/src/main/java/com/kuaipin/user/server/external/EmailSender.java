package com.kuaipin.user.server.external;

import com.kuaipin.user.server.entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * @Author: ljf
 * @DateTime: 2022/3/16 10:47
 */
@Component
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 发送Html版本的邮件
     */
    @Async("asyncPromiseExecutor")
    public void sendHtmlEmail(Email email){
        // 创建一个Thymeleaf的Context对象
        Context context = new Context();
        // 设置参数
        context.setVariable("to", email.getTo());
        context.setVariable("content", email.getContent());
        // 生成一个字符串类型的内容（将模板页面和上下文对象绑定）
        String c = templateEngine.process(email.getTemplateName(), context);
        // 准备一个邮件信息对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 设置从哪里发送
            helper.setFrom(new InternetAddress("17687443422@163.com", "快拼电商搜索系统", "UTF-8"));
            // 设置发送给谁
            helper.setTo(email.getTo());
            // 设置标题
            helper.setSubject(email.getTitle());
            // 设置内容
            helper.setText(c, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}