package org.pgl.Model;

import java.util.ArrayList;
import java.util.List;

public class CheckModel
{
	public long id;
	public long projectid;
	public long menuid;
	public String menuname;
	public int number;
	private long menuParentId;
	public String anchor_id;
	public String anchor_key;
	public int clientcode;
	public String clientName;
	private String path;// �㼶·��
	public List<AllLanguage> lstLanguage = new ArrayList<AllLanguage>();

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public int getClientcode()
	{
		return clientcode;
	}

	public void setClientcode(int clientcode)
	{
		this.clientcode = clientcode;
	}

	public String getClientName()
	{
		return clientName;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public long getProjectid()
	{
		return projectid;
	}

	public void setProjectid(long projectid)
	{
		this.projectid = projectid;
	}

	public String getMenuname()
	{
		return menuname;
	}

	public void setMenuname(String menuname)
	{
		this.menuname = menuname;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public String getAnchor_key()
	{
		return anchor_key;
	}

	public void setAnchor_key(String anchor_key)
	{
		this.anchor_key = anchor_key;
	}

	public List<AllLanguage> getLstLanguage()
	{
		return lstLanguage;
	}

	public void setLstLanguage(List<AllLanguage> lstLanguage)
	{
		this.lstLanguage = lstLanguage;
	}

	public String getAnchor_id()
	{
		return anchor_id;
	}

	public void setAnchor_id(String anchor_id)
	{
		this.anchor_id = anchor_id;
	}

	public long getMenuid()
	{
		return menuid;
	}

	public void setMenuid(long menuid)
	{
		this.menuid = menuid;
	}

	public long getMenuParentId()
	{
		return menuParentId;
	}

	public void setMenuParentId(long menuParentId)
	{
		this.menuParentId = menuParentId;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

}
