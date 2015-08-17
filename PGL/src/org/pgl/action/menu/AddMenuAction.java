package org.pgl.action.menu;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.MenuModel;
import org.pgl.service.MenuService;

import com.opensymphony.xwork2.ActionSupport;

public class AddMenuAction extends ActionSupport
{
	private static final long serialVersionUID = 5822226543528831790L;
	private static Log logger = LogFactory.getLog(AddMenuAction.class);

	private String parentid;
	private String projectid;
	private String name;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public String execute() throws Exception
	{
		List<MenuModel> menuList = new ArrayList<MenuModel>();
		if (parentid == null || parentid.length() <= 0 || name == null || name.length() <= 0 || projectid == null || projectid.length() <= 0)
		{
			request.setAttribute("errorMes", "");
			return ERROR;
		}

		MenuService service = new MenuService();
		response = ServletActionContext.getResponse();
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try
		{
			boolean result = service.insertMenu(name, Long.parseLong(parentid), Long.parseLong(projectid));
			menuList = service.getMenuList(projectid);
			String _r = "{\"id\":" + service.newNemuId + ",\"result\":" + result + "}";
			writer.println(_r);
		}
		catch (Exception e)
		{
			logger.error("��Ӳ˵��쳣", e);
			e.printStackTrace();
		}
		finally
		{
			writer.flush();
			writer.close();
		}

		request = ServletActionContext.getRequest();
		request.getSession().setAttribute("menuList", menuList);

		return SUCCESS;
	}

	public String getParentid()
	{
		return parentid;
	}

	public void setParentid(String parentid)
	{
		this.parentid = parentid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getProjectid()
	{
		return projectid;
	}

	public void setProjectid(String projectid)
	{
		this.projectid = projectid;
	}

}
