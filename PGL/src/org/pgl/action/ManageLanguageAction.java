package org.pgl.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.LanguageBean;
import org.pgl.Model.LanguageInfoBean;
import org.pgl.Model.User;
import org.pgl.service.ManageLanguageService;
import org.pgl.util.DBLoggerUtil;
import org.pgl.util.LM_Util;
import org.pgl.util.LM_Util.OperationType;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ����������Ϣ
 * 
 * @author mazhanghui
 * 
 */
public class ManageLanguageAction extends ActionSupport
{
	private static final long serialVersionUID = 3786055879839170530L;
	private static Log logger = LogFactory.getLog(ManageLanguageAction.class);
	private static DBLoggerUtil dblog = DBLoggerUtil.getInstance();
	private int lan_id;
	private String languageName;
	private int languageCode;
	private String ios_fileName;
	private String android_fileName;
	private List<LanguageInfoBean> lan_List = new ArrayList<LanguageInfoBean>();

	HttpServletResponse response;
	private ManageLanguageService service = new ManageLanguageService();

	/*
	 * ��ȡ�����б�
	 */
	public String execute() throws Exception
	{
		try
		{
			lan_List = service.getAllLanguage();
		}
		catch (Exception e)
		{
			logger.error("��ȡ���е������б�-�쳣", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * ����µ�����
	 */
	public String addLanguage() throws Exception
	{
		try
		{
			// ���⺺������
			
			languageName = new String(languageName.getBytes("ISO-8859-1"), "UTF-8");
			
			if (lan_id == 0)
			{
				boolean _r = service.addLanguage(languageName, ios_fileName, android_fileName);
				logger.info(String.format("Add A New Language, LanguageName:%s", languageName));
				if (_r)
				{
					User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
					if (user == null)
						return Action.LOGIN; 
					dblog.logger((int)user.getId(), OperationType.INSERT.getValue(), String.format("User %s add a new Language ,LanguageName:%s", user.getName(),languageName));
				}
			}
			else
			{
				updateLanguage();
			}
			lan_List = service.getAllLanguage();
			syncLanguageUtil();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(String.format("����������쳣,LanguageName:%s", languageName), e);
		}

		return SUCCESS;
	}

	/*
	 * ɾ������ ɾ��...
	 */
	public String deleteLanguage() throws Exception
	{
		try
		{
			boolean _r = service.removeLanguage(lan_id, languageCode);
			
			if (_r)
			{
				User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
				if (user == null)
					return Action.LOGIN; 
				dblog.logger((int)user.getId(), OperationType.DELETE.getValue(), String.format("User %s delete a Language ,Language id:%s", user.getName(),lan_id));
			}
			lan_List = service.getAllLanguage();
			syncLanguageUtil();

			// ��sessionʧЧ ����ǰ̨�ͺ�̨�������ݲ�һ������
			HttpServletRequest request = ServletActionContext.getRequest();
			request.getSession().invalidate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(String.format("ɾ������-�쳣,LanguageID:%s", lan_id), e);
		}

		return SUCCESS;
	}

	/*
	 * �޸���������
	 */
	public String updateLanguage() throws Exception
	{
		try
		{
			boolean _r = service.updateLanguage(lan_id, languageName, ios_fileName, android_fileName);
			if (_r)
			{
				User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
				if (user == null)
					return Action.LOGIN; 
				dblog.logger((int)user.getId(), OperationType.UPDATE.getValue(), String.format("User %s update a Language ,Language id:%s,Language name:%s", user.getName(),lan_id,languageName));
			}
			lan_List = service.getAllLanguage();
			syncLanguageUtil();
		}
		catch (Exception e)
		{
			logger.error(String.format("�޸������쳣, LanguageName:%s", languageName), e);
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/*
	 * [AJAX У�����������ظ���...]
	 */
	public String checkLanguageName() throws Exception
	{
		boolean flag = service.checkLanguageName(languageName);

		response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		out.println(flag);
		out.flush();
		out.close();

		return SUCCESS;
	}

	/*
	 * [AJAX У��ios�����ظ���...]
	 */
	public String checkIOSName() throws Exception
	{
		boolean flag = service.checkIOSName(ios_fileName);

		response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		out.println(flag);
		out.flush();
		out.close();

		return SUCCESS;
	}

	/*
	 * [AJAX У��android�����ظ���...]
	 */
	public String checkAndroidName() throws Exception
	{
		boolean flag = service.checkAndroidName(android_fileName);

		response = ServletActionContext.getResponse();
		PrintWriter out = response.getWriter();
		out.println(flag);
		out.flush();
		out.close();

		return SUCCESS;
	}

	/*
	 * �޸����Ժ�Ҫ���³������е�����List
	 */
	private void syncLanguageUtil()
	{
		LM_Util.languageList.clear();
		LanguageBean bean = null;
		for (int i = 0; i < lan_List.size(); i++)
		{
			bean = new LanguageBean();
			bean.setLanguageCode(lan_List.get(i).getLanguageCode());
			bean.setLanguageName(lan_List.get(i).getLanguageName());

			LM_Util.languageList.add(bean);
		}
	}

	// getter setter
	public List<LanguageInfoBean> getLan_List()
	{
		return lan_List;
	}

	public void setLan_List(List<LanguageInfoBean> lan_List)
	{
		this.lan_List = lan_List;
	}

	public String getIos_fileName()
	{
		return ios_fileName;
	}

	public void setIos_fileName(String ios_fileName)
	{
		this.ios_fileName = ios_fileName;
	}

	public String getAndroid_fileName()
	{
		return android_fileName;
	}

	public void setAndroid_fileName(String android_fileName)
	{
		this.android_fileName = android_fileName;
	}

	public String getLanguageName()
	{
		return languageName;
	}

	public void setLanguageName(String languageName)
	{
		this.languageName = languageName;
	}

	public int getLan_id()
	{
		return lan_id;
	}

	public void setLan_id(int lan_id)
	{
		this.lan_id = lan_id;
	}

	public int getLanguageCode()
	{
		return languageCode;
	}

	public void setLanguageCode(int languageCode)
	{
		this.languageCode = languageCode;
	}

}
