package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.pgl.db.DataBase;

public class SaveAnchorService
{

	public boolean saveAnchor(int existId, int projectId, int menuId, String anchorId, String anchorKey, int anchor_x, int anchor_y, int anchor_width, int anchor_height)
	{
		String insertSql = "INSERT INTO anchor (project_id, menu_id, anchor_id, anchor_key, anchor_x, anchor_y, anchor_width, anchor_height) " + " values (" + projectId + ", " + menuId + ",'" + anchorId + "','" + anchorKey + "'," + anchor_x + "," + anchor_y + "," + anchor_width + "," + anchor_height + ")";

		String updateSql = "UPDATE anchor SET anchor_key='" + anchorKey + "',anchor_x=" + anchor_x + ",anchor_y=" + anchor_y + ",anchor_width=" + anchor_width + ",anchor_height=" + anchor_height + " WHERE id=" + existId;
		String _sql = "";
		if (anchor_x == 0 && anchor_y == 0)
		{
			// ������Ϊû��˫��ê����޸�ê����Ϣ����ê��������ʼ��Ϊ0
			updateSql = "UPDATE anchor SET anchor_key='" + anchorKey + "' WHERE id=" + existId;
		}
		Connection conn = null;
		PreparedStatement ps = null;
		// DataBase db = new DataBase();
		int i = -1;

		try
		{

			conn = new DataBase().getConnect();
			if (existId != 0)
			{
				System.out.println(updateSql);
				_sql = updateSql;
				// i = db.executeUpdate(updateSql);
			}
			else
			{
				System.out.println(insertSql);
				_sql = insertSql;
				// i = db.executeUpdate(insertSql);
			}
			ps = conn.prepareStatement(_sql);
			i = ps.executeUpdate();
			if (i != -1)
				return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (ps != null)
				{
					ps.close();
				}
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		return false;
	}

	/**
	 * 
	 * @param projectId
	 * @param menuId
	 * @param anchorId
	 * @return 0:��������� ��0:���ݸ÷���ִֵ�и��²���
	 */
	public int isExist(int projectId, int menuId, String anchorId)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement("SELECT id FROM anchor WHERE project_id=" + projectId + " AND menu_id=" + menuId + " AND anchor_id='" + anchorId + "'");
			rs = ps.executeQuery();// new
									// DataBase().executeQuery("SELECT id FROM anchor WHERE project_id="
									// + projectId + " AND menu_id=" + menuId +
									// " AND anchor_id='" + anchorId + "'");
			if (rs != null && rs.next())
			{
				return rs.getInt(1);
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
				{
					ps.close();
				}
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return 0;
	}

}
