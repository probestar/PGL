package org.pgl.Model;

import java.io.Serializable;

public class MenuModel implements Serializable
{
	private static final long serialVersionUID = 1193838290835738318L;
	public long id;
	public long parentid;
	public long projectid;
	public String name;
	public int number;
	public String path;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getParentid()
	{
		return parentid;
	}

	public void setParentid(long parentid)
	{
		this.parentid = parentid;
	}

	public long getProjectid()
	{
		return projectid;
	}

	public void setProjectid(long projectid)
	{
		this.projectid = projectid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	/*
	 * public MenuModel(int nodeId, int parentNodeId, String nodeName, String
	 * nodeTitle) { super(); this.nodeId = nodeId; this.parentNodeId =
	 * parentNodeId; this.nodeName = nodeName; this.nodeTitle = nodeTitle; }
	 */

}
