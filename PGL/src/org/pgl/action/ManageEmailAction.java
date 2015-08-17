package org.pgl.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pgl.Model.EmailBean;
import org.pgl.service.ManageEmailService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * �ʼ�����
 * 
 * ɾ��һЩ��Ҫ���ݵ�ʱ���ʼ��������б���˻��յ��ʼ�
 * 
 * @author mazhanghui
 * 
 */
public class ManageEmailAction extends ActionSupport
{
	private static final long serialVersionUID = 6677845026715237673L;
	private static Log logger = LogFactory.getLog(ManageEmailAction.class);
	private List<EmailBean> emailList = new ArrayList<EmailBean>();
	private int id;
	private String email;
	private int state;
	private ManageEmailService service = new ManageEmailService();

	// GET ALL EMAIL
	public String execute() throws Exception
	{
		try
		{
			// ��ѯ����Email�б�
			emailList = service.getAllEmail();
		}
		catch (Exception e)
		{
			logger.error("Get All Email Exception", e);
			return ERROR;
		}
		return SUCCESS;
	}

	// Delete Email
	public String removeEmail() throws Exception
	{
		try
		{
			service.deleteEmail(id);
			emailList = service.getAllEmail();
		}
		catch (Exception e)
		{
			logger.error("Delete Email Exception:", e);
			return ERROR;
		}
		return SUCCESS;
	}

	// Add Email
	public String addEmail() throws Exception
	{
		try
		{
			service.addEmail(email, state);
			emailList = service.getAllEmail();
		}
		catch (Exception e)
		{
			logger.error("add Email Exception:", e);
			return ERROR;
		}
		return SUCCESS;
	}

	// getter setter
	public List<EmailBean> getEmailList()
	{
		return emailList;
	}

	public void setEmailList(List<EmailBean> emailList)
	{
		this.emailList = emailList;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

}
