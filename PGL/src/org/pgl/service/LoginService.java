package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pgl.Model.ProjectModel;
import org.pgl.Model.User;
import org.pgl.db.DataBase;
import org.pgl.util.EncryptUtil;

public class LoginService
{
	private static Log logger = LogFactory.getLog(LoginService.class);

	public User checkUser(String username, String password)
	{
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		User user = null;
		String namesql = "SELECT u.id,u.name,u.deltab_role,u.password,u.role,r.operforlang FROM user as u,role as r WHERE u.name= ? and u.role=r.purview";
		String sql = "SELECT u.id,u.name,u.deltab_role,u.password,u.role,r.operforlang FROM user as u,role as r WHERE u.name= ? and u.password = ? and u.role=r.purview";
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(namesql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs == null || !rs.next()){
				user = new User();
			}
			else{
				ps = conn.prepareStatement(sql);
				ps.setString(1, username);
				ps.setString(2, password);
				rs = ps.executeQuery();
				if (rs != null && rs.next())
				{
					user = new User();
					user.setId(rs.getLong("id"));
					user.setName(rs.getString("name"));
					user.setDelTabRole(rs.getLong("deltab_role"));
					user.setPassword(rs.getString("password"));
					user.setRole(rs.getLong("role"));
					user.setOperforlang(rs.getString("operforlang"));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			closeStmt(ps);
			closeConn(conn);
		}

		return user;
	}

	public User getUserById(String id)
	{
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		User user = null;
		String sql = "SELECT id,name,deltab_role,password,role FROM user WHERE id = ?";
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, Long.parseLong(id));
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				user = new User();
				user.setId(rs.getLong("id"));
				user.setName(rs.getString("name"));
				user.setDelTabRole(rs.getLong("deltab_role"));
				// /��ȡ���ܺ������
				user.setPassword(EncryptUtil.getDecString(rs.getString("password")));
				// user.setPassword(rs.getString("password"));
				user.setRole(rs.getLong("role"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs != null)
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			closeStmt(ps);
			closeConn(conn);
		}

		return user;
	}

	public boolean insertProject(String name, String shortname)
	{
		boolean bIsSuc = true;
		String sql = "INSERT INTO project (name,shortname) VALUES (?,?)";
		PreparedStatement ps = null;
		Connection conn = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, shortname);
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

	public boolean projectExist(String name, String shortmane)
	{
		boolean exist = false;
		String sql = "SELECT COUNT(id) AS counter FROM project WHERE name=? OR shortname=? ";
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, shortmane);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				int _c = rs.getInt("counter");
				if (_c > 0)
				{
					exist = true;
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
		return exist;
	}

	public List<ProjectModel> getAllProject()
	{
		List<ProjectModel> lstProject = new ArrayList<ProjectModel>();

		ProjectModel objProject = null;
		String sql = "SELECT id,name,shortname FROM project";
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				objProject = new ProjectModel();
				objProject.setId(rs.getLong("id"));
				objProject.setName(rs.getString("name"));
				objProject.setShortname(rs.getString("shortname"));
				lstProject.add(objProject);
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
				closeStmt(ps);
				closeConn(conn);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}

		return lstProject;
	}

	// �޸ĸ�����Ϣ
	public boolean updateUserMes(String id, String password)
	{
		PreparedStatement ps = null;
		Connection conn = null;
		boolean bIsSuc = true;
		String sql = "UPDATE user SET password = ? WHERE id = ?";
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, password);
			ps.setLong(2, Long.parseLong(id));
			ps.executeUpdate();
		}
		catch (Exception e)
		{
			bIsSuc = false;
			e.printStackTrace();
		}
		finally
		{
			closeStmt(ps);
			closeConn(conn);
		}
		return bIsSuc;
	}

	/*
	 * ����projectIdɾ����Ŀ
	 * 
	 * GlobalKey��ֹɾ������Ϊ��Ŀ���GlobalKeyͨ��
	 */
	public void removeProject(int projectId) throws Exception
	{
		String img_sql = "DELETE FROM backgroundimage WHERE project_id=" + projectId;
		String menu_sql = "DELETE FROM menu WHERE projectid=" + projectId;
		String anchor_sql = "DELETE FROM anchor WHERE project_id=" + projectId;
		String mapping_sql = "DELETE FROM anchor_languageData WHERE project_id=" + projectId;
		String languageData_sql = "DELETE FROM languageData WHERE global_type=0 AND project_id=" + projectId;
		String project_sql = "DELETE FROM project WHERE id=" + projectId;
		PreparedStatement ps = null;
		Connection conn = null;
		try
		{
			conn = new DataBase().getConnect();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(project_sql);
			ps.addBatch();
			ps.addBatch(menu_sql);
			ps.addBatch(anchor_sql);
			ps.addBatch(mapping_sql);
			ps.addBatch(languageData_sql);
			ps.addBatch(img_sql);
			ps.executeBatch();

			conn.commit();
		}
		catch (Exception e)
		{
			conn.rollback();
			e.printStackTrace();
		}
		finally
		{
			closeStmt(ps);
			closeConn(conn);
		}
	}

	/*
	 * ������Ŀ����-���
	 */
	public boolean updateProject(int projectId, String name, String shortName) throws Exception
	{
		String updateSQL = "UPDATE project set shortname=?,name=? WHERE id=?";

		PreparedStatement ps = null;
		Connection conn = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(updateSQL);
			ps.setString(1, shortName);
			ps.setString(2, name);
			ps.setInt(3, projectId);

			ps.executeUpdate();
		}
		catch (Exception e)
		{
			logger.error("update project name Exception:", e);
			return false;
		}
		finally
		{
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
		return true;
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
