package org.pgl.action.menu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.MenuModel;
import org.pgl.Model.User;
import org.pgl.service.MenuService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class EditMenuAction extends ActionSupport
{
	private static final long serialVersionUID = 2125323995765860369L;
	private static Log logger = LogFactory.getLog(EditMenuAction.class);
	private String updateid;
	private String updatename;
	private String parentid;
	private HttpServletRequest request;
	private String projectid;
	private HttpServletResponse response;

	public String execute() throws Exception
	{
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();

		Object obj = ServletActionContext.getRequest().getSession().getAttribute("loginuser");
		if (obj == null)
			return Action.LOGIN;

		User user = (User) obj;
		logger.info(String.format("[%s] ���²˵���menuId:%s menuName:%s", user.getName(), updateid, updatename));

		List<MenuModel> menuList = new ArrayList<MenuModel>();
		if (updateid == null || updateid.length() <= 0 || updatename == null || updatename.length() <= 0)
		{
			request.setAttribute("errorMes", getText("menuidnull"));
			return ERROR;
		}

		MenuService service = new MenuService();
		boolean result = false;
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		try
		{
			result = service.updateMenu(Long.parseLong(updateid), updatename);
			writer.println(result);
			menuList = service.getMenuList(projectid);
		}
		catch (Exception e)
		{
			logger.error(String.format("[%s] ���²˵��쳣-menuId:%s menuName:%s", user.getName(), updateid, updatename), e);
			e.printStackTrace();
		}
		finally
		{
			writer.flush();
			writer.close();
		}
		request.getSession().setAttribute("menuList", menuList);
		return SUCCESS;
	}

	public String updateMenuPid()
	{
		MenuService service = new MenuService();
		boolean result = false;
		response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = null;
		try
		{
			writer = response.getWriter();
			result = service.updateMenuPid(Long.parseLong(updateid), Long.parseLong(parentid));
			writer.println(result);
		}
		catch (IOException e)
		{
			logger.error("updateMenuPid Exception:", e);
			e.printStackTrace();
		}
		finally
		{
			writer.flush();
			writer.close();
		}
		return SUCCESS;
	}

	public String getUpdateid()
	{
		return updateid;
	}

	public void setUpdateid(String updateid)
	{
		this.updateid = updateid;
	}

	public String getUpdatename()
	{
		return updatename;
	}

	public void setUpdatename(String updatename)
	{
		this.updatename = updatename;
	}

	public String getProjectid()
	{
		return projectid;
	}

	public void setProjectid(String projectid)
	{
		this.projectid = projectid;
	}

	public String getParentid()
	{
		return parentid;
	}

	public void setParentid(String parentid)
	{
		this.parentid = parentid;
	}
}
