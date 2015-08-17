package org.pgl.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.pgl.service.CheckAnchorKeyService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ͬһ����Ŀͬһ���ͻ����²��������ͬ����KEY
 * 
 * @author mazhanghui
 * 
 */
public class CheckAnchorKeyAction extends ActionSupport
{
	private static final long serialVersionUID = -5977608915999799801L;

	private int projectId;
	private int clientTypeCode;
	private String anchorKey;
	HttpServletResponse response;
	
	private CheckAnchorKeyService checkService = new CheckAnchorKeyService();
	
	public String execute() throws Exception 
	{
		try
		{
			System.out.println("Check Duplicate Key ...");
			
			boolean flag = checkService.checkDuplicateKey(projectId, clientTypeCode, anchorKey);
			
			response = ServletActionContext.getResponse();
			PrintWriter out = response.getWriter();
			out.println(flag);
			out.flush();
			out.close();
		
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// getter setter
	public int getProjectId()
	{
		return projectId;
	}

	public void setProjectId(int projectId)
	{
		this.projectId = projectId;
	}

	public int getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(int clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
	}

	public String getAnchorKey()
	{
		return anchorKey;
	}

	public void setAnchorKey(String anchorKey)
	{
		this.anchorKey = anchorKey;
	}

}
