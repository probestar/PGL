package org.pgl.Model;

public class User
{
	public long id;
	public String name;
	public String password;
	public long role;
	public long delTabRole;
	public String operforlang;
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public long getRole()
	{
		return role;
	}

	public void setRole(long role)
	{
		this.role = role;
	}

	public long getDelTabRole()
	{
		return delTabRole;
	}

	public void setDelTabRole(long delTabRole)
	{
		this.delTabRole = delTabRole;
	}

	public String getOperforlang()
	{
		return operforlang;
	}

	public void setOperforlang(String operforlang)
	{
		this.operforlang = operforlang;
	}

}
