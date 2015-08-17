package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pgl.Model.EmailBean;
import org.pgl.db.DataBase;

public class ManageEmailService
{
	// get all email
	public List<EmailBean> getAllEmail() throws Exception
	{
		List<EmailBean> list = new ArrayList<EmailBean>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();

			ps = conn.prepareStatement("SELECT id,email,state FROM email");
			rs = ps.executeQuery();
			EmailBean bean = null;
			while (rs != null && rs.next())
			{
				bean = new EmailBean();
				bean.setId(rs.getInt(1));
				bean.setEmail(rs.getString(2));
				bean.setState(rs.getInt(3));

				list.add(bean);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			}
			catch (SQLException e)
			{
				throw e;
			}
		}
		return list;
	}

	public void deleteEmail(int id) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement("DELETE FROM email where id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
	}

	public void addEmail(String email, int state) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement("INSERT INTO email (email,state) VALUES(?,?)");
			ps.setString(1, email);
			ps.setInt(2, state);
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
	}
}
