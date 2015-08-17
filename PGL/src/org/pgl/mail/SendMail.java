package org.pgl.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
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
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pgl.util.LM_Util;

public class SendMail
{
	private static Log logger = LogFactory.getLog(SendMail.class);

	public void sendEmail(String message)
	{
		MailBean mb = new MailBean();
		mb.setHost("smtp.fetionyy.com.cn"); // ����SMTP����(163)������126������Ϊ��smtp.126.com
		mb.setUsername("bugfree"); // ���÷�����������û���
		mb.setPassword("allstar2012"); // ���÷�������������룬�轫*�Ÿĳ���ȷ������
		mb.setFrom("bugfree@fetionyy.com.cn"); // ���÷����˵�����
		mb.setTo("mazhanghui@feinno.com"); // �����ռ��˵�����
		mb.setSubject("�����Թ���ƽ̨-����ɾ��֪ͨ"); // ����
		mb.setContent(message); // ����
		mb.setCCList(LM_Util.mailReceiverList);

		// ��Ӹ���
		// mb.attachFile("E:/test.xls");

		// �����ʼ�
		if (sendMail(mb))
			logger.info("�ʼ����ͳɹ�!");
		else
			logger.info("�ʼ�����ʧ��!");

	}

	public String toChinese(String text)
	{
		try
		{
			text = MimeUtility.encodeText(new String(text.getBytes(), "GB2312"), "GB2312", "B");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return text;
	}

	private boolean sendMail(MailBean mb)
	{
		String host = mb.getHost();
		final String username = mb.getUsername();
		final String password = mb.getPassword();
		String from = mb.getFrom();
		String to = mb.getTo();
		String subject = mb.getSubject();
		String content = mb.getContent();
		// String fileName = mb.getFilename();
		// Vector<String> file = mb.getFile();

		Properties props = System.getProperties();
		props.put("mail.smtp.host", host); // ����SMTP������
		props.put("mail.smtp.auth", "true"); // ��Ҫ������֤

		Session session = Session.getInstance(props, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(username, password);
			}
		});

		try
		{
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address_to = {new InternetAddress(to)};
			msg.setRecipients(Message.RecipientType.TO, address_to);

			// ����
			if (mb.getCCList() != null && mb.getCCList().size() > 0)
			{
				InternetAddress[] address_cc = new InternetAddress [mb.getCCList().size()];
				for (int i = 0; i < mb.getCCList().size(); i++)
				{
					address_cc[i] = new InternetAddress(mb.getCCList().get(i));
				}
				msg.setRecipients(Message.RecipientType.CC, address_cc);
			}
			msg.setSubject(subject);// thChinese(subject);

			Multipart mp = new MimeMultipart();
			MimeBodyPart mbpContent = new MimeBodyPart();
			mbpContent.setText(content);
			mp.addBodyPart(mbpContent);

			/* ���ʼ�����Ӹ��� */
			// Enumeration<String> efile = file.elements();
			// while (efile.hasMoreElements())
			// {
			// MimeBodyPart mbpFile = new MimeBodyPart();
			// fileName = efile.nextElement().toString();
			// FileDataSource fds = new FileDataSource(fileName);
			// mbpFile.setDataHandler(new DataHandler(fds));
			// mbpFile.setFileName(toChinese(fds.getName()));
			// mp.addBodyPart(mbpFile);
			// }

			msg.setContent(mp);
			msg.setSentDate(new Date());
			Transport.send(msg);

		}
		catch (MessagingException me)
		{
			me.printStackTrace();
			return false;
		}
		return true;
	}
}
