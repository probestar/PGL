package org.pgl.action.project;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.pgl.service.LoginService;

import com.opensymphony.xwork2.ActionSupport;

public class CheckProjectNameAction extends ActionSupport
{
	private static final long serialVersionUID = 9220760329316191555L;
	private HttpServletResponse response;
	private HttpServletRequest request;

	public String checkProjectExist()
	{
		try
		{
			request = ServletActionContext.getRequest();
			String cname = request.getParameter("cname");
			String ename = request.getParameter("ename");
			boolean isExist = false;
			LoginService objService = new LoginService();
			response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer;
			try
			{
				writer = response.getWriter();
				isExist = objService.projectExist(cname, ename);
				writer.print(isExist);
				writer.flush();
				writer.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
