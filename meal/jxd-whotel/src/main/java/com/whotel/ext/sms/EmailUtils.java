package com.whotel.ext.sms;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtils {

	/**
	 * 发送邮件
	 * @param userEmail 用户账号
	 * @param userPsw 用户密码
	 * @param toEmail 接收账号
	 * @param subject 标题
	 * @param content 内容
	 */
	public static void sendEmail(final String userEmail,final String userPsw,String toEmail,String subject,String content){
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.163.com");
		properties.put("mail.smtp.port", "25");
		properties.put("mail.smtp.auth", "true");

		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userEmail, userPsw);
			}
		});
		try {
			// 根据session创建一个邮件消息
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);//"捷信达科技有限公司"
			message.setSentDate(new Date());

			Multipart multipart = new MimeMultipart();
			BodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(content, "text/html; charset=utf-8");
			multipart.addBodyPart(bodyPart);

			message.setContent(multipart);
			message.saveChanges();
			Transport.send(message);
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}
}
