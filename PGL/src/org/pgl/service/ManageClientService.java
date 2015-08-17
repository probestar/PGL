package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pgl.Model.ClientTypeBean;
import org.pgl.db.DataBase;

public class ManageClientService
{

	/*
	 * ��ȡ���еĿͻ�����Ϣ�б�
	 */
	public List<ClientTypeBean> getAllClient()
	{
		List<ClientTypeBean> list = new ArrayList<ClientTypeBean>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();

			ps = conn.prepareStatement("SELECT id,client_type,client_code,mixtrue_type FROM clienttype");
			rs = ps.executeQuery();
			ClientTypeBean bean = null;
			while (rs != null && rs.next())
			{
				bean = new ClientTypeBean();
				bean.setId(rs.getInt(1));
				bean.setClientTypeName(rs.getString(2));
				bean.setClientTypeCode(rs.getInt(3));
				bean.setMixtrueType(rs.getInt(4));

				list.add(bean);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}

		return list;
	}

	/*
	 * ��ȡ�ͻ�����Ϣ�б�--�����Ƿ�Ϊ�������
	 * 
	 * 0���ǻ�� 1��������� ��A&B
	 */
	public List<ClientTypeBean> getClientByType(int type)
	{
		List<ClientTypeBean> list = new ArrayList<ClientTypeBean>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();

			ps = conn.prepareStatement("SELECT id,client_type,client_code FROM clienttype WHERE mixtrue_type=" + type);
			rs = ps.executeQuery();
			ClientTypeBean bean = null;
			while (rs != null && rs.next())
			{
				bean = new ClientTypeBean();
				bean.setId(rs.getInt(1));
				bean.setClientTypeName(rs.getString(2));
				bean.setClientTypeCode(rs.getInt(3));

				list.add(bean);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}

		return list;
	}

	/*
	 * ��ӿͻ���
	 */
	public void saveClient(String newName, int mixtrueType)
	{
		String sql = "INSERT INTO clienttype(client_type,client_code,mixtrue_type) VALUES (?,?,?)";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int maxCode = -1;
		try
		{
			rs = new DataBase().executeQuery("SELECT MAX(client_code)+1 FROM clienttype");
			while (rs != null && rs.next())
			{
				maxCode = rs.getInt(1);
			}
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, newName);
			ps.setInt(2, maxCode);
			ps.setInt(3, mixtrueType);
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
	}

	/*
	 * ֻ�ܸ�������
	 */
	public boolean updateClient(int id, String newName)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement("UPDATE clienttype SET client_type=? WHERE id=?");
			ps.setString(1, newName);
			ps.setInt(2, id);
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

	/*
	 * ɾ���ͻ���
	 */
	public void deleteClient(int id, int code)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement("DELETE FROM clienttype WHERE id=" + id);
			ps.executeUpdate();
			ps1 =  conn.prepareStatement("DELETE FROM languageData WHERE client_code=" + code);
			ps1.executeUpdate();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try
			{
				if (ps != null)
					ps.close();
				if (ps1 != null)
					ps1.close();
				if (conn != null)
					conn.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
//		new DataBase().executeUpdate("DELETE FROM clienttype WHERE id=" + id);
//		new DataBase().executeUpdate("DELETE FROM languageData WHERE client_code=" + code);
	}

	/**
	 * У��ͻ��������Ƿ��ظ�
	 * 
	 * @return true:�ظ�; false:���ظ�
	 */
	public boolean checkClientName(String clientName)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn =  new DataBase().getConnect();
			ps = conn.prepareStatement("SELECT COUNT(*) FROM clienttype WHERE client_type='" + clientName + "'");
			rs = ps.executeQuery();//new DataBase().executeQuery("SELECT COUNT(*) FROM clienttype WHERE client_type='" + clientName + "'");
			while (rs != null && rs.next())
			{
				if (rs.getInt(1) > 0)
					return true;
				return false;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		finally{
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
		return false;
	}

}
