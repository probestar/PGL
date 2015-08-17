package org.pgl.Model;

public class ClientTypeBean
{
	private int id;
	private String clientTypeName;
	private int clientTypeCode;
	public  int mixtrueType;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getClientTypeName()
	{
		return clientTypeName;
	}

	public void setClientTypeName(String clientTypeName)
	{
		this.clientTypeName = clientTypeName;
	}

	public int getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(int clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
	}

	public int getMixtrueType()
	{
		return mixtrueType;
	}

	public void setMixtrueType(int mixtrueType)
	{
		this.mixtrueType = mixtrueType;
	}

}
