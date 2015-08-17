package org.pgl.Model;

import java.util.List;

public class GlobalKeyBean
{
	private int languageDataId;
	private String anchorKey;
	private String remark;
	private int clientCode;
	private List<LanguageBean> languageList;

	// GETTER SETTER
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

	public int getClientCode()
	{
		return clientCode;
	}

	public void setClientCode(int clientCode)
	{
		this.clientCode = clientCode;
	}

	public List<LanguageBean> getLanguageList()
	{
		return languageList;
	}

	public void setLanguageList(List<LanguageBean> languageList)
	{
		this.languageList = languageList;
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
