package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.pgl.Model.AnchorInfoBean;
import org.pgl.Model.LanguageBean;
import org.pgl.db.DataBase;
import org.pgl.util.LM_Util;

public class GetAllLanguageService
{
	/**
	 * 
	 * @param languageList
	 * @param clientTypeCode
	 * @param anchorId
	 * @param anchorKey
	 * @param projectId
	 * @param menuId
	 * @param flag
	 *            true:�İ� false:ê��
	 * @return
	 */
	public List<LanguageBean> getAllLanguageData(List<LanguageBean> languageList, int clientTypeCode, String anchorId, String anchorKey, int projectId, int menuId, boolean flag)
	{
		// ��ȡê������SQL
		StringBuffer sbSQL = new StringBuffer("SELECT id,");
		for (int i = 0; i < languageList.size(); i++)
		{
			sbSQL.append("language_" + languageList.get(i).getLanguageCode() + ",");
		}
		sbSQL.append("remark FROM languageData WHERE anchor_id='" + anchorId + "' AND client_code=" + clientTypeCode + " AND menu_id=" + menuId + " AND project_id=" + projectId);

		// ��ȡ�İ�����SQL
		StringBuffer cpSQL = new StringBuffer("SELECT id,");
		for (int i = 0; i < languageList.size(); i++)
		{
			cpSQL.append("language_" + languageList.get(i).getLanguageCode() + ",");
		}
		cpSQL.append("remark FROM languageData WHERE anchor_id IS NULL AND anchor_key='" + anchorKey + "' AND client_code=" + clientTypeCode + " AND menu_id=" + menuId + " AND project_id=" + projectId);

		try
		{
			ResultSet rs;
			if (flag)
			{
				rs = new DataBase().executeQuery(cpSQL.toString());
			}
			else
			{
				rs = new DataBase().executeQuery(sbSQL.toString());
			}

			if (rs != null && rs.next())
			{
				for (int i = 0; i < languageList.size(); i++)
				{
					languageList.get(i).setLanguageDataId(rs.getInt(1));
					languageList.get(i).setLanguageValue(rs.getString(i + 2));
					languageList.get(i).setRemark(rs.getString(languageList.size() + 2));
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return languageList;
	}

	public String getAnchorKey(String anchorId) throws Exception
	{
		ResultSet rs = null;
		try
		{
			String sql = "SELECT anchor_key FROM anchor WHERE anchor_id='" + anchorId + "'";
			rs = new DataBase().executeQuery(sql);
			while (rs != null && rs.next())
			{
				return rs.getString(1);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (rs != null)
				rs.close();
		}
		return null;
	}

	// ------------------------------------������Ϊ��д˫��ê���ȡ��Ϣ�¼ӵķ���--START
	public int getAnchorId(int projectId, int menuId, String anchorId) throws Exception
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			Thread.sleep(1000);
			con = new DataBase().getConnect();
			ps = con.prepareStatement("SELECT id FROM anchor WHERE anchor_id=? AND menu_id=? AND project_id=?");
			ps.setString(1, anchorId);
			ps.setInt(2, menuId);
			ps.setInt(3, projectId);

			rs = ps.executeQuery();
			while (rs != null && rs.next())
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
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			// if (con != null)
			// con.close();
		}

		return 0;
	}

	public List<AnchorInfoBean> getAllAnchorInfo(int projectId, int menuId, String anchorId) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AnchorInfoBean bean;
		LanguageBean lanBean;
		List<LanguageBean> lanList;
		List<AnchorInfoBean> list = new ArrayList<AnchorInfoBean>();

		try
		{
			StringBuffer sql = new StringBuffer("SELECT id,project_id,menu_id,anchor_id,anchor_key,client_code,remark");

			for (LanguageBean b : LM_Util.languageList)
			{
				sql.append(",language_").append(b.getLanguageCode());
			}
			sql.append(" FROM languageData WHERE anchor_id=? AND menu_id=? AND project_id=?");

			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, anchorId);
			ps.setInt(2, menuId);
			ps.setInt(3, projectId);
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				bean = new AnchorInfoBean();
				bean.anchorKey = rs.getString("anchor_key");
				bean.languageDataId = rs.getInt("id");
				bean.clientTypeCode = rs.getInt("client_code");
				bean.remark = rs.getString("remark");

				lanList = new ArrayList<LanguageBean>();
				// �˴��Ƿ��п��������������˳�����
				for (LanguageBean b : LM_Util.languageList)
				{
					lanBean = new LanguageBean();
					lanBean.setLanguageName(b.getLanguageName());
					lanBean.setLanguageCode(b.getLanguageCode());
					lanBean.setLanguageValue(rs.getString("language_" + b.getLanguageCode()));
					lanList.add(lanBean);
				}
				bean.languageList = lanList;

				list.add(bean);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
//			if (conn != null)
//				conn.close();
		}
		return list;
	}

