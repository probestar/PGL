package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.pgl.db.DataBase;

public class CheckAnchorKeyService
{
	/**
	 * ����Ƿ����ظ���KEY; ��鷶Χ: ê����İ�
	 * 
	 * ͬһ���ͻ����µ�KEY�����ظ�
	 * 
	 * ��һ��KEY�ڿͻ�������Ϊȫ����clientCode�����������KEY�������ͻ����²���������
	 * 
	 * @param projectId
	 * @param menuId
	 * @param anchorKey
	 * @return true:�� false:��
	 */
	public boolean checkDuplicateKey(int projectId, int clientTypeCode, String anchorKey)
	{
		String sql = "SELECT COUNT(*) FROM languageData WHERE anchor_key=? AND client_code=? AND project_id=?";

		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			prst = conn.prepareStatement(sql);
			prst.setString(1, anchorKey);
			prst.setInt(2, clientTypeCode);
			prst.setInt(3, projectId);
			rs = prst.executeQuery();

			if (rs != null && rs.next())
			{
				if (rs.getInt(1) > 0)
				{
					return true;
				}
			}
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
				if (rs != null)
					rs.close();
				if (prst != null)
					prst.close();
//				if (conn != null)
//					conn.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * ��������ǰ��KEY���ظ���У��
	 * 
	 * @param projectId
	 * @param clientTypeCode
	 *            0:��ʾ����ȫ���ͻ���;ͬһ��KEY�ڿͻ�������0���������������ͻ����²��ܱ�������
	 *            ������ڿͻ�������Ϊ0(ȫ��)�±�������Ҫ��ȷ�������κοͻ����¾��޸�KEY������
	 * @param anchorKey
	 * @return
	 */
	public boolean checkKeyBeforeSave(int languageDataId, int projectId, int clientTypeCode, String anchorKey)
	{
		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM languageData WHERE anchor_key=? ");

		if (anchorKey != null && !anchorKey.equals("") && !anchorKey.toLowerCase().startsWith("general_"))
		{
			sql.append(" AND project_id=? ");
		}

		if (clientTypeCode != 0)
		{
			sql.append(" AND client_code IN(0,?)");
		}

		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			prst = conn.prepareStatement(sql.toString());
			prst.setString(1, anchorKey);

			// 'general_'�����ظ���֤����projectId��������
			if (anchorKey != null && !anchorKey.equals("") && !anchorKey.toLowerCase().startsWith("general_"))
			{
				prst.setInt(2, projectId);
			}

			if (clientTypeCode != 0)
				prst.setInt(3, clientTypeCode);

			rs = prst.executeQuery();

			if (rs != null && rs.next())
			{
				// insert
				if (languageDataId == 0)
				{
					if (rs.getInt(1) > 0)
					{
						return true;
					}
				}
				// update
				else
				{
					if (rs.getInt(1) > 1)
					{
						return true;
					}
				}
			}
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
				if (rs != null)
					rs.close();
				if (prst != null)
					prst.close();
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
