package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.pgl.Model.LanguageBean;
import org.pgl.db.DataBase;
import org.pgl.util.LM_Util;

/**
 * PreparedStatement ��SQLע��
 * 
 * @author mazhanghui
 * 
 */
public class SaveLanguageService
{
	public int saveLanguage(int existId, int projectId, int menuId, String anchorId, String anchorKey, List<LanguageBean> languageList, int clientTypeCode, String remark)
	{

		StringBuffer insertSql = new StringBuffer("INSERT INTO languageData (project_id, menu_id, anchor_id, anchor_key, client_code, remark");
		StringBuffer updateSql = new StringBuffer("UPDATE languageData SET anchor_key=?,remark=?");

		int lan_size = languageList.size();
		for (int i = 0; i < lan_size; i++)
		{
			insertSql.append(",language_" + LM_Util.languageList.get(i).getLanguageCode());

			updateSql.append(",language_" + LM_Util.languageList.get(i).getLanguageCode() + "=?");
		}

		insertSql.append(") values (?,?,?,?,?,?");

		for (int i = 0; i < lan_size; i++)
		{
			insertSql.append(",?");
		}
		insertSql.append(")");

		updateSql.append(" WHERE client_code=? AND anchor_id=? AND menu_id=? AND project_id=?");

		int i = -1;
		DataBase db = new DataBase();
		Connection conn = null;
		PreparedStatement pres = null;
		try
		{
			if (existId != 0)
			{
				// UPDATE languageData SET
				// anchor_key=?,remark=?,language_1=?,language_2=?,language_3=?,language_4=?,language_5=?,language_6=?,language_7=?
				// WHERE client_code=? AND anchor_id=? AND menu_id=? AND
				// project_id=?

				System.out.println(updateSql.toString());
				conn = db.getConnect();
				pres = conn.prepareStatement(updateSql.toString());
				pres.setString(1, anchorKey);
				pres.setString(2, remark);
				for (int j = 0; j < lan_size; j++)
				{
					pres.setString(j + 3, languageList.get(j).getLanguageValue());
				}

				pres.setInt(lan_size + 3, clientTypeCode);
				pres.setString(lan_size + 4, anchorId);
				pres.setInt(lan_size + 5, menuId);
				pres.setInt(lan_size + 6, projectId);

				i = pres.executeUpdate();
			}
			else
			{
				// INSERT INTO languageData (project_id, menu_id, anchor_id,
				// anchor_key, client_code,
				// remark,language_1,language_2,language_3,language_4,language_5,language_6,language_7)
				// VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)

				System.out.println(insertSql.toString());
				conn = db.getConnect();
				pres = conn.prepareStatement(insertSql.toString());
				pres.setInt(1, projectId);
				pres.setInt(2, menuId);
				pres.setString(3, anchorId);
				pres.setString(4, anchorKey);
				pres.setInt(5, clientTypeCode);
				pres.setString(6, remark);

				for (int k = 0; k < lan_size; k++)
				{
					pres.setString(k + 7, languageList.get(k).getLanguageValue());
				}

				i = pres.executeUpdate();
			}
			return i;
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
				{
					pres.close();
				}
				if (conn != null)
					conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param projectId
	 * @param menuId
	 * @param anchorId
	 * @return 0:��������� ��0:���ݸ÷���ִֵ�и��²���
	 */
	public int isExist(int projectId, int menuId, String anchorId, int clientTypeCode)
	{

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT id FROM languageData" + " WHERE client_code=" + clientTypeCode + " AND anchor_id='" + anchorId + "' AND menu_id=" + menuId + " AND project_id=" + projectId;
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(sql);
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
