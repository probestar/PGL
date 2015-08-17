package org.pgl.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.LanguageBean;
import org.pgl.Model.User;
import org.pgl.service.CheckAnchorKeyService;
import org.pgl.service.CopyWriterService;
import org.pgl.util.DBLoggerUtil;
import org.pgl.util.LM_Util;
import org.pgl.util.LM_Util.OperationType;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ����ê�㼰��ê���Ӧ����Ϣ�������İ���Ϣ
 * 
 * @author mazhanghui
 * 
 */
public class SaveAnchorAction extends ActionSupport
{
	private static final long serialVersionUID = 2595002813505793695L;
	private static Log logger = LogFactory.getLog(SaveAnchorAction.class);
	private static DBLoggerUtil dblog = DBLoggerUtil.getInstance();
	private int projectId;
	private int menuId;
	private String anchorId;
	private String anchorKey;
	private int clientTypeCode;
	private int anchor_x;
	private int anchor_y;
	private int anchor_width;// ê����
	private int anchor_height;// ê��߶�
	private String fileName;
	private String remark; // ��ע
	private int id; // �İ�ID
	private String message;
	private int copyWriterCount;
	private int languageDataId;

	private List<LanguageBean> languageList = new ArrayList<LanguageBean>();
	private CopyWriterService copyService = new CopyWriterService();
	private CheckAnchorKeyService checkService = new CheckAnchorKeyService();

	boolean anchorFlag = false;

	public String execute() throws Exception
	{
		for (int i = 0; i < languageList.size(); i++)
		{
			languageList.get(i).setLanguageCode(LM_Util.languageList.get(i).getLanguageCode());
			languageList.get(i).setLanguageName(LM_Util.languageList.get(i).getLanguageName());
		}

		if (id != 0)
		{
			return updateWriter();
		}
		else
		{
			System.out.println("--->>Session is valid,Please Logon!");
			message = "Session is valid,Please Logon!";
			return ERROR;
		}
	}


	/*
	 * �����İ�
	 */
	private String updateWriter()
	{
		try
		{
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
			if (user == null)
				return Action.LOGIN;
			boolean keyFlag = checkService.checkKeyBeforeSave(id, projectId, clientTypeCode, anchorKey);
			if (keyFlag)
			{
				message = getText("keyexists");//" KEY�ظ�������������...";
				return ERROR;
			}

			int globalType = 0;// �Ƿ�ΪGlobalKey
			if (anchorKey != null && anchorKey.toLowerCase().startsWith("general_"))
			{
				clientTypeCode = 0;
				globalType = 1;
			}
			boolean flag = copyService.updateWriter(id, anchorKey, languageList, clientTypeCode, remark, globalType);

			if (flag)
			{
				for (LanguageBean langbean : languageList)
				{
						dblog.logger((int) user.getId(), OperationType.UPDATE.getValue(), String.format("User %s update copyWriter ,id:%s, anchorKey:%s, clientTypeCode:%s, remark:%s, globalType:%s, langValue:%s, langCode:%s", user.getName(),id, anchorKey, clientTypeCode, remark, globalType,langbean.getLanguageValue(), langbean.getLanguageCode()));				
				}
				message = getText("copywriteupdatesuc");
				return SUCCESS;
			}
		}
		catch (Exception e)
		{
			logger.info(String.format("�����İ��쳣���İ�ID:%s projectId:%s", id, projectId), e);
			e.printStackTrace();
		}
		message =  getText("copywriteupdatefail");
		return ERROR;
	}

	// getter setter
	public int getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(int clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
	}

	public String getAnchorId()
	{
		return anchorId;
	}

	public void setAnchorId(String anchorId)
	{
		this.anchorId = anchorId;
	}

	public int getAnchor_x()
	{
		return anchor_x;
	}

	public void setAnchor_x(int anchor_x)
	{
		this.anchor_x = anchor_x;
	}

	public int getAnchor_y()
	{
		return anchor_y;
	}

	public void setAnchor_y(int anchor_y)
	{
		this.anchor_y = anchor_y;
	}

	public String getAnchorKey()
	{
		return anchorKey;
	}

	public void setAnchorKey(String anchorKey)
	{
		this.anchorKey = anchorKey;
	}

	public List<LanguageBean> getLanguageList()
	{
		return languageList;
	}

	public void setLanguageList(List<LanguageBean> languageList)
	{
		this.languageList = languageList;
	}

	public int getProjectId()
	{
		return projectId;
	}

	public void setProjectId(int projectId)
	{
		this.projectId = projectId;
	}

	public int getMenuId()
	{
		return menuId;
	}

	public void setMenuId(int menuId)
	{
		this.menuId = menuId;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getCopyWriterCount()
	{
		return copyWriterCount;
	}

	public void setCopyWriterCount(int copyWriterCount)
	{
		this.copyWriterCount = copyWriterCount;
	}

	public int getAnchor_width()
	{
		return anchor_width;
	}

	public void setAnchor_width(int anchor_width)
	{
		this.anchor_width = anchor_width;
	}

	public int getAnchor_height()
	{
		return anchor_height;
	}

	public void setAnchor_height(int anchor_height)
	{
		this.anchor_height = anchor_height;
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
