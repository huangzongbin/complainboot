package com.krt.mail.service;


/**
 * 邮件服务接口层
 *
 * @author 殷帅
 * @version 1.0
 * @date 2018年07月25日
 */
public interface IMailService {

    /**
     * 发送邮件
     *
     * @param to      接收人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String to, String subject, String content);


    /**
     * 发送html格式邮件
     *
     * @param to      接收人
     * @param subject 主题
     * @param content 内容
     */
    void sendHtmlMail(String to, String subject, String content);


    /**
     * 发送带附件的邮件
     *
     * @param to       接收人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件地址
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    /**
     * 发送带静态资源的邮件
     *
     * @param to      接收人
     * @param subject 主题
     * @param content 内容
     * @param rscPath 资源地址
     * @param rscId   资源id
     */
    void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId);
}
