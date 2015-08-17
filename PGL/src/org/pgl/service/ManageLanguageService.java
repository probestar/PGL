package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pgl.Model.LanguageInfoBean;
import org.pgl.db.DataBase;

public class ManageLanguageService
{
	/*
	 * ��ȡ���������б���Ϣ
	 */
	public List<LanguageInfoBean> getAllLanguage()
	{
		String sql = "SELECT id,language_name,language_code,ios_filename,android_filename FROM language";

		List<LanguageInfoBean> list = new ArrayList<LanguageInfoBean>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();

			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			LanguageInfoBean bean = null;
			while (rs != null && rs.next())
			{
				bean = new LanguageInfoBean();
				bean.setId(rs.getInt(1));
				bean.setLanguageName(rs.getString(2));
				bean.setLanguageCode(rs.getInt(3));
				bean.setIos_fileName(rs.getString(4));
				bean.setAndroid_fileName(rs.getString(5));

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
	 * �������
	 */
	public boolean addLanguage(String languageName, String iosName, String androidName)
	{
		String lan_sql = "INSERT into language (language_name,language_code,ios_filename,android_filename) VALUES (?,?,?,?)";
		String languageData_sql = "ALTER TABLE languageData ADD language_? VARCHAR(500)";

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		int maxCode = -1;
		try
		{
			rs = new DataBase().executeQuery("SELECT MAX(language_code)+1 FROM language");
			while (rs != null && rs.next())
			{
				maxCode = rs.getInt(1);
			}

			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(lan_sql);
			ps.setString(1, languageName);
			ps.setInt(2, maxCode);
			ps.setString(3, iosName);
			ps.setString(4, androidName);
			ps.executeUpdate();

			ps2 = conn.prepareStatement(languageData_sql);
			ps2.setInt(1, maxCode);
			ps2.executeUpdate();
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
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (ps2 != null)
					ps2.close();
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
	 * �޸�������Ϣ
	 */
	public boolean updateLanguage(int id, String lanName, String iosName, String androidName)
	{
		String sql = "UPDATE language SET language_name=?,ios_filename=?,android_filename=? WHERE id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setString(1, lanName);
			ps.setString(2, iosName);
			ps.setString(3, androidName);
			ps.setInt(4, id);
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
	 * ɾ������
	 */
	public boolean removeLanguage(int id, int code)
	{
		//
		String language_sql = "DELETE FROM language WHERE id=?";
		String languageData_sql = "ALTER TABLE languageData DROP COLUMN language_" + code;

		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		try
		{
			conn = new DataBase().getConnect();

			ps = conn.prepareStatement(language_sql);
			ps.setInt(1, id);
			ps.executeUpdate();

			ps2 = conn.prepareStatement(languageData_sql);
			ps2.executeUpdate();
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
					ps.close();
				if (ps2 != null)
					ps2.close();
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
	 * У�����������Ƿ��ظ�
	 * 
	 * @return true:�ظ�; false:���ظ�
	 */
	public boolean checkLanguageName(String languageName)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement("SELECT COUNT(*) FROM language WHERE language_name='" + languageName + "'");
			rs = ps.executeQuery();// new
									// DataBase().executeQuery("SELECT COUNT(*) FROM language WHERE language_name='"
									// + languageName + "'");
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
		return false;
	}

	/**
	 * У��ios�����Ƿ��ظ�
	 * 
	 * @return true:�ظ�; false:���ظ�
	 */
	public boolean checkIOSName(String iosName)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
//		 rs = new DataBase().executeQuery("SELECT COUNT(*) FROM language WHERE ios_filename='" + iosName + "'");

		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement("SELECT COUNT(*) FROM language WHERE ios_filename='" + iosName + "'");
			rs = ps.executeQuery();
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
		return false;
	}

	/**
	 * У��android�����Ƿ��ظ�
	 * 
	 * @return true:�ظ�; false:���ظ�
	 */
	public boolean checkAndroidName(String androidName)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
//		ResultSet rs = new DataBase().executeQuery("SELECT COUNT(*) FROM language WHERE android_filename='" + androidName + "'");

		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement("SELECT COUNT(*) FROM language WHERE android_filename='" + androidName + "'");
			rs = ps.executeQuery();
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
		return false;
	}
}
