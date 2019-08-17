package com.zxf.controller;

import java.io.File;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
public class MailController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	  
    @Value("${mail.fromMail.sender}")  
    private String sender;// 发送者  
  
    @Value("${mail.fromMail.receiver}")  
    private String receiver;// 接受者  
  
    @Autowired  
    private JavaMailSender javaMailSender;
    
    @Autowired
    private TemplateEngine templateEngine;
  
    /** 
     * @Description http://localhost:8091/sendMail 
     * @method 发送文本邮件 
     * @return 
     */  
    @RequestMapping("/sendMail")  
    public String sendMail() {  
        SimpleMailMessage message = new SimpleMailMessage();  
        message.setFrom(sender);  
        message.setTo(receiver);  
        message.setSubject(" 打酱油的程序员发送文本邮件测试");// 标题  
        message.setText("世界，你好！--->这是我的文本邮件");// 内容  
        try {  
            javaMailSender.send(message);  
            logger.info("文本邮件发送成功！");  
            return "success";  
        } catch (Exception e) {  
            logger.error("文本邮件发送异常！", e);  
            return "failure";  
        }  
    }  
  
    /** 
     * @Description http://localhost:8091/sendHtmlMail 
     * @method 发送html邮件 
     * @return 
     */  
    @RequestMapping("/sendHtmlMail")  
    public String testHtmlMail() {  
        String content = "<html><body><h3>hello world ! --->Html邮件</h3></body></html>";  
        MimeMessage message = javaMailSender.createMimeMessage();  
  
        try {  
            //true表示需要创建一个multipart message  
            MimeMessageHelper helper = new MimeMessageHelper(message, true);  
            helper.setFrom(sender);  
            helper.setTo(receiver);  
            helper.setSubject("打酱油的程序员发送Html邮件测试");  
            helper.setText(content, true);  
  
            javaMailSender.send(message);  
            logger.info("Html邮件发送成功！");  
            return "success";  
        } catch (MessagingException e) {  
            logger.error("Html邮件发送异常！", e);  
            return "failure";  
        }  
    }  
  
    /** 
     * @Description http://localhost:8091/sendFilesMail 
     * @method 发送附件邮件 
     * @return 
     */  
    @RequestMapping("/sendFilesMail")  
    public String sendFilesMail() {  
        String filePath = "C:\\Users\\lancoo\\Desktop\\sql.txt";  
        MimeMessage message = javaMailSender.createMimeMessage();  
  
        try {  
            MimeMessageHelper helper = new MimeMessageHelper(message, true);  
            helper.setFrom(sender);  
            helper.setTo(receiver);  
            helper.setSubject("打酱油的程序员发送附件邮件测试");  
            helper.setText("一封带附件的邮件", true);  
  
            FileSystemResource file = new FileSystemResource(new File(filePath));  
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));  
            helper.addAttachment(fileName, file);  
  
            javaMailSender.send(message);  
            logger.info("附件邮件发送成功！");  
            return "success";  
        } catch (MessagingException e) {  
            logger.error("附件邮件发送异常！", e);  
            return "failure";  
        }  
    }  
  
    /** 
     * @Description http://localhost:8091/sendInlineResourceMail 
     * @method 发送图片邮件 
     * @return 
     */  
    @RequestMapping("/sendInlineResourceMail")  
    public String sendInlineResourceMail() {  
        String id = "打酱油的程序员";  
        // cid:内嵌资源  
        String content = "<html><body>带有图片的邮件：<img src=\'cid:" + id + "\'></body></html>";  
        String imgPath = "C:\\Users\\lancoo\\Desktop\\1.PNG";  
        MimeMessage message = javaMailSender.createMimeMessage();  
  
        try {  
            MimeMessageHelper helper = new MimeMessageHelper(message, true);  
            helper.setFrom(sender);  
            helper.setTo(receiver);  
            helper.setSubject("打酱油的程序员发送图片邮件测试");  
            helper.setText(content, true);  
  
            FileSystemResource res = new FileSystemResource(new File(imgPath));  
            helper.addInline(id, res);  
  
            javaMailSender.send(message);  
            logger.info("图片邮件发送成功！");  
            return "success";  
        } catch (MessagingException e) {  
            logger.error("图片邮件发送异常！", e);  
            return "failure";  
        }  
  
    } 
    /** 
     * @Description http://localhost:8091/sendTemplateMail 
     * @method 创建模板邮件并发送 
     * @return 
     */
    @RequestMapping("/sendTemplateMail")
    public String sendTemplateMail() {
    	 MimeMessage message = javaMailSender.createMimeMessage();//构建一个邮件对象
        try {  
        	//创建邮件正文
            Context context = new Context();
            context.setVariable("id", "006");
            String emailContent = templateEngine.process("emailTemplate", context);
            
            //true表示需要创建一个multipart message  
            MimeMessageHelper helper = new MimeMessageHelper(message, true);  
            helper.setSubject("主题：这是打酱油的程序员模板邮件");  //设置邮件主题
            helper.setFrom(sender);  //设置邮件发送者
            helper.setTo(receiver);  //设置邮件接收者
            helper.setSentDate(new Date()); //设置邮件发送日期
            helper.setText(emailContent, true);//设置邮件正文
            
            javaMailSender.send(message);  //发送邮件
            logger.info("模板邮件发送成功！");  
            return "success";  
        } catch (MessagingException e) {  
            logger.error("模板邮件发送异常！", e);  
            return "failure";  
        }
    }

}
