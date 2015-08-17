package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.pgl.Model.LanguageBean;
import org.pgl.db.DataBase;
import org.pgl.util.LM_Util;

public class CopyWriterService
{

	/**
	 * ��ȡ�İ��б�
	 * 
	 * @param projectId
	 * @param menuId
	 * @param clientTypeCode
	 * @return
	 */
	public Map<Integer, String> getCopyWriter(int projectId, int menuId, int clientTypeCode)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		DataBase db = new DataBase();
		ResultSet rs = null;
		// Ϊ��֤����Mapʱ ȡֵ��������ʹ�� LinkedHashMap
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		String sql = "SELECT id, language_1 FROM languageData WHERE anchor_id IS NULL AND menu_id=" + menuId + " AND project_id=" + projectId + " ORDER BY id";

		try
		{
			conn = db.getConnect();
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();
			while (rs.next())
			{
				map.put(rs.getInt(1), rs.getString(2));
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
		return map;
	}

	/**
	 * ��ȡ�İ������б�
	 * 
	 * @param copyWriterId
	 * @return
	 */
	public List<LanguageBean> getCopyWriterLanguage(int copyWriterId)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		DataBase db = new DataBase();
		List<LanguageBean> languageList = new ArrayList<LanguageBean>();
		LanguageBean bean;
		StringBuffer sql = new StringBuffer("SELECT anchor_key,client_code,remark,menu_id");
		ResultSet rs = null;
		for (int i = 0; i < LM_Util.languageList.size(); i++)
		{
			sql.append(",language_" + LM_Util.languageList.get(i).getLanguageCode());
		}
		sql.append(" FROM languageData WHERE id=" + copyWriterId);

		try
		{
			conn = db.getConnect();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				for (int j = 0; j < LM_Util.languageList.size(); j++)
				{
					bean = new LanguageBean();
					bean.setAnchorKey(rs.getString(1));
					bean.setClientType(rs.getInt(2));
					bean.setRemark(rs.getString(3));
					bean.setMenuId(rs.getInt(4));
					bean.setLanguageValue(rs.getString(5 + j));

					bean.setLanguageCode(LM_Util.languageList.get(j).getLanguageCode());
					bean.setLanguageName(LM_Util.languageList.get(j).getLanguageName());

					languageList.add(bean);
				}
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
		return languageList;
	}

	/**
	 * ɾ���İ�
	 * 
	 * @param cpId
	 * @return
	 */
	public boolean removeCopyWriter(int cpId)
	{
		Connection conn = null;
		PreparedStatement pres = null;
		int i = 0;
		try
		{
			conn = new DataBase().getConnect();
			pres = conn.prepareStatement("DELETE FROM languageData WHERE id=?");
			pres.setInt(1, cpId);
			i = pres.executeUpdate();
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

		if (i > 0)
			return true;

		return false;
	}

	/**
	 * �����İ�
	 * 
	 * @return
	 */
	public boolean saveNewWriter(int projectId, int menuId, String defaultName, int defaultClientCode)
	{
		Connection conn = null;
		PreparedStatement ps = null;

		try
		{
			conn = new DataBase().getConnect();
			String sql = "INSERT INTO languageData (project_id,menu_id,language_1,client_code) VALUES (?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, projectId);
			ps.setInt(2, menuId);
			ps.setString(3, defaultName);
			ps.setInt(4, defaultClientCode);
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

	/**
	 * �����İ�
	 * 
	 * @param id
	 * @param anchorKey
	 * @param languageList
	 * @param clientTypeCode
	 * @param remark
	 * @return
	 */
	public boolean updateWriter(int id, String anchorKey, List<LanguageBean> languageList, int clientTypeCode, String remark, int globalType)
	{
		int count = 0;
		Connection conn = null;
		PreparedStatement ps = null;

		StringBuffer sql = new StringBuffer("UPDATE languageData SET anchor_key=?,client_code=?,remark=?,global_type=?");
		for (int i = 0; i < languageList.size(); i++)
		{
			sql.append(",language_" + languageList.get(i).getLanguageCode() + "=?");
		}
		sql.append(" WHERE id=" + id);

		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, anchorKey);
			ps.setInt(2, clientTypeCode);
			ps.setString(3, remark);
			ps.setInt(4, globalType);

			for (int j = 0; j < languageList.size(); j++)
			{
				ps.setString(j + 5, languageList.get(j).getLanguageValue());
			}

			count = ps.executeUpdate();
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

		if (count > 0)
			return true;
		return false;
	}

}
