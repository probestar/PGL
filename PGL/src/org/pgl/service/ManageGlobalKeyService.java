package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pgl.Model.GlobalKeyBean;
import org.pgl.Model.LanguageBean;
import org.pgl.db.DataBase;
import org.pgl.util.LM_Util;

public class ManageGlobalKeyService
{
	public List<GlobalKeyBean> getAllGlobalKey() throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<GlobalKeyBean> list = new ArrayList<GlobalKeyBean>();
		GlobalKeyBean bean = null;
		List<LanguageBean> lanList = null;
		LanguageBean lanBean = null;
		try
		{
			conn = new DataBase().getConnect();
			StringBuffer sql = new StringBuffer("SELECT id,anchor_key,client_code,remark");
			for (LanguageBean b : LM_Util.languageList)
			{
				sql.append(",language_").append(b.getLanguageCode());
			}
			sql.append(" FROM languageData WHERE global_type=1 ORDER BY anchor_key");

			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				bean = new GlobalKeyBean();
				bean.setLanguageDataId(rs.getInt(1));
				bean.setAnchorKey(rs.getString(2));
				bean.setClientCode(rs.getInt(3));
				bean.setRemark(rs.getString(4));
				lanList = new ArrayList<LanguageBean>();
				for (LanguageBean b : LM_Util.languageList)
				{
					lanBean = new LanguageBean();
					lanBean.setLanguageValue(rs.getString("language_" + b.getLanguageCode()));
					lanBean.setLanguageCode(b.getLanguageCode());
					lanBean.setLanguageName(b.getLanguageName());
					lanList.add(lanBean);
					bean.setLanguageList(lanList);
				}
				list.add(bean);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
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
				throw e;
			}
		}
		return list;
	}

	/**
	 * ���ȫ��ͨ��KEY
	 * 
	 * 0��ʧ�� ��0���ɹ�
	 */
	public int addGlobalKey(String sql) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{

			conn = new DataBase().getConnect();

			ps = conn.prepareStatement(sql);

			return ps.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (ps != null)
			{
				ps.close();
			}
			if (conn != null)
				conn.close();
		}
		return 0;
	}

	public int removeGlobalKey(int id) throws Exception
	{
		// ɾ��GlobalKey
		String sql = "DELETE FROM languageData WHERE id="+id;
		
		//ɾ���Ը�GlobalKey������
		String mappingSql = "DELETE FROM anchor_languageData WHERE languagedata_id="+id;
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = new DataBase().getConnect();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			ps.addBatch();
			ps.addBatch(mappingSql);
			
			ps.executeBatch();
			conn.commit();
			return 1;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		finally
		{
			if (ps != null)
			{
				ps.close();
			}
			if (conn != null)
				conn.close();
		}
		return 0;
	}
}
