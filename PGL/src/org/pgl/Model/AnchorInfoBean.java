package org.pgl.Model;

import java.util.ArrayList;
import java.util.List;

public class AnchorInfoBean
{
	public String anchorKey;
	public int languageDataId;
	public int clientTypeCode;
	public String remark;
	private String anchorId;
	public List<LanguageBean> languageList = new ArrayList<LanguageBean>();
	
	
	// getter setter
	public String getAnchorKey()
	{
		return anchorKey;
	}

	public void setAnchorKey(String anchorKey)
	{
		this.anchorKey = anchorKey;
	}

	public int getLanguageDataId()
	{
		return languageDataId;
	}

	public void setLanguageDataId(int languageDataId)
	{
		this.languageDataId = languageDataId;
	}

	public int getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(int clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
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

	public String getAnchorId()
	{
		return anchorId;
	}

	public void setAnchorId(String anchorId)
	{
		this.anchorId = anchorId;
	}

}
