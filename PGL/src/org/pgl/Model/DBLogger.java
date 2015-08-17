package org.pgl.Model;

import java.util.Date;

import org.pgl.util.LM_Util.OperationType;

public class DBLogger
{
	private Integer id;
	private Integer operationtype;
	private Integer userid;
	private String operation;
	private Date  createdatetime;
	private String username;
	private String opertype;
	
	
	public DBLogger(){
		
	}
	public DBLogger(Integer operationtype, Integer userid, String operation, Date createdatetime)
	{
		super();
		this.operationtype = operationtype;
		this.userid = userid;
		this.operation = operation;
		this.createdatetime = createdatetime;
	}
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public Integer getOperationtype()
	{
		return operationtype;
	}
	public void setOperationtype(Integer operationtype)
	{
		this.operationtype = operationtype;
	}
	public Integer getUserid()
	{
		return userid;
	}
	public void setUserid(Integer userid)
	{
		this.userid = userid;
	}
	public String getOperation()
	{
		return operation;
	}
	public void setOperation(String operation)
	{
		this.operation = operation;
	}
	public Date getCreatedatetime()
	{
		return createdatetime;
	}
	public void setCreatedatetime(Date createdatetime)
	{
		this.createdatetime = createdatetime;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getOpertype()
	{
		return opertype;
	}
	public void setOpertype(int opertypeid)
	{
		String _r = "";
		for (OperationType optype : OperationType.values())
		{
			if(optype.getValue()==opertypeid)
				_r = optype.name();
		}
		this.opertype = _r;
	}
	
}
