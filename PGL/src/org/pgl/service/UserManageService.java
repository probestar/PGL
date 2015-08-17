package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.pgl.Model.User;
import org.pgl.db.DataBase;
import org.pgl.util.EncryptUtil;

public class UserManageService 
{
    public List<User> getAllUser()
    {
    	List<User> lstUser = new ArrayList<User>();
    	
    	User objUser = null;
    	String sql = "SELECT id,name,password,role,deltab_role FROM user";
    	PreparedStatement ps = null;
    	Connection conn = null;
    	try 
    	{
    		conn =  new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs != null && rs.next())
			{
				objUser = new User();
				objUser.setId(rs.getLong("id"));
				objUser.setName(rs.getString("name"));
//				objUser.setNickname(rs.getString("nickname"));
				objUser.setPassword(EncryptUtil.getDecString(rs.getString("password")));
				objUser.setRole(rs.getLong("role"));
				objUser.setDelTabRole(rs.getLong("deltab_role"));
				lstUser.add(objUser);
			}
		}
    	catch (Exception e) 
		{
			e.printStackTrace();
		}
    	finally
    	{
    		closeStmt(ps);
    		closeConn(conn);
    	}
    	
    	return lstUser;
    }
    
    public boolean checkUserName(String name)
    {
    	boolean bIsSuc = false;
    	String sql = "SELECT COUNT(*) FROM user WHERE name=?";
    	PreparedStatement ps = null;
    	Connection conn = null;
    	try 
    	{
    		conn =  new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				if(rs.getInt(1)>0)
				{
					return true;
				}
				return false;
			}
		}
    	catch (Exception e) 
		{
			e.printStackTrace();
			bIsSuc = false;
		}
    	finally
    	{
    		closeStmt(ps);
    		closeConn(conn);
    	}
    	
    	return bIsSuc;
    }
    
    
    public boolean insertUser(String name, int delTabRole, String password, int role)
    {
    	boolean bIsSuc = true;
    	String sql = "INSERT INTO user(name,deltab_role,password,role)VALUES(?,?,?,?)";
    	PreparedStatement ps = null;
    	Connection conn = null;
    	try 
    	{
    		conn =  new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, delTabRole);
			ps.setString(3, password);
			ps.setInt(4, role);
			ps.executeUpdate();
		}
    	catch (Exception e) 
		{
			e.printStackTrace();
			bIsSuc = false;
		}
    	finally
    	{
    		closeStmt(ps);
    		closeConn(conn);
    	}
    	
    	return bIsSuc;
    }
    
    public boolean deleteUser(long id)
    {
    	boolean bIsSuc = true;
    	
    	String sql = "DELETE FROM user WHERE id = ?";
    	PreparedStatement ps = null;
    	Connection conn = null;
    	try 
    	{
    		conn =  new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			ps.executeUpdate();
		}
    	catch (Exception e) 
		{
			e.printStackTrace();
			bIsSuc = false;
		}
    	finally
    	{
    		closeStmt(ps);
    		closeConn(conn);
    	}
    	
    	return bIsSuc;
    }
    
    public boolean updateUser(long id, String name, int delTabRole, String password, int role)
    {
    	boolean bIsSuc = true;
    	String sql = "UPDATE user SET name=?,deltab_role=?,password=?,role=? WHERE id=?";
    	PreparedStatement ps = null;
    	Connection conn = null;
    	try 
    	{
    		conn =  new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, delTabRole);
			ps.setString(3, password);
			ps.setInt(4, role);
			ps.setLong(5, id);
			ps.executeUpdate();
		}
    	catch (Exception e) 
		{
			e.printStackTrace();
			bIsSuc = false;
		}
    	finally
    	{
    		closeStmt(ps);
    		closeConn(conn);
    	}
    	
    	return bIsSuc;
    }
    
    private void closeConn(Connection conn)
	{
		try
		{
			if (conn != null)
				conn.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void closeStmt(PreparedStatement stmt)
	{
		try
		{
			if (stmt != null)
				stmt.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
