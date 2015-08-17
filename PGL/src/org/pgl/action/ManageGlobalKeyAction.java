/*
 * ����ȫ�����͵�KEY<�ɱ����õ�KEY>
 */
package org.pgl.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.GlobalKeyBean;
import org.pgl.Model.LanguageBean;
import org.pgl.Model.User;
import org.pgl.service.ManageGlobalKeyService;
import org.pgl.util.DBLoggerUtil;
import org.pgl.util.LM_Util;
import org.pgl.util.LM_Util.OperationType;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ManageGlobalKeyAction extends ActionSupport
{
	private static final long serialVersionUID = 8197541992562211188L;
	private static Log logger = LogFactory.getLog(ManageGlobalKeyAction.class);
	private static DBLoggerUtil dblog = DBLoggerUtil.getInstance();
	private List<GlobalKeyBean> globalKeyList = new ArrayList<GlobalKeyBean>();
	private List<LanguageBean> list = new ArrayList<LanguageBean>();

	private ManageGlobalKeyService service = new ManageGlobalKeyService();
	private String anchorKey;
	private String remark;
	private String allLanguage;
	private int languageDataId;

	/**
	 * ��������GlobalKEY����Ŀ���GlobalKey����
	 */
	public String execute() throws Exception
	{
		try
		{
			globalKeyList = service.getAllGlobalKey();
			LanguageBean bean = null;
			list.clear();
			for (LanguageBean bb : LM_Util.languageList)
			{
				bean = new LanguageBean();
				bean.setLanguageName(bb.getLanguageName());
				bean.setLanguageCode(bb.getLanguageCode());
				list.add(bean);
			}
		}
		catch (Exception e)
		{
			logger.error("����ȫ�����͵�KEY�쳣:", e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * ���GlobalKey
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addGlobalKey() throws Exception
	{
		StringBuilder sql = new StringBuilder("INSERT INTO languageData(project_id,menu_id,client_code,global_type,anchor_key,remark");
		try
		{
			Object projectId = ServletActionContext.getRequest().getSession().getAttribute("projectid");
			if (projectId == null)
			{
				return ERROR;
			}

			String[] tabs = allLanguage.split(LM_Util.GROUP_SEPARATOR);

			for (String str : tabs)
			{
				sql.append(",").append(str.split(LM_Util.DATA_SEPARATOR)[0]);
			}
			sql.append(")VALUES(").append(String.valueOf(projectId)).append(",0,0,1,").append("'").append(anchorKey).append("','").append(remark).append("'");
			for (String str : tabs)
			{
				if (str.split(LM_Util.DATA_SEPARATOR).length > 1)
				{
					sql.append(",'").append(str.split(LM_Util.DATA_SEPARATOR)[1].replace("'", "\\'")).append("'");
				}
				else
				{
					sql.append(",").append("''");
				}
			}
			sql.append(")");

			int _r = service.addGlobalKey(sql.toString());
			if (_r > 0)
			{
				User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
				if (user == null)
					return Action.LOGIN; 
				dblog.logger((int)user.getId(), OperationType.INSERT.getValue(), String.format("User %s add a new GlobalKey ,detail is: %s", user.getName(),sql.toString()));
			}
		}
		catch (Exception e)
		{
			logger.error(String.format("���ȫ�ֱ���KEY�쳣,SQL:%s,������Ϣ:%s", sql.toString(), allLanguage), e);
			e.printStackTrace();
		}

		execute();

		return SUCCESS;
	}

	/**
	 * ɾ��GlobalKey
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteGlobalKey() throws Exception
	{

		try
		{
			int _r = service.removeGlobalKey(languageDataId);
			if (_r > 0)
			{
				User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
				if (user == null)
					return Action.LOGIN; 
				dblog.logger((int)user.getId(), OperationType.DELETE.getValue(), String.format("User %s delete GlobalKey ,id is: %s", user.getName(),languageDataId));
			}
		}
		catch (Exception e)
		{
			logger.error(String.format("ɾ��GlobalKey�쳣,GlobalKeyID:", languageDataId), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * �޸�GlobalKey
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateGlobalKey() throws Exception
	{
		return SUCCESS;
	}

	public List<GlobalKeyBean> getGlobalKeyList()
	{
		return globalKeyList;
	}

	public void setGlobalKeyList(List<GlobalKeyBean> globalKeyList)
	{
		this.globalKeyList = globalKeyList;
	}

	public List<LanguageBean> getList()
	{
		return list;
	}

	public void setList(List<LanguageBean> list)
	{
		this.list = list;
	}

	public String getAnchorKey()
	{
		return anchorKey;
	}

	public void setAnchorKey(String anchorKey)
	{
		this.anchorKey = anchorKey;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getAllLanguage()
	{
		return allLanguage;
	}

	public void setAllLanguage(String allLanguage)
	{
		this.allLanguage = allLanguage;
	}

	public int getLanguageDataId()
	{
		return languageDataId;
	}

	public void setLanguageDataId(int languageDataId)
	{
		this.languageDataId = languageDataId;
	}

}
