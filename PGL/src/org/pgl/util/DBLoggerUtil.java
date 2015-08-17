package org.pgl.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.pgl.Model.DBLogger;
import org.pgl.db.DataBase;

public class DBLoggerUtil
{
	private static class DBLoggerHolder
	{
		private final static DBLoggerUtil INSTANCE = new DBLoggerUtil();
	}

	private DBLoggerUtil()
	{
	}

	public static DBLoggerUtil getInstance()
	{
		return DBLoggerHolder.INSTANCE;
	}
	public void test(){
		System.err.println("test get single");
	}
	
	public void logger(int uid,int type,String oper){
		System.out.println("###------DB Operation Logger begin------###");
		DBLogger loggermodel = new DBLogger(type, uid, oper, new Date());
		boolean _r = saveLogInDB(loggermodel);
		System.out.println("###------DB Operation Logger over------### " + _r );
	}

	private boolean saveLogInDB(DBLogger loggermodel)
	{
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			conn = new DataBase().getConnect();
			String sql = "INSERT INTO dblogger (operationtype,userid,operation,createdatetime) VALUES (?,?,?,now())";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, loggermodel.getOperationtype());
			ps.setInt(2, loggermodel.getUserid());
			ps.setString(3, loggermodel.getOperation());
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}
}
