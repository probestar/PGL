package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.pgl.Model.DBLogger;
import org.pgl.db.DataBase;
import org.pgl.util.LM_Util;

public class OperationService
{
	public int getLogCount(int userid,int opertype, String searchkey,String operdate){
		int count = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			StringBuffer sql = new StringBuffer("SELECT COUNT(id) FROM dblogger ");
			if (userid!=0 || opertype!=0 || (searchkey != null && searchkey.length()>0) ||(operdate!=null && operdate.length()>0) )
			{
				sql.append(" where 1=1 ");
				if(userid!=0)
					sql.append(" and userid="+userid);
				if(opertype!=0)
					sql.append(" and operationtype="+opertype);
				if(searchkey != null && searchkey!="")
					sql.append(" and operation like '%"+searchkey+"%'");
				if(operdate!=null && operdate.length()>0){
					String _btime = operdate +" 00:00:00";
					String _etime = operdate +" 23:59:59";
					sql.append(" and createdatetime between '").append(_btime).append("' and '").append(_etime).append("' ");
				}
			}
			ps = conn.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			rs = ps.executeQuery(sql.toString());

			while (rs != null && rs.next())
			{
				count = rs.getInt(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeResultSet(rs);
			closeStmt(ps);
			closeConn(conn);
		}
		
		return count;
	}
	
	public List<DBLogger> getDbLogger(int userid,int opertype, String searchkey,String operdate, int startCount){
		List<DBLogger> _list = new ArrayList<DBLogger>();
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			StringBuffer sql = new StringBuffer("SELECT d.*,u.id,u.name FROM dblogger as d, user as u where d.userid=u.id");
			if (userid!=0 || opertype!=0 || (searchkey != null && searchkey!="") || (operdate!=null && operdate.length()>0) )
			{
				if(userid!=0)
					sql.append(" and d.userid="+userid);
				if(opertype!=0)
					sql.append(" and d.operationtype="+opertype);
				if(searchkey != null && searchkey!="")
					sql.append(" and d.operation like '%"+searchkey+"%'");
				if(operdate!=null && operdate.length()>0){
					String _btime = operdate +" 00:00:00";
					String _etime = operdate +" 23:59:59";
					sql.append(" and d.createdatetime between '").append(_btime).append("' and '").append(_etime).append("' ");
				}
			}
			sql.append(" ORDER BY d.createdatetime desc LIMIT ").append(startCount).append(",").append(LM_Util.SHOW_COUNT);
			ps = conn.prepareStatement(sql.toString());
			System.out.println(sql.toString());
			rs = ps.executeQuery(sql.toString());

			while (rs != null && rs.next())
			{
				DBLogger logger = new DBLogger();
				logger.setId(rs.getInt(1));
				logger.setOperationtype(rs.getInt(2));
				logger.setUserid(rs.getInt(3));
				logger.setOperation(rs.getString(4));
				logger.setCreatedatetime(new Date(rs.getTimestamp(5).getTime()));
				logger.setUsername(rs.getString(7));
				logger.setOpertype(rs.getInt(2));
				_list.add(logger);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeResultSet(rs);
			closeStmt(ps);
			closeConn(conn);
		}
		return _list;
	}
	
	private void closeResultSet(ResultSet rs)
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
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
