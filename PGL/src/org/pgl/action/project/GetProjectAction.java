package org.pgl.action.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.ProjectModel;
import org.pgl.Model.User;
import org.pgl.service.LoginService;
import org.pgl.util.DBLoggerUtil;
import org.pgl.util.LM_Util.OperationType;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class GetProjectAction extends ActionSupport
{
	private static final long serialVersionUID = -6372228219795493902L;
	private static Log logger = LogFactory.getLog(GetProjectAction.class);
	private static DBLoggerUtil dblog = DBLoggerUtil.getInstance();
	private HttpServletRequest request;
	private int projectId;
	private String projectShortName;
	private String projectName;

	public String execute() throws Exception
	{
		try
		{
			LoginService objService = new LoginService();
			List<ProjectModel> lstProject = objService.getAllProject();
			request = ServletActionContext.getRequest();
			request.setAttribute("lstProject", lstProject);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/*
	 * ɾ��һ����Ŀ
	 */
	public String removeProject()
	{
		User user = null;
		try
		{
			request = ServletActionContext.getRequest();
			Object obj = request.getSession().getAttribute("loginuser");
			if (obj == null)
				return Action.LOGIN;
			user = (User) obj;
			logger.info(String.format("[%s] ɾ����Ŀ!projectID:%s", user.getName(), projectId));
			LoginService objService = new LoginService();
			objService.removeProject(projectId);
			dblog.logger((int) user.getId(), OperationType.DELETE.getValue(), String.format("User %s delete project,projectID:%s", user.getName(), projectId));
			List<ProjectModel> lstProject = objService.getAllProject();
			request = ServletActionContext.getRequest();
			request.setAttribute("lstProject", lstProject);
		}
		catch (Exception e)
		{
			logger.error(String.format("[%s] ɾ����Ŀ�쳣!projectID:%s", user.getName(), projectId), e);
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * �޸���Ŀ����
	 */
	public String updateProject()
	{
		logger.info("update project name!");
		User user = null;
		try
		{
			if (projectId == 0 || projectName == null || projectName == "" || projectShortName == null || projectShortName == "")
				return ERROR;

			request = ServletActionContext.getRequest();
			Object obj = request.getSession().getAttribute("loginuser");
			if (obj == null)
				return Action.LOGIN;
			user = (User) obj;
			LoginService objService = new LoginService();
			objService.updateProject(projectId, projectName, projectShortName);
			dblog.logger((int)user.getId(), OperationType.UPDATE.getValue(), String.format("User %s update project,projectId:%s, projectName:%s, projectShortName:%s ", user.getName(),projectId, projectName, projectShortName));
			// �޸ĳɹ����ѯһ��
			List<ProjectModel> lstProject = objService.getAllProject();
			request.setAttribute("lstProject", lstProject);
		}
		catch (Exception e)
		{
			logger.error("update project name Exception!", e);
			return ERROR;
		}

		return SUCCESS;
	}

	// getter setter
	public int getProjectId()
	{
		return projectId;
	}

	public void setProjectId(int projectId)
	{
		this.projectId = projectId;
	}

	public String getProjectShortName()
	{
		return projectShortName;
	}

	public void setProjectShortName(String projectShortName)
	{
		this.projectShortName = projectShortName;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

}
