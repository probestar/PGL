package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.pgl.Model.MenuModel;
import org.pgl.db.DataBase;

public class MenuService
{
	public int newNemuId;

	public int getNewNemuId()
	{
		return newNemuId;
	}

	public void setNewNemuId(int newNemuId)
	{
		this.newNemuId = newNemuId;
	}

	public List<MenuModel> lstDelMenu = new ArrayList<MenuModel>();

	public List<MenuModel> getMenuList(String projectid)
	{
		List<MenuModel> lstMenu = new ArrayList<MenuModel>();
		MenuModel objMenu = null;
		String sql = "select * from menu where projectid=?";
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, Long.parseLong(projectid));
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				objMenu = new MenuModel();
				objMenu.setId(rs.getLong("id"));
				objMenu.setName(rs.getString("name"));
				objMenu.setNumber(rs.getInt("number"));
				objMenu.setParentid(rs.getLong("parentid"));
				objMenu.setProjectid(rs.getLong("projectid"));
				lstMenu.add(objMenu);
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

		return lstMenu;
	}

	public MenuModel getMenuById(String id)
	{
		MenuModel objMenu = null;
		String sql = "select * from menu where id = ?";
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, Long.parseLong(id));
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				objMenu = new MenuModel();
				objMenu.setId(rs.getLong("id"));
				objMenu.setName(rs.getString("name"));
				objMenu.setNumber(rs.getInt("number"));
				objMenu.setParentid(rs.getLong("parentid"));
				objMenu.setProjectid(rs.getLong("projectid"));
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

		return objMenu;
	}

	public boolean insertMenu(String name, long parentid, long projectid)
	{
		boolean bIsSuc = true;
		ResultSet rs = null;
		int number = 1;
		if (parentid != -1)
		{
			number = getNumber(parentid);
		}
		String sql = "insert into menu (name,projectid,parentid,number) values (?,?,?,?)";
		PreparedStatement ps = null;
		Connection conn = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, name);
			ps.setLong(2, projectid);
			ps.setLong(3, parentid);
			ps.setInt(4, number);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs != null && rs.next())
				this.newNemuId = rs.getInt(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			bIsSuc = false;
		}
		finally
		{
			closeResultSet(rs);
			closeStmt(ps);
			closeConn(conn);
		}

		return bIsSuc;
	}

	public int getNumber(long parentid)
	{
		int number = 0;
		String sql = "select number from menu where id = ?";
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, parentid);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				number = rs.getInt("number") + 1;
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

		return number;
	}

	/*
	 * ɾ���ڵ㼰�ڵ��µ�����
	 */
	public boolean deleteMenu(List<MenuModel> lstMenu, String projectId)
	{
		boolean bIsSuc = true;
		StringBuffer sb = new StringBuffer("");

		for (int i = 0; i < lstMenu.size(); i++)
		{
			sb.append(lstMenu.get(i).getId());
			if (i != lstMenu.size() - 1)
			{
				sb.append(",");
			}
		}
		String sql = "DELETE FROM menu WHERE id IN(" + sb.toString() + ")";
		String delMappingSql = "DELETE FROM anchor_languageData WHERE anchor_id IN(SELECT id FROM anchor WHERE menu_id IN(?)) AND project_id=?";// ɾ����ϵӳ���Ҫ����ɾ��ê��
		String delAnchorSql = "DELETE FROM anchor WHERE menu_id IN(?) AND project_id=?";// ɾ��ê��SQL
		String dataSQL = "DELETE FROM languageData WHERE menu_id in (?) AND global_type=0 AND project_id=?";

		PreparedStatement ps = null;
		PreparedStatement dataPS = null;
		PreparedStatement dataPSAnchor = null;
		PreparedStatement dataPSMapping = null;
		Connection conn = null;
		try
		{
			conn = new DataBase().getConnect();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);

			dataPS = conn.prepareStatement(dataSQL);
			dataPS.setString(1, sb.toString());
			dataPS.setString(2, projectId);

			dataPSAnchor = conn.prepareStatement(delAnchorSql);
			dataPSAnchor.setString(1, sb.toString());
			dataPSAnchor.setString(2, projectId);

			dataPSMapping = conn.prepareStatement(delMappingSql);
			dataPSMapping.setString(1, sb.toString());
			dataPSMapping.setString(2, projectId);

			dataPSMapping.executeUpdate();
			ps.executeUpdate();
			dataPS.executeUpdate();
			dataPSAnchor.executeUpdate();

			conn.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				conn.rollback();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			bIsSuc = false;
		}
		finally
		{
			closeStmt(ps);
			closeStmt(dataPS);
			closeStmt(dataPSAnchor);
			closeStmt(dataPSMapping);
			closeConn(conn);
		}

		return bIsSuc;
	}

	public boolean updateMenu(long id, String name)
	{
		boolean bIsSuc = true;
		String sql = "update menu set name = ?  where id=?";
		PreparedStatement ps = null;
		Connection conn = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setLong(2, id);
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

	public boolean updateMenuPid(long id, long pid)
	{
		boolean bIsSuc = true;
		String sql = "UPDATE menu SET parentid = ? WHERE id= ? ";
		PreparedStatement ps = null;
		Connection conn = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, pid);
			ps.setLong(2, id);
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

	public List<MenuModel> getNowDeleteMenu(long id)
	{
		String sql = "SELECT id,name,number,parentid,projectid FROM menu WHERE id=?";
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			MenuModel objMenu = null;
			if (rs != null && rs.next())
			{
				objMenu = new MenuModel();
				objMenu.setId(rs.getLong("id"));
				objMenu.setName(rs.getString("name"));
				objMenu.setNumber(rs.getInt("number"));
				objMenu.setParentid(rs.getLong("parentid"));
				objMenu.setProjectid(rs.getLong("projectid"));
				lstDelMenu.add(objMenu);
			}
			if (objMenu != null)
			{
				getChildMenu(id);
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
		return lstDelMenu;
	}

	// ȡ����ɾ�������нڵ㡣
	public void getChildMenu(long id)
	{
		List<Long> lstDelMenuId = new ArrayList<Long>();
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			String childSql = "SELECT id,name,number,parentid,projectid FROM menu WHERE parentid = ?";
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(childSql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			MenuModel objMenu = new MenuModel();
			while (rs != null && rs.next())
			{
				objMenu = new MenuModel();
				objMenu.setId(rs.getLong("id"));
				objMenu.setName(rs.getString("name"));
				objMenu.setNumber(rs.getInt("number"));
				objMenu.setParentid(rs.getLong("parentid"));
				objMenu.setProjectid(rs.getLong("projectid"));
				lstDelMenu.add(objMenu);
				lstDelMenuId.add(objMenu.getId());
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
		if(lstDelMenuId != null && lstDelMenuId.size() > 0)	
			getChildMenuId(lstDelMenuId);
	}
	
	public void getChildMenuId(List<Long> lstDelMenuId)
	{
		List<Long> listDelMenuId = new ArrayList<Long>();
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
			
		for (Long id : lstDelMenuId) {
			try
			{
				String childSql = "SELECT id,name,number,parentid,projectid FROM menu WHERE parentid = ?";
				conn = new DataBase().getConnect();
				ps = conn.prepareStatement(childSql);
				ps.setLong(1, id);
				rs = ps.executeQuery();
				MenuModel objMenu = new MenuModel();
				while (rs != null && rs.next())
				{
					objMenu = new MenuModel();
					objMenu.setId(rs.getLong("id"));
					objMenu.setName(rs.getString("name"));
					objMenu.setNumber(rs.getInt("number"));
					objMenu.setParentid(rs.getLong("parentid"));
					objMenu.setProjectid(rs.getLong("projectid"));
					lstDelMenu.add(objMenu);
					listDelMenuId.add(objMenu.getId());
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
		}
		if(listDelMenuId != null && listDelMenuId.size() > 0)	
			getChildMenuId(listDelMenuId);
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

	public List<MenuModel> getLstDelMenu()
	{
		return lstDelMenu;
	}

	public void setLstDelMenu(List<MenuModel> lstDelMenu)
	{
		this.lstDelMenu = lstDelMenu;
	}

}
