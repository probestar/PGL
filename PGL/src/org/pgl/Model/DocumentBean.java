package org.pgl.Model;

public class DocumentBean
{
	public int projectId;
	public int menuId;
	public String documentId;
	public String documentKey;

	// getter setter
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

	public String getDocumentId()
	{
		return documentId;
	}

	public void setDocumentId(String documentId)
	{
		this.documentId = documentId;
	}

	public String getDocumentKey()
	{
		return documentKey;
	}

	public void setDocumentKey(String documentKey)
	{
		this.documentKey = documentKey;
	}

}
