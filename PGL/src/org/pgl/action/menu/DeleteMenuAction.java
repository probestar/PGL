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
import org.pgl.Model.User;
import org.pgl.mail.SendMail;
import org.pgl.service.MenuService;

import com.opensymphony.xwork2.ActionSupport;

public class DeleteMenuAction extends ActionSupport
{
	private static final long serialVersionUID = 6484066331916522180L;
	private static Log logger = LogFactory.getLog(DeleteMenuAction.class);
	private String id;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String projectid;

	public String execute() throws Exception
	{
		logger.info("Delete Menu With ProjectId:" + projectid + " menuId:" + id);

		List<MenuModel> menuList = new ArrayList<MenuModel>();

		if (id == null || id.length() <= 0)
		{
			request.setAttribute("errorMes", getText("menuidnull"));
			return ERROR;
		}
		MenuService service = new MenuService();
		response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter writer = response.getWriter();
		boolean delResult = false;

		try
		{
			// ������ӽڵ�����ӽڵ�Ҳɾ��
			List<MenuModel> menuDelList = new ArrayList<MenuModel>();

			menuDelList = service.getNowDeleteMenu(Long.parseLong(id));

			delResult = service.deleteMenu(menuDelList, projectid);
			writer.print(delResult);

			menuList = service.getMenuList(projectid);
			
			// ���ʼ�֪ͨ
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
			String mess = String.format("[%s]ɾ���˲˵�.��ĿID��%s,�˵�ID��%s.", user.getName(), projectid, id);
			SendMail sm = new SendMail();
			sm.sendEmail(mess);
		}
		catch (Exception e)
		{
			logger.info("Delete Menu With ProjectId:" + projectid + " menuId:" + id + " Exception:", e);
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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
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