	/*
	 * ��������ê�㣬���ã����������������SQL<��һ��>
	 * 
	 * ��Ϊ�˷�ֹһ��ê�����ظ�����ͬһKEY�����ñ�����Լ��
	 */
	public void saveOrUpdateAnchorInfo(List<String> sqlList) throws Exception
	{
		if (sqlList == null || sqlList.size() == 0)
			return;

		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = new DataBase().getConnect();
			conn.setAutoCommit(false);// Ĭ��Ϊtrue,true������²���rollback

			ps = conn.prepareStatement(sqlList.get(0));
			ps.addBatch();
			for (int i = 1; i < sqlList.size(); i++)
			{
				ps.addBatch(sqlList.get(i));
			}
			ps.executeBatch();
			conn.commit();
		}
		catch (SQLException e)
		{
			// ӳ���ΨһԼ��SQL����
			conn.rollback();
			e.printStackTrace();
		}
		catch (Exception e)
		{
			conn.rollback();
			e.printStackTrace();
		}
		finally
		{
			ps.close();
			//conn.close();
		}
	}

	/*
	 * �����û������KEYģ����ѯ�����е�KEY-->������GlobalKey--���ٷ���Ŀ
	 */
	public List<AnchorInfoBean> quoteAnchorKey(String anchorKey, int projectId) throws Exception
	{
		Connection conn = null;
		PreparedStatement pres = null;
		ResultSet rs = null;
		List<LanguageBean> list = null;
		try
		{
			List<AnchorInfoBean> anInfoList = new ArrayList<AnchorInfoBean>();
			AnchorInfoBean anBean;
			conn = new DataBase().getConnect();
			StringBuffer sql = new StringBuffer("SELECT id,anchor_id,anchor_key,client_code,remark");
			for (LanguageBean bean : LM_Util.languageList)
			{
				sql.append(",language_").append(bean.getLanguageCode());
			}
			sql.append(" FROM languageData WHERE anchor_key LIKE '" + anchorKey + "%' AND global_type=1 ORDER BY anchor_key"); // AND
																																// project_id=?
																																// to
			pres = conn.prepareStatement(sql.toString());
			// pres.setInt(1, projectId);
			rs = pres.executeQuery();
			while (rs != null && rs.next())
			{
				anBean = new AnchorInfoBean();
				anBean.setLanguageDataId(rs.getInt(1));
				anBean.setAnchorId(rs.getString(2));
				anBean.setAnchorKey(rs.getString(3));
				anBean.setClientTypeCode(rs.getInt(4));
				anBean.setRemark(rs.getString(5));
				for (LanguageBean bb : LM_Util.languageList)
				{
					list = new ArrayList<LanguageBean>();
					LanguageBean b = new LanguageBean();
					b.setLanguageCode(bb.getLanguageCode());
					b.setLanguageValue(rs.getString("language_" + bb.getLanguageCode()));
					list.add(b);
				}
				anBean.setLanguageList(list);

				anInfoList.add(anBean);
			}

			return anInfoList;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (rs != null)
				rs.close();
			if (pres != null)
				pres.close();
			if (conn != null)
				conn.close();
		}

		return null;
	}

	public List<AnchorInfoBean> quoteAnchorKeyById(int quoteLanguageDataId) throws Exception
	{
		Connection conn = null;
		PreparedStatement pres = null;
		ResultSet rs = null;
		List<LanguageBean> list = null;
		try
		{
			List<AnchorInfoBean> anInfoList = new ArrayList<AnchorInfoBean>();
			AnchorInfoBean anBean;
			conn = new DataBase().getConnect();
			StringBuffer sql = new StringBuffer("SELECT id,anchor_id,anchor_key,client_code,remark");
			for (LanguageBean bean : LM_Util.languageList)
			{
				sql.append(",language_").append(bean.getLanguageCode());
			}
			sql.append(" FROM languageData WHERE id=?");
			pres = conn.prepareStatement(sql.toString());
			pres.setInt(1, quoteLanguageDataId);
			rs = pres.executeQuery();
			while (rs != null && rs.next())
			{
				anBean = new AnchorInfoBean();
				anBean.setLanguageDataId(rs.getInt(1));
				anBean.setAnchorId(rs.getString(2));
				anBean.setAnchorKey(rs.getString(3));
				anBean.setClientTypeCode(rs.getInt(4));
				anBean.setRemark(rs.getString(5));
				list = new ArrayList<LanguageBean>();
				for (LanguageBean bb : LM_Util.languageList)
				{
					LanguageBean b = new LanguageBean();
					b.setLanguageCode(bb.getLanguageCode());
					b.setLanguageValue(rs.getString("language_" + bb.getLanguageCode()));
					list.add(b);
				}
				anBean.setLanguageList(list);

				anInfoList.add(anBean);
			}

			return anInfoList;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (rs != null)
				rs.close();
			if (pres != null)
				pres.close();
			if (conn != null)
				conn.close();
		}

		return null;
	}

	/**
	 * ���һ����¼����������
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public int insertDataReturnKey(String inserSql) throws Exception
	{
		int id = 0;
		Connection conn = null;
		PreparedStatement pres = null;
		ResultSet rs = null;

		try
		{
			conn = new DataBase().getConnect();
			pres = conn.prepareStatement(inserSql, Statement.RETURN_GENERATED_KEYS);
			pres.executeUpdate();

			// ��������ִ�д� Statement ����������������Զ����ɵļ�
			rs = pres.getGeneratedKeys();
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
			if (rs != null)
				rs.close();
			if (pres != null)
				pres.close();
			if (conn != null)
				conn.close();
		}
		return id;
	}

	/**
	 * ����ӳ���ϵ��ȡê���µ�KEY
	 * 
	 * @param anchorTavleId
	 * @return
	 * @throws Exception
	 */
	public List<AnchorInfoBean> getLanguageInfoByAnchorTableId(int anchorTableId) throws Exception
	{
		List<AnchorInfoBean> list = null;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps_res = null;
		ResultSet rs = null;
		ResultSet res = null;
		try
		{
			list = new ArrayList<AnchorInfoBean>();
			AnchorInfoBean bean;
			LanguageBean lanBean;
			List<LanguageBean> lanList;

			conn = new DataBase().getConnect();

			// ��ȡӳ���ϵ����ê������ݵĶ�Ӧ���ݵ�ID
			ps_res =conn.prepareStatement("SELECT languagedata_id FROM anchor_languageData WHERE anchor_id=" + anchorTableId);
			res = ps_res.executeQuery();//conn.prepareStatement("SELECT languagedata_id FROM anchor_languageData WHERE anchor_id=" + anchorTableId).executeQuery();
			StringBuffer sql = new StringBuffer("SELECT id,project_id,menu_id,anchor_id,anchor_key,client_code,remark");
			for (LanguageBean b : LM_Util.languageList)
			{
				sql.append(",language_").append(b.getLanguageCode());
			}
			sql.append(" FROM languageData WHERE id in(0,");
			while (res.next())
			{
				sql.append(res.getInt(1)).append(",");
			}
			sql = new StringBuffer(sql.toString().substring(0, sql.length() - 1));

			sql.append(")");
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				bean = new AnchorInfoBean();
				bean.anchorKey = rs.getString("anchor_key");
				bean.languageDataId = rs.getInt("id");
				bean.clientTypeCode = rs.getInt("client_code");
				bean.remark = rs.getString("remark");

				lanList = new ArrayList<LanguageBean>();
				// �˴��Ƿ��п��������������˳�����
				for (LanguageBean b : LM_Util.languageList)
				{
					lanBean = new LanguageBean();
					lanBean.setLanguageName(b.getLanguageName());
					lanBean.setLanguageCode(b.getLanguageCode());
					lanBean.setLanguageValue(rs.getString("language_" + b.getLanguageCode()));
					lanList.add(lanBean);
				}
				bean.languageList = lanList;

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
				if(res!=null)
					res.close();
				
				if (rs != null)
					rs.close();
				
				if(ps_res!=null)
					ps_res.close();
				if (ps != null)
					ps.close();
				// if (conn != null)
				// conn.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return list;
	}

	public String checkDuplicateKey(int projectId, String anchorKey) throws Exception
	{
		StringBuffer result = new StringBuffer();
		StringBuffer sql = new StringBuffer("SELECT id,anchor_key,client_code,remark");

		for (LanguageBean bean : LM_Util.languageList)
		{
			sql.append(",language_").append(bean.getLanguageCode());
		}
		sql.append(" FROM languageData WHERE anchor_key LIKE '").append(anchorKey).append("%' AND project_id=?");

		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			prst = conn.prepareStatement(sql.toString());
			prst.setInt(1, projectId);
			rs = prst.executeQuery();

			// ����json����
			if (rs != null && rs.next())
			{
				result.append("{id:'").append(rs.getInt("id")).append("'}");
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
		return result.toString();
	}

	/**
	 * 
	 * @param projectId
	 * @param anchorKey
	 * @return 0:���ݲ��ظ�;>0:�ظ����ݵ�����
	 * @throws Exception
	 *             GeneralKey���ظ�����֤Ҫ����Ŀ;��ͨKEY����֤���ܿ���Ŀ
	 */
	public int checkDuplicateKeyReturnKey(int projectId, String anchorKey) throws Exception
	{
		String sql;
		if (anchorKey != null && anchorKey.toLowerCase().startsWith("general_"))
		{
			sql = "SELECT id FROM languageData WHERE anchor_key =?";
		}
		else
		{
			sql = "SELECT id FROM languageData WHERE anchor_key =? AND project_id=?";
		}

		Connection conn = null;
		PreparedStatement prst = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			prst = conn.prepareStatement(sql);
			prst.setString(1, anchorKey);
			if (anchorKey != null && !anchorKey.toLowerCase().startsWith("general_"))
				prst.setInt(2, projectId);
			rs = prst.executeQuery();

			if (rs != null && rs.next())
			{
				return rs.getInt("id");
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
		return 0;
	}

	/*
	 * ɾ��Tabҳ
	 * 
	 * 1.ɾ��languageData��<ͨ��KEYֻ��ɾ�����ù�ϵ,��ͨKEYɾ�����ݺ����ù�ϵ>
	 * 
	 * 2.ɾ����ϵ��
	 * 
	 * 3.ɾ��ê���<�����������ɾ����Tab�Ƿ�Ϊ���һ��Tab>
	 * 
	 * ���ɾ����Tab�µ�Key�������ط�����,���Key�����������ط���KEY�����������õĵط�Ҳ�����ٱ�չʾ
	 * 
	 * global_type=0 ��ͨ���� global_type=1ͨ������
	 */
	public String deleteTabById(int anchorTableId, int languageDataId, int tabCount) throws Exception
	{

		PreparedStatement ps = null;
		Connection conn = null;
		try
		{
			String deleteSQL1 = String.format("DELETE FROM anchor_languageData WHERE anchor_id=%s AND languageData_id=%s", anchorTableId, languageDataId);
			String deleteSQL2 = String.format("DELETE FROM languageData WHERE id=%s AND global_type=0", languageDataId);
			String deleteSQL3 = String.format("DELETE FROM anchor WHERE id=%s", anchorTableId);
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(deleteSQL1);
			ps.addBatch();
			ps.addBatch(deleteSQL2);
			// 2:һ��NewTab��һ��Ҫɾ����Tab������2��Tab����֪tabCount>=2
			if (tabCount == 2)
				ps.addBatch(deleteSQL3);

			ps.executeBatch();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}

		return null;
	}

	// ------------------------------------������Ϊ��д˫��ê���ȡ��Ϣ�¼ӵķ���--END

}
