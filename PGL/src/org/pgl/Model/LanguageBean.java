package org.pgl.Model;

import java.io.Serializable;

public class LanguageBean implements Serializable
{	
	private static final long serialVersionUID = 4404841978745384965L;
	
	private int languageDataId;
	private int projectId;
	private int menuId;
	private String anchorId;
	private String anchorKey;
	private int clientType;
	private int languageCode;
	private String languageName;
	private String languageValue;
	private String remark;
	
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

	public int getClientType()
	{
		return clientType;
	}

	public void setClientType(int clientType)
	{
		this.clientType = clientType;
	}

	public int getLanguageCode()
	{
		return languageCode;
	}

	public void setLanguageCode(int languageCode)
	{
		this.languageCode = languageCode;
	}

	public String getLanguageName()
	{
		return languageName;
	}

	public void setLanguageName(String languageName)
	{
		this.languageName = languageName;
	}

	public String getLanguageValue()
	{
		return languageValue;
	}

	public void setLanguageValue(String languageValue)
	{
		this.languageValue = languageValue;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public int getLanguageDataId()
	{
		return languageDataId;
	}

	public void setLanguageDataId(int languageDataId)
	{
		this.languageDataId = languageDataId;
	}

	//������
	public LanguageBean()
	{
		super();
	}

//	public LanguageBean(int projectId, int menuId, String anchorId, String anchorKey, int clientType, int languageCode, String languageName, String languageValue, String remark)
//	{
//		super();
//		this.projectId = projectId;
//		this.menuId = menuId;
//		this.anchorId = anchorId;
//		this.anchorKey = anchorKey;
//		this.clientType = clientType;
//		this.languageCode = languageCode;
//		this.languageName = languageName;
//		this.languageValue = languageValue;
//		this.remark = remark;
//	}
	
}
