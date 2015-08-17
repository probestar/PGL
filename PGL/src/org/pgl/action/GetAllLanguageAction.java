package org.pgl.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pgl.Model.LanguageBean;
import org.pgl.service.GetAllLanguageService;
import org.pgl.util.LM_Util;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author mazhanghui
 * 
 *         �ͻ�������������仯��ʱ���������,
 */
public class GetAllLanguageAction extends ActionSupport
{
	private static final long serialVersionUID = 7349864898405295494L;
	private static Log logger = LogFactory.getLog(GetAllLanguageAction.class);

	private int clientTypeCode;
	private String anchorId;
	private String anchorKey;
	private int projectId;
	private int menuId;
	private String fileName;
	private String remark;

	// ��ֹ���Ŀͻ�������ʱ���ê�����ݶ�ʧ
	private int anchor_x;
	private int anchor_y;
	private int anchor_width;
	private int anchor_height;
	private int languageDataId;
	private int id;
	private int copyWriterCount;

	private List<LanguageBean> languageList = new ArrayList<LanguageBean>();
	private GetAllLanguageService getLanguageService = new GetAllLanguageService();

	// Ajax ����
	public String execute() throws Exception
	{
		try
		{
			logger.info("��ȡ���е�����....�ͻ������ʹ��룺" + clientTypeCode);
			languageList.clear();
			LanguageBean bean;
			for (int i = 0; i < LM_Util.languageList.size(); i++)
			{
				bean = new LanguageBean();
				bean.setLanguageName(LM_Util.languageList.get(i).getLanguageName());
				bean.setLanguageCode(LM_Util.languageList.get(i).getLanguageCode());
				languageList.add(bean);
			}

			if (anchorId == null || anchorId.equals(""))
			{
				// �İ�
				languageList = getLanguageService.getAllLanguageData(languageList, clientTypeCode, anchorId, anchorKey, projectId, menuId, true);
			}
			else
			{
				// ê��
				languageList = getLanguageService.getAllLanguageData(languageList, clientTypeCode, anchorId, anchorKey, projectId, menuId, false);
			}
			if (languageList.size() > 0)
			{
				languageDataId = languageList.get(0).getLanguageDataId();
				remark = languageList.get(0).getRemark();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("��ȡ���е�����....�ͻ������ʹ���-�쳣", e);
		}

		return SUCCESS;
	}

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

}
