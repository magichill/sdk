package com.bingdou.tools;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
import java.util.Random;

/**
 * 邮件工具类
 */
public class EmailUtil {

    private static final String[] avalivleMails;
    private static final JavaMailSenderImpl mailSender;
    private static final Random random;

    private EmailUtil() {
    }

    static {

        //TODO 发送邮件的邮箱账号
        avalivleMails = new String[]{"gaoshan12321@163.com"};
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qiye.163.com");
        Properties mailProperty = new Properties();
        mailProperty.put("mail.smtp.auth", true);
        mailProperty.put("mail.smtp.timeout", 25000);
        mailSender.setJavaMailProperties(mailProperty);
        mailSender.setPassword("gaoshan12321");
        random = new Random();
    }

    public static boolean sendEmail(String toEmail, String title, String content) {
        String mailUser = avalivleMails[random.nextInt(avalivleMails.length)];
        mailSender.setUsername(mailUser);
        long beginTime = System.currentTimeMillis();
        SimpleMailMessage mail = new SimpleMailMessage();
        try {
            mail.setTo(toEmail);
            mail.setFrom(mailUser);
            mail.setSubject(title);
            mail.setText(content);
            mailSender.send(mail);
            RecordLogger.mailLog(mailUser, toEmail, title, content);
        } catch (MailException e) {
            LogContext.instance().error(e, "邮件发送失败");
            return false;
        } finally {
            long endTime = System.currentTimeMillis();
            RecordLogger.timeLog("java-send-mail", endTime - beginTime);
        }
        return true;
    }
}
