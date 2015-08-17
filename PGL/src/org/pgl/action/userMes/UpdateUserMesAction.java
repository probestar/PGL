package org.pgl.action.userMes;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.User;
import org.pgl.service.LoginService;
import org.pgl.util.EncryptUtil;

import com.opensymphony.xwork2.ActionSupport;

public class UpdateUserMesAction extends ActionSupport
{
	private static final long serialVersionUID = 5906660464394172624L;
	private static Log logger = LogFactory.getLog(UpdateUserMesAction.class);
	private HttpServletRequest request;
	private String id;
	private String loginpwd;

	public String execute() throws Exception
	{
		try
		{
			request = ServletActionContext.getRequest();
			if (id == null || id.length() <= 0 || loginpwd == null || loginpwd.length() <= 0)
			{
				request.setAttribute("updateMes", getText("changepwdfail"));
				return ERROR;
			}

			// ���û�������������
			loginpwd = EncryptUtil.getEncString(loginpwd);

			LoginService service = new LoginService();
			boolean bIsSuc = service.updateUserMes(id, loginpwd);
			User user = service.getUserById(id);
			request.getSession().setAttribute("loginuser", user);
			request.setAttribute("user", user);
			if (bIsSuc)
			{
				request.setAttribute("updateMes", getText("changepwdsuc"));
			}
			else
			{
				request.setAttribute("updateMes", getText("changepwdfail"));
			}
		}
		catch (Exception e)
		{
			logger.error("�޸��˻���Ϣ�쳣", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getLoginpwd()
	{
		return loginpwd;
	}

	public void setLoginpwd(String loginpwd)
	{
		this.loginpwd = loginpwd;
	}

}
