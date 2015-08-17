package org.pgl.action.login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.ProjectModel;
import org.pgl.Model.User;
import org.pgl.service.LoginService;
import org.pgl.util.EncryptUtil;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport
{
	private static final long serialVersionUID = -8246570622823667140L;
	private static Log logger = LogFactory.getLog(LoginAction.class);

	private String loginname;
	private String loginpwd;
	private String user_local;
	private HttpServletRequest request;

	public String execute() 
	{
		try
		{
			this.request = ServletActionContext.getRequest();
			if (loginname == null || loginname.length() <= 0 || loginpwd == null || loginpwd.length() <= 0)
			{
				request.setAttribute("errorMes", getText("loginempty"));
				//return ERROR;
				return INPUT;
			}
			LoginService objService = new LoginService();
			loginpwd = EncryptUtil.getEncString(loginpwd);
			User user = objService.checkUser(loginname, loginpwd);
			if (user == null)
			{
				request.setAttribute("errorMes", getText("loginerro"));
				return INPUT;
			}
			else if(user.getName() == null){
				request.setAttribute("errorMes", getText("loginisexist"));
				return INPUT;
			}

			logger.info(String.format("%s Login System!", loginname));

			List<ProjectModel> lstProject = objService.getAllProject();
			request.setAttribute("lstProject", lstProject);
			request.getSession().setAttribute("loginuser", user);
			request.getSession().setAttribute("role", user.getRole());
			request.getSession().setAttribute("operforlang", user.getOperforlang());
			System.err.println("operforlang="+user.getOperforlang());
			//�����û���ѡ�������
			String _lang = user_local;
			System.err.println("lang=="+_lang);
			
			request.getSession().setAttribute("WW_TRANS_I18N_LOCALE", LocaleUtils.toLocale(_lang));
			//zh_CN
			
		}
		catch (Exception e)
		{
			logger.error(String.format("%s Login System! Exception:", loginname), e);
			e.printStackTrace();
			return ERROR;
		}

		return SUCCESS;
	}

	public String getLoginname()
	{
		return loginname;
	}

	public void setLoginname(String loginname)
	{
		this.loginname = loginname;
	}

	public String getLoginpwd()
	{
		return loginpwd;
	}

	public void setLoginpwd(String loginpwd)
	{
		this.loginpwd = loginpwd;
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getUser_local()
	{
		return user_local;
	}

	public void setUser_local(String user_local)
	{
		this.user_local = user_local;
	}
}
