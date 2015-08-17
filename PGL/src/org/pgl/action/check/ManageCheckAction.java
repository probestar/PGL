package org.pgl.action.check;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.pgl.Model.AllLanguage;
import org.pgl.Model.CheckModel;
import org.pgl.Model.MenuModel;
import org.pgl.service.CheckService;
import org.pgl.service.MenuService;
import org.pgl.util.LM_Util;

import com.opensymphony.xwork2.ActionSupport;

public class ManageCheckAction extends ActionSupport
{
	private static final long serialVersionUID = 3415582878432820265L;

	private HttpServletRequest request;
	private String projectid;
	private List<CheckModel> lstCheck = new ArrayList<CheckModel>();
	private String menuid;
	private String id;
	private String searchkey;
	private int startPage;// ��ҳ-Ҫ��ȡ��һҳ
	private int totlePage;// ��ҳ��
	private int currentPage;// ��ǰҳ
	private List<AllLanguage> lstLanguage = new ArrayList<AllLanguage>();
	private List<MenuModel> lstMenu = new ArrayList<MenuModel>();

	/*
	 * ��ȡȫ���б���Ϣ (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception
	{
		request = ServletActionContext.getRequest();
		CheckService objService = new CheckService();
		MenuService objMenuSer = new MenuService();
		Object projectid = request.getSession().getAttribute("projectid");
		if (projectid == null)
		{
			return INPUT;
		}

		// Ϊ��ҳ��ѯ������׼��
		int count = objService.getListCount(String.valueOf(projectid), menuid, searchkey);
		startPage = 1;
		currentPage = 1;
		totlePage = count % LM_Util.SHOW_COUNT == 0 ? count / LM_Util.SHOW_COUNT : count / LM_Util.SHOW_COUNT + 1;
		if (totlePage == 0)
			totlePage = 1;
		// lstCheck = objService.getAllCheck(String.valueOf(projectid), menuid,
		// searchkey);
		lstCheck = objService.getList(Integer.valueOf((String) projectid), menuid, searchkey, 0);
		lstMenu = objMenuSer.getMenuList(String.valueOf(projectid));
		for (CheckModel tmp : lstCheck)
		{
			tmp.setPath(resetPath(tmp.getMenuParentId(), tmp.menuname));
		}
		request.setAttribute("menuList", lstMenu);

		return SUCCESS;
	}

	// �㼶������תΪ·��--�ݹ�
	private String resetPath(long parentId, String menuName)
	{
		if (parentId == -1)
			return menuName + ">";
		else
		{
			for (MenuModel tmp : lstMenu)
			{
				if (parentId == tmp.id)
				{
					return resetPath(tmp.parentid, tmp.name) + menuName;
				}
			}
			return menuName + ">";
		}
	}

	/*
	 * �޸���������
	 */
	public String saveCheck() throws Exception
	{
		try
		{
			CheckService objService = new CheckService();
			MenuService objMenuSer = new MenuService();

			if (lstLanguage != null && lstLanguage.size() > 0 && id != null && id.length() > 0)
			{
				objService.updateCheck(lstLanguage, Long.parseLong(id));
			}

			// lstCheck = objService.getAllCheck(projectid, menuid, searchkey);
			lstCheck = objService.getList(Integer.valueOf(projectid), menuid, searchkey, 0);
			for (CheckModel tmp : lstCheck)
			{
				tmp.setPath(resetPath(tmp.getMenuParentId(), tmp.menuname));
			}

			int count = objService.getListCount(String.valueOf(projectid), menuid, searchkey);
			startPage = 1;
			currentPage = 1;
			totlePage = count % LM_Util.SHOW_COUNT == 0 ? count / LM_Util.SHOW_COUNT : count / LM_Util.SHOW_COUNT + 1;
			if (totlePage == 0)
				totlePage = 1;

			lstMenu = objMenuSer.getMenuList(projectid);
			request = ServletActionContext.getRequest();
			request.setAttribute("menuList", lstMenu);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}

	/*
	 * ��ǰ��Ŀ�¸���searchKey��ѯ����
	 */
	public String searchCheck()
	{
		try
		{
			CheckService objService = new CheckService();
			MenuService objMenuSer = new MenuService();
			if (searchkey == null)
				return ERROR;
			searchkey = new String(searchkey.getBytes("ISO-8859-1"), "UTF-8");

			int count = objService.getListCount(String.valueOf(projectid), menuid, searchkey);
			startPage = 1;
			currentPage = 1;
			totlePage = count % LM_Util.SHOW_COUNT == 0 ? count / LM_Util.SHOW_COUNT : count / LM_Util.SHOW_COUNT + 1;
			if (totlePage == 0)
				totlePage = 1;
			// int startCount = (startPage - 1) * LM_Util.SHOW_COUNT;
			// lstCheck = objService.getAllCheck(projectid, menuid, searchkey);
			lstCheck = objService.getList(Integer.valueOf(projectid), menuid, searchkey, 0);

			lstMenu = objMenuSer.getMenuList(projectid);
			request = ServletActionContext.getRequest();
			for (CheckModel tmp : lstCheck)
			{
				tmp.setPath(resetPath(tmp.getMenuParentId(), tmp.menuname));
			}

			request.setAttribute("menuList", lstMenu);
			request.setAttribute("key", searchkey);
		}
		catch (UnsupportedEncodingException e)
		{
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * ��ҳ(��һҳ����һҳ-��ȡê����İ����б�)
	 */
	public String getAnchorList() throws Exception
	{
		try
		{
			request = ServletActionContext.getRequest();
			if (searchkey != null && !searchkey.equals("null"))
			{
				searchkey = new String(searchkey.getBytes("ISO-8859-1"), "UTF-8");
				request.getSession().setAttribute("searchkey", searchkey);
			}
			else
			{
				if (request.getSession().getAttribute("searchkey") != null)
				{
					searchkey = String.valueOf(request.getSession().getAttribute("searchkey"));
				}
			}

			request = ServletActionContext.getRequest();
			CheckService objService = new CheckService();
			MenuService objMenuSer = new MenuService();

			int startCount = (startPage - 1) * LM_Util.SHOW_COUNT;
			currentPage = startPage;

			Object sessionProjectId = request.getSession().getAttribute("projectid");
			if (sessionProjectId == null)
			{
				return INPUT;
			}
			lstCheck = objService.getList(Integer.valueOf((String) sessionProjectId), null, searchkey, startCount);
			for (CheckModel tmp : lstCheck)
			{
				tmp.setPath(resetPath(tmp.getMenuParentId(), tmp.menuname));
			}
			request.setAttribute("searchkey", searchkey);
			lstMenu = objMenuSer.getMenuList((String) sessionProjectId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		request.setAttribute("menuList", lstMenu);
		return SUCCESS;
	}

	// GETTER SETTER
	public String getProjectid()
	{
		return projectid;
	}

	public void setProjectid(String projectid)
	{
		this.projectid = projectid;
	}

	public List<CheckModel> getLstCheck()
	{
		return lstCheck;
	}

	public void setLstCheck(List<CheckModel> lstCheck)
	{
		this.lstCheck = lstCheck;
	}

	public String getMenuid()
	{
		return menuid;
	}

	public void setMenuid(String menuid)
	{
		this.menuid = menuid;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public List<AllLanguage> getLstLanguage()
	{
		return lstLanguage;
	}

	public void setLstLanguage(List<AllLanguage> lstLanguage)
	{
		this.lstLanguage = lstLanguage;
	}

	public List<MenuModel> getLstMenu()
	{
		return lstMenu;
	}

	public void setLstMenu(List<MenuModel> lstMenu)
	{
		this.lstMenu = lstMenu;
	}

	public String getSearchkey()
	{
		return searchkey;
	}

	public void setSearchkey(String searchkey)
	{
		this.searchkey = searchkey;
	}

	public int getStartPage()
	{
		return startPage;
	}

	public void setStartPage(int startPage)
	{
		this.startPage = startPage;
	}

	public int getTotlePage()
	{
		return totlePage;
	}

	public void setTotlePage(int totlePage)
	{
		this.totlePage = totlePage;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

}
