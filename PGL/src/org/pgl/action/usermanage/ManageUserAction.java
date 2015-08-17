package org.pgl.action.usermanage;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.User;
import org.pgl.service.UserManageService;
import org.pgl.util.EncryptUtil;

import com.opensymphony.xwork2.ActionSupport;

public class ManageUserAction extends ActionSupport
{
	private static final long serialVersionUID = 7755107452941399705L;
	private static Log logger = LogFactory.getLog(ManageUserAction.class);
	private String id;
	private String name;
	private String nickname;
	private String password;
	private String role;
	private int delTabRole;// �Ƿ����ɾ��TabȨ��
	private HttpServletRequest request;
	HttpServletResponse response;
	private List<User> lstUser = new ArrayList<User>();
	private UserManageService ums = new UserManageService();

	public String execute() throws Exception
	{
		try
		{
			lstUser = ums.getAllUser();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String deleteUser()
	{
		// ����־
		request = ServletActionContext.getRequest();
		Object obj = request.getSession().getAttribute("loginuser");
		String userName = null;
		if (obj != null)
		{
			User user = (User) obj;
			userName = user.getName();
		}
		logger.info(String.format("[%s] ɾ���û�, ��ɾ���û���ID:%s", userName, id));

		ums.deleteUser(Long.parseLong(id));
		lstUser = ums.getAllUser();
		return SUCCESS;
	}

	public String addUser() throws Exception
	{
		// ���⺺������
		name = new String(name.getBytes("ISO-8859-1"), "UTF-8");

		request = ServletActionContext.getRequest();
		Object obj = request.getSession().getAttribute("loginuser");
		String userName = null;
		if (obj != null)
		{
			User user = (User) obj;
			userName = user.getName();
		}
		logger.info(String.format("[%s] ���һ���µ��û����û���:%s ��ɫ:%s", userName, name, role));

		// ��������
		password = EncryptUtil.parseByte2HexStr(EncryptUtil.encrypt(password, EncryptUtil.ENCRYPT_KEY));

		if ("0".equals(id))
		{
			ums.insertUser(name, delTabRole, password, Integer.parseInt(role));
		}
		else
		{
			updateUser();
		}
		lstUser = ums.getAllUser();
		return SUCCESS;
	}

	public String updateUser()
	{

		ums.updateUser(Long.parseLong(id), name, delTabRole, password, Integer.parseInt(role));
		// lstUser = ums.getAllUser();
		return SUCCESS;
	}

	public String checkUserName() throws Exception
	{
		boolean flag = ums.checkUserName(name);

		response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(flag);
		out.flush();
		out.close();

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

	public List<User> getLstUser()
	{
		return lstUser;
	}

	public void setLstUser(List<User> lstUser)
	{
		this.lstUser = lstUser;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public int getDelTabRole()
	{
		return delTabRole;
	}

	public void setDelTabRole(int delTabRole)
	{
		this.delTabRole = delTabRole;
	}

}
