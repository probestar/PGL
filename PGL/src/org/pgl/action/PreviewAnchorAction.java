package org.pgl.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.AnchorBean;
import org.pgl.service.PreviewAnchorService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ê���Ԥ��
 * 
 * @author mazhanghui
 * 
 */
public class PreviewAnchorAction extends ActionSupport
{
	private static final long serialVersionUID = 8687350091424840407L;
	private static Log logger = LogFactory.getLog(PreviewAnchorAction.class);

	private int projectId;
	private int menuId;
	private int languageCode;
	private int clientTypeCode;
	private String anchorId;
	private String anchorKey;
	private String fileName;

	private PreviewAnchorService previewService = new PreviewAnchorService();
	private List<AnchorBean> anchorList = new ArrayList<AnchorBean>();
	// private List<LanguageBean> languageList = new ArrayList<LanguageBean>();
	private HttpServletRequest request;

	public String execute() throws Exception
	{
		if (fileName.equals(""))
			fileName = null;

		request = ServletActionContext.getRequest();
		try
		{
			anchorList = previewService.getAnchorInfo(projectId, menuId, languageCode, clientTypeCode);
			request.setAttribute("anchorList", anchorList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Ԥ��ê���쳣!", e);
			return SUCCESS;
		}

		return SUCCESS;
	}

	// getter setter
	public int getLanguageCode()
	{
		return languageCode;
	}

	public void setLanguageCode(int languageCode)
	{
		this.languageCode = languageCode;
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

	public int getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(int clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
	}

	public int getMenuId()
	{
		return menuId;
	}

	public void setMenuId(int menuId)
	{
		this.menuId = menuId;
	}

	public List<AnchorBean> getAnchorList()
	{
		return anchorList;
	}

	public void setAnchorList(List<AnchorBean> anchorList)
	{
		this.anchorList = anchorList;
	}

	public int getProjectId()
	{
		return projectId;
	}

	public void setProjectId(int projectId)
	{
		this.projectId = projectId;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	// public List<LanguageBean> getLanguageList()
	// {
	// return languageList;
	// }
	//
	// public void setLanguageList(List<LanguageBean> languageList)
	// {
	// this.languageList = languageList;
	// }

}
