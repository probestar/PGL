package org.pgl.mail;

import java.util.ArrayList;
import java.util.Vector;

public class MailBean
{
	private String to; // �ռ���
	private String from; // ������
	private String host; // SMTP����
	private String username; // �����˵��û���
	private String password; // �����˵�����
	private String subject; // �ʼ�����
	private String content; // �ʼ�����
	Vector<String> file; // �������
	private String filename; // �������ļ���
	private ArrayList<String> CCList;// �����б�

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public Vector<String> getFile()
	{
		return file;
	}

	public void attachFile(String fileName)
	{
		if (file == null)
			file = new Vector<String>();
		file.addElement(fileName);
	}

	public ArrayList<String> getCCList()
	{
		return CCList;
	}

	public void setCCList(ArrayList<String> cCList)
	{
		CCList = cCList;
	}
}
