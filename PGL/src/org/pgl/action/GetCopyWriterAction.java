package org.pgl.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pgl.Model.LanguageBean;
import org.pgl.service.CopyWriterService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ��ȡ�İ���Ϣ
 * 
 * @author mazhanghui
 * 
 */
public class GetCopyWriterAction extends ActionSupport
{
	private static final long serialVersionUID = -5316124087889407489L;
	private static Log logger = LogFactory.getLog(GetCopyWriterAction.class);

	private int projectId;
	private int menuId;
	private int clientTypeCode;
	private int id;
	private String anchorKey;
	private String remark;
	private int copyWriterCount; // ͨ���İ�������̬����ҳ��߶�
	private List<LanguageBean> languageList = new ArrayList<LanguageBean>();
	private CopyWriterService service = new CopyWriterService();

	public String execute() throws Exception
	{
		try
		{
			logger.info("��ȡ�İ���Ϣ...ID=" + id);
			
			languageList = service.getCopyWriterLanguage(id);
			if (languageList != null && languageList.size() > 0)
			{
				menuId = languageList.get(0).getMenuId();
				anchorKey = languageList.get(0).getAnchorKey();
				remark = languageList.get(0).getRemark();
				clientTypeCode = languageList.get(0).getClientType();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.info("��ȡ�İ���Ϣ...�쳣", e);
		}

		return SUCCESS;
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

	public List<LanguageBean> getLanguageList()
	{
		return languageList;
	}

	public void setLanguageList(List<LanguageBean> languageList)
	{
		this.languageList = languageList;
	}

	public int getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(int clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
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
