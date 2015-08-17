package org.pgl.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.pgl.Model.MenuModel;
import org.pgl.service.MenuService;
import org.pgl.util.LM_Util;

import com.opensymphony.xwork2.ActionSupport;

public class EditAction extends ActionSupport
{
	private static final long serialVersionUID = 3886144207383975677L;
	private List<MenuModel> menuList;
	private String menu = null;
	private String projectid;

	// ����Ϣ�б���ת��ͼ��չʾ
	private int menuId;
	private int id;// �İ�ID �����İ���Ϣ
	private String anchorId;// ����ê����Ϣ
	private int clientTypeCode;

	private HttpServletRequest request;

	public String execute() throws Exception
	{
		try
		{
			menuList = new ArrayList<MenuModel>();
			MenuService service = new MenuService();
			if (projectid == null || projectid.equals(""))
			{
				return INPUT;
			}

			menuList = service.getMenuList(projectid);

			request = ServletActionContext.getRequest();
			request.getSession().setAttribute("menuList", menuList);
			request.getSession().setAttribute("projectid", projectid);
			request.getSession().setAttribute("se_clientMap", LM_Util.map);
			request.getSession().setAttribute("se_noMixtrueMap", LM_Util.noMixtureMap);
			request.getSession().setAttribute("languageList", LM_Util.languageList);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return LOGIN;
		}
		return SUCCESS;
	}

	public List<MenuModel> getMenuList()
	{
		return menuList;
	}

	public void setMenuList(List<MenuModel> menuList)
	{
		this.menuList = menuList;
	}

	public String getMenu()
	{
		return menu;
	}

	public void setMenu(String menu)
	{
		this.menu = menu;
	}

	public String getProjectid()
	{
		return projectid;
	}

	public void setProjectid(String projectid)
	{
		this.projectid = projectid;
	}

	public int getMenuId()
	{
		return menuId;
	}

	public void setMenuId(int menuId)
	{
		this.menuId = menuId;
	}

	public String getAnchorId()
	{
		return anchorId;
	}

	public void setAnchorId(String anchorId)
	{
		this.anchorId = anchorId;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(int clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
	}

}
