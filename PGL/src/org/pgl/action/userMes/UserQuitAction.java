package org.pgl.action.userMes;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class UserQuitAction extends ActionSupport
{
	private static final long serialVersionUID = -3539927923241630927L;

	public String execute() throws Exception
	{
		try
		{
			ServletActionContext.getRequest().getSession().invalidate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return SUCCESS;
		}
		return SUCCESS;
	}
}
