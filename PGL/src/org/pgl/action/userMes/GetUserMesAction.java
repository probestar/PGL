package org.pgl.action.userMes;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.pgl.Model.User;
import org.pgl.util.EncryptUtil;

import com.opensymphony.xwork2.ActionSupport;

public class GetUserMesAction extends ActionSupport
{
	private static final long serialVersionUID = -4616638331048338498L;
	private HttpServletRequest request;
	private String password;

	public String execute() throws Exception
	{
		request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute("loginuser");
		request.setAttribute("user", user);

		password = EncryptUtil.getDecString(user.getPassword());

		return SUCCESS;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

}
