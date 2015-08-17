package org.pgl.action.menu;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.MenuModel;
import org.pgl.service.MenuService;

import com.opensymphony.xwork2.ActionSupport;

public class AddMenuRootAction extends ActionSupport
{
	private static final long serialVersionUID = 3765756925991986709L;
	private static Log logger = LogFactory.getLog(AddMenuRootAction.class);
	private String parentid;
	private String projectid;
	private String name;
	private HttpServletRequest request;

	public String execute() throws Exception
	{
		List<MenuModel> menuList = new ArrayList<MenuModel>();

		if (parentid == null || parentid.length() <= 0 || name == null || name.length() <= 0 || projectid == null || projectid.length() <= 0)
		{
			request.setAttribute("errorMes", "");
			return ERROR;
		}

		MenuService service = new MenuService();
		try
		{
			service.insertMenu(name, Long.parseLong(parentid), Long.parseLong(projectid));
			menuList = service.getMenuList(projectid);
		}
		catch (Exception e)
		{
			logger.error(String.format("Add Root Menu Exception:menuName:%s,projectId:%s", name, projectid), e);
			e.printStackTrace();
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
