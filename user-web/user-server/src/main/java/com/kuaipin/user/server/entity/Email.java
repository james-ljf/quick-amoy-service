package com.kuaipin.user.server.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 邮件实体
 * @Author: hyf
 * @DateTime: 2022/3/16 11:40
 */
@Data
public class Email implements Serializable {

    /**
     * 发送给谁
     */
    private String to;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     *  html模板
     */
    private String templateName;

    @Override
    public String toString() {
        return "Email{" +
                "to='" + to + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", templateName='" + templateName + '\'' +
                '}';
    }
}
