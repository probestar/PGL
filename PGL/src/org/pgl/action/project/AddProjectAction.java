package org.pgl.action.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.ProjectModel;
import org.pgl.Model.User;
import org.pgl.service.LoginService;
import org.pgl.util.DBLoggerUtil;
import org.pgl.util.LM_Util.OperationType;

import com.opensymphony.xwork2.ActionSupport;

public class AddProjectAction extends ActionSupport
{
	private static final long serialVersionUID = 1510134483346360802L;
	private static Log logger = LogFactory.getLog(AddProjectAction.class);
	private static DBLoggerUtil dblog = DBLoggerUtil.getInstance();
	private String engShort;
	private String chinaShort;
	private HttpServletRequest request;
	HttpServletResponse response;

	public String execute() throws Exception
	{
		try
		{
			// �ж���Ŀ�Ƿ����
			request = ServletActionContext.getRequest();
			Object obj = request.getSession().getAttribute("loginuser");
			String userName = null;
			int userid = 0;
			if (obj != null)
			{
				User user = (User) obj;
				userName = user.getName();
				userid = (int)user.getId();
			}
			LoginService objService = new LoginService();
			if (engShort != null && engShort.length() > 0 && chinaShort != null && chinaShort.length() > 0)
			{
				if (!objService.projectExist(chinaShort, engShort))
				{
					objService.insertProject(chinaShort, engShort);
					logger.info(String.format("Add Project With ChineseName:%s EnglishName:%s userName:%s", chinaShort, engShort, userName));
					dblog.logger(userid, OperationType.INSERT.getValue(), String.format("User %s Add Project With ChineseName:%s ,EnglishName:%s ", userName,chinaShort, engShort));
				}
				else
				{

				}
			}
			List<ProjectModel> lstProject = objService.getAllProject();
			request.setAttribute("lstProject", lstProject);
		}
		catch (Exception e)
		{
			logger.error("�����Ŀ�����쳣", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getEngShort()
	{
		return engShort;
	}

	public void setEngShort(String engShort)
	{
		this.engShort = engShort;
	}

	public String getChinaShort()
	{
		return chinaShort;
	}

	public void setChinaShort(String chinaShort)
	{
		this.chinaShort = chinaShort;
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}

}
