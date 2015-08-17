package org.pgl.action.interceptor;

import org.apache.struts2.ServletActionContext;
import org.pgl.Model.User;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor
{
	private static final long serialVersionUID = -8184934485231339441L;

	public String intercept(ActionInvocation invocation) throws Exception
	{
		
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
		if (user == null)
		{
			System.out.println("��û�е�¼��");
			return "login";
		}
		else
		{
			System.out.println("-->loginUserName>" + user.getName());
			return invocation.invoke();
		}
	}
}
