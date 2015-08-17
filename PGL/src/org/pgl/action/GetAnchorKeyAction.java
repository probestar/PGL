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
 *         ˫��ê���ʱ��������࣬������ê��ID�����꣬����ê��Id���س���ê���Ӧ��KEY������Ϊȫ���Ķ�������Ϣ
 */
public class GetAnchorKeyAction extends ActionSupport
{
	private static final long serialVersionUID = 4049601197213586432L;
	private static Log logger = LogFactory.getLog(GetAnchorKeyAction.class);

	private int projectId;
	private int menuId;
	private String anchorKey;
	private String anchorId;
	private int anchor_x;// ê���ͼƬ����λ��
	private int anchor_y;// ê���ͼƬ�Ϸ���λ��
	private int anchor_width;// ê����
	private int anchor_height;// ê��߶�
	private int clientTypeCode;// �ͻ������ͻ���
	private String fileName;
	private String remark;
	private int languageDataId;
	private int copyWriterCount;

	// �����б�--���Է��ڳ���������������͵�ʱ�����һ��
	private List<LanguageBean> languageList = new ArrayList<LanguageBean>();
	private GetAllLanguageService languageService = new GetAllLanguageService();

	public String execute() throws Exception
	{
		try
		{
			if (anchorId == null || anchorId.equals("null"))
				return SUCCESS;

			// ��α��ê��������ӵ�
			anchorKey = languageService.getAnchorKey(anchorId);

			// language���ȡ���е��������ƺ�code
			languageList.clear();
			LanguageBean bean;
			for (int i = 0; i < LM_Util.languageList.size(); i++)
			{
				bean = new LanguageBean();
				bean.setLanguageName(LM_Util.languageList.get(i).getLanguageName());
				bean.setLanguageCode(LM_Util.languageList.get(i).getLanguageCode());
				languageList.add(bean);
			}

			languageList = languageService.getAllLanguageData(languageList, clientTypeCode, anchorId, anchorKey, projectId, menuId, false);
			if (languageList.size() > 0)
			{
				languageDataId = languageList.get(0).getLanguageDataId();
				remark = languageList.get(0).getRemark();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("˫��ê���ȡKEY�쳣:" + anchorId, e);
		}

		return SUCCESS;
	}

	// getter setter
	public String getAnchorKey()
	{
		return anchorKey;
	}

	public void setAnchorKey(String anchorKey)
	{
		this.anchorKey = anchorKey;
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

	public int getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(int clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
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
