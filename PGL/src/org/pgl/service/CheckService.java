package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pgl.Model.AllLanguage;
import org.pgl.Model.CheckModel;
import org.pgl.Model.LanguageBean;
import org.pgl.Model.MenuModel;
import org.pgl.db.DataBase;
import org.pgl.util.LM_Util;

public class CheckService
{
	List<LanguageBean> _lang = LM_Util.languageList;
	public boolean updateCheck(List<AllLanguage> lstLang, long id)
	{
		boolean bIsSuc = true;
		StringBuffer updateSql = new StringBuffer("UPDATE languageData SET ");
		for (int i = 0; i < lstLang.size(); i++)
		{
			if (i != 0)
			{
				updateSql.append(",");
			}
			updateSql.append(" language_" + LM_Util.languageList.get(i).getLanguageCode() + "=? ");
		}
		updateSql.append(" where id=?");

		PreparedStatement ps = null;
		Connection conn = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(updateSql.toString());
			for (int j = 1; j <= lstLang.size(); j++)
			{
				ps.setString(j, lstLang.get(j - 1).getLanguagevalue());
			}
			ps.setLong(lstLang.size() + 1, id);
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsSuc = false;
		}
		finally
		{
			closeStmt(ps);
			closeConn(conn);
		}

		return bIsSuc;
	}

	public int getListCount(String projectId, String menuId, String searcheKey)
	{
		int count = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM languageData WHERE project_id=" + projectId);
			if (searcheKey != null)
			{
				sql.append(" AND (anchor_key LIKE '%" + searcheKey + "%'");
				if(_lang!=null && _lang.size()>0){
					for (LanguageBean languageBean : _lang)
					{
						sql.append(" OR language_").append(languageBean.getLanguageCode()).append(" LIKE '%").append(searcheKey).append("%'");
					}
					sql.append(")");
				}
//				sql.append(" OR language_1 LIKE '%").append(searcheKey).append("%'");
//				sql.append(" OR language_2 LIKE '%").append(searcheKey).append("%')");
			}
			if (menuId != null)
			{
				sql.append(" AND menu_id=" + menuId);
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
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			closeStmt(ps);
			closeConn(conn);
		}
		return count;
	}

	public List<CheckModel> getList(int projectId, String menuId, String searcheKey, int startCount)
	{
		List<CheckModel> list = new ArrayList<CheckModel>();
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			// ƴSQL
			StringBuffer sql = new StringBuffer("SELECT * FROM languageData WHERE project_id=").append(projectId);
			if (menuId != null)
			{
				sql.append(" AND menu_id=" + menuId);
			}
			if (searcheKey != null && !searcheKey.equals("null"))
			{
				sql.append(" AND (anchor_key LIKE '%").append(searcheKey).append("%'");
				if(_lang!=null && _lang.size()>0){
					for (LanguageBean languageBean : _lang)
					{
						sql.append(" OR language_").append(languageBean.getLanguageCode()).append(" LIKE '%").append(searcheKey).append("%'");
					}
					sql.append(")");
				}
//				sql.append(" OR language_1 LIKE '%").append(searcheKey).append("%'");
//				sql.append(" OR language_2 LIKE '%").append(searcheKey).append("%')");
			}
			sql.append(" ORDER BY anchor_key LIMIT ").append(startCount).append(",").append(LM_Util.SHOW_COUNT);

			System.out.println("FenYe Query SQL=" + sql.toString());

			List<MenuModel> lstMenu = new ArrayList<MenuModel>();
			List<AllLanguage> lstData = null;
			AllLanguage objLang = null;
			MenuService objMenuS = new MenuService();

			lstMenu = objMenuS.getMenuList(String.valueOf(projectId));

			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			CheckModel objCheck = null;
			while (rs != null && rs.next())
			{
				objCheck = new CheckModel();
				objCheck.setAnchor_key(rs.getString("anchor_key"));
				objCheck.setClientName(LM_Util.map.get(rs.getInt("client_code")));
				objCheck.setClientcode(rs.getInt("client_code"));
				objCheck.setId(rs.getLong("id"));
				objCheck.setAnchor_id(rs.getString("anchor_id"));
				for (MenuModel menu : lstMenu)
				{
					if (menu.getId() == rs.getInt("menu_id"))
					{
						objCheck.setMenuname(menu.getName());
						objCheck.setNumber(menu.getNumber());
						objCheck.setMenuid(menu.getId());
						objCheck.setMenuParentId(menu.parentid);
						break;
					}
				}
				objCheck.setProjectid(projectId);

				lstData = new ArrayList<AllLanguage>();
				for (int i = 0; i < LM_Util.languageList.size(); i++)
				{
					objLang = new AllLanguage();
					int code = LM_Util.languageList.get(i).getLanguageCode();
					objLang.setLanguagekey("language_" + code);
					objLang.setLanguagevalue(rs.getString("language_" + code));
					objLang.setLanguagecode(String.valueOf(code));
					lstData.add(objLang);
				}
				objCheck.setLstLanguage(lstData);

				list.add(objCheck);
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
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			closeStmt(ps);
			closeConn(conn);
		}

		return list;
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
