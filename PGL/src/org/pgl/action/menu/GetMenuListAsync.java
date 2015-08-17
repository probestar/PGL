package org.pgl.action.menu;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.MenuModel;
import org.pgl.service.MenuService;

import com.opensymphony.xwork2.ActionSupport;

public class GetMenuListAsync extends ActionSupport
{
	private static final long serialVersionUID = 2428176331265697031L;
	private static Log logger = LogFactory.getLog(GetMenuListAsync.class);

	private String projectid;
	private HttpServletResponse response;

	public String execute() throws Exception
	{
		String returnStrModel = "{\"id\":%s,\"projectid\":%s,\"pId\":%s,\"name\":\"%s\",\"click\":\"parent.mainFrame.location.href='loadAnchor.action?projectId=%s&menuId=%s'\"}";
		try
		{
			StringBuffer _sb = new StringBuffer();
			MenuService service = new MenuService();
			response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			if (projectid != null && !"".equals(projectid))
			{
				List<MenuModel> mlist = service.getMenuList(projectid);
				if (mlist.size() > 0)
				{
					for (Iterator<MenuModel> iterator = mlist.iterator(); iterator.hasNext();)
					{
						MenuModel menuModel = iterator.next();
						if (_sb.length() > 0)
							_sb.append(",");
						_sb.append(String.format(returnStrModel, menuModel.getId(), projectid, menuModel.getParentid(), menuModel.getName(), projectid, menuModel.getId()));
					}
				}
			}
			writer.println("[" + _sb.toString() + "]");
			writer.flush();
			writer.close();
		}
		catch (Exception e)
		{
			logger.error(String.format("��ȡ�˵��б��쳣,returnStrModel=%s", returnStrModel), e);
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public static void main(String[] args)
	{
		String projectid = "1";
		String returnStrModel = "{\"id\":%s,\"projectid\":%s,\"pId\":%s,\"name\":\"%s\"}";
		StringBuffer _sb = new StringBuffer();
		MenuService service = new MenuService();
		if (projectid != null && !"".equals(projectid))
		{
			List<MenuModel> mlist = service.getMenuList(projectid);
			if (mlist.size() > 0)
			{
				for (Iterator<MenuModel> iterator = mlist.iterator(); iterator.hasNext();)
				{
					MenuModel menuModel = iterator.next();
					if (_sb.length() > 0)
						_sb.append(",");
					_sb.append(String.format(returnStrModel, menuModel.getId(), projectid, menuModel.getParentid(), menuModel.getName(), projectid, menuModel.getId()));
				}
			}
		}
		System.err.println(_sb.toString());
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
