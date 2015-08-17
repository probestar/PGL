package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.pgl.db.DataBase;

/**
 * @author mazhanghui
 */
public class RemoveAnchorService
{

	/*
	 * ��������ɾ��ê�������
	 */
	public void removeAnchor(int anchorPrimeryKey)
	{
		Connection conn = null;
		PreparedStatement pres = null;

		try
		{
			String deleteSql = "DELETE FROM anchor WHERE id=?";
			conn = new DataBase().getConnect();
			pres = conn.prepareStatement(deleteSql, Statement.RETURN_GENERATED_KEYS);
			pres.setInt(1, anchorPrimeryKey);
			pres.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (pres != null)
					pres.close();
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
	 * ɾ�����Ա��Ӧ������
	 */
	public void removeLanguageData(int projectId, int menuId, String anchorId)
	{
		String sql = "DELETE FROM languageData WHERE anchor_id='" + anchorId + "' AND menu_id=" + menuId + " AND project_id=" + projectId;

		new DataBase().executeUpdate(sql);
	}

	/*
	 * ��ȡê���Ӧ������
	 */
	public int getAnchorPrimeryKey(int projectId, int menuId, String anchorId)
	{
		int id = 0;
		Connection conn = null;
		PreparedStatement pres = null;
		ResultSet rs = null;
		try
		{
			String deleteSql = "SELECT id FROM anchor WHERE anchor_id=? AND menu_id=? AND project_id=?";
			conn = new DataBase().getConnect();
			pres = conn.prepareStatement(deleteSql, Statement.RETURN_GENERATED_KEYS);
			pres.setString(1, anchorId);
			pres.setInt(2, menuId);
			pres.setInt(3, projectId);
			rs = pres.executeQuery();
			while (rs != null && rs.next())
			{
				id = rs.getInt(1);
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
				if (pres != null)
					pres.close();
				if (conn != null)
					conn.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return id;
	}

	/*
	 * ��������ɾ��ӳ���
	 */
	public void removeMappingTableData(int anchorPrimeryKey)
	{

		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "DELETE FROM anchor_languageData WHERE anchor_id=" + anchorPrimeryKey;

		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	}
}
