package org.pgl.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase 
{
	 //private static String driverClass = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	 //private static String jdbcUrl = "jdbc:microsoft:sqlserver://127.0.0.1:1433;databaseName=tast;characterEncoding=utf-8";
	 public static String driverClass = "com.mysql.jdbc.Driver";
//	 public static String jdbcUrl = "jdbc:mysql://10.10.209.31:3306/language_manage?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
//	 public static String username = "centosuser";
//	 public static String password = "user2012";
	 public static Connection conn = null;
	 public PreparedStatement stmt = null;
	 public ResultSet rs = null;
	 C3p0ConnectionPool c3p0pool = C3p0ConnectionPool.getInstance();
	 public DataBase() 
	 {
		 boolean flag =true;
		 try 
		 {
			 if(conn != null)
			 {
				 flag = conn.isClosed();
			 }
		 } 
		 catch (SQLException e1) 
		 {
				e1.printStackTrace();
		 }

		 if(flag)
		 {
			 try
			 {
				 Class.forName(driverClass);
//				 conn = DriverManager.getConnection(jdbcUrl,username,password);
				 conn = c3p0pool.getConnection();
			 }
			 catch (ClassNotFoundException e)
			 {
				 e.printStackTrace();
			 } 
//			 catch(SQLException e) 
//			 {
//				 e.printStackTrace();
//			 }
		 }
	 }
	 
	 public Connection getConnect() throws Exception
	 {
		 if (conn == null || conn.isClosed())
		 {
//			 conn = DriverManager.getConnection(jdbcUrl,username,password);
			 conn = c3p0pool.getConnection();
		 }
		 return conn;
	 }

	 public ResultSet executeQuery(String sql) 
	 {
		 try
		 {
			 stmt = getConnect().prepareStatement(sql);//.createStatement();
			 rs = stmt.executeQuery(sql);
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		 return rs;
	 }

	 /**
	  * @param sql
	  * @return  -1:error 
	  */
	 public int executeUpdate(String sql) 
	 {
	      try
		  {
			   stmt = getConnect().prepareStatement(sql);//.createStatement();
			   return stmt.executeUpdate(sql);
		  }
		  catch (Exception e)
		  {
			   e.printStackTrace();
			   return -1;
		   }
	 }

	 public void rsClose() 
	 {
		 try
		 {
			 if(rs != null) 
			 {
				 rs.close();
			 }
		 }
		 catch (SQLException e)
		 {
			 e.printStackTrace();
		 }
	 }

	public void stmtClose() 
	{
		try
		{
			if(stmt != null) 
			{
				stmt.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public void connClose() 
	{
		try
		{
			if(conn != null) 
			{
				conn.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	/*public static void main(String[] args)
	{
		 try {
			Class.forName(driverClass);
			 conn = DriverManager.getConnection(jdbcUrl,username,password);
			 if (conn != null)
			 {
				 System.out.println("success!!!");
			 }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
