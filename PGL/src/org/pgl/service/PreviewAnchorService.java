package org.pgl.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.pgl.Model.AnchorBean;
import org.pgl.db.DataBase;

public class PreviewAnchorService
{

	/**
	 * ��ȡê����Ϣ�����Ӧ������������Ϣ ---Ԥ��������
	 * 
	 * @param projectId
	 * @param menuId
	 * @param languageCode
	 * @param clientTypeCode
	 * @return
	 */
	public synchronized List<AnchorBean> getAnchorInfo(int projectId, int menuId, int languageCode, int clientTypeCode)
	{
		List<AnchorBean> list = new ArrayList<AnchorBean>();
		AnchorBean bean;
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps_lan = null;
		PreparedStatement ps_dataId = null;
		DataBase db = new DataBase();
		ResultSet rs_lan = null;
		ResultSet rs_dataId = null;
		ResultSet rs = null;
		try
		{
			String anchorSql = String.format("SELECT id,anchor_id,anchor_key,anchor_x,anchor_y,anchor_width,anchor_height" + " FROM anchor WHERE menu_id=%s AND project_id=%s", menuId, projectId);

			System.out.println("��ѯê��SQL:" + anchorSql);
			String sql = "SELECT languagedata_id FROM anchor_languageData WHERE anchor_id=%s LIMIT 0,1";

			conn = db.getConnect();
			ps = conn.prepareStatement(anchorSql);
			// ���ز˵��µ�����ê��������Ϣ
			rs = ps.executeQuery();// db.executeQuery(anchorSql);

			// --
			// String lan_sql = "SELECT language_"+languageCode +
			// " FROM languageData" +
			// " WHERE	menu_id="+menuId +
			// " AND project_id="+projectId +
			// " AND anchor_id='";
			String lan_sql = "SELECT language_" + languageCode + " FROM languageData WHERE id=%s";

			while (rs != null && rs.next())
			{
				ps_dataId = conn.prepareStatement(String.format(sql, rs.getInt(1)));
				rs_dataId = ps_dataId.executeQuery();// db.executeQuery(String.format(sql,
														// rs.getInt(1)));
				while (rs_dataId != null && rs_dataId.next())
				{
					ps_lan = conn.prepareStatement(String.format(lan_sql, rs_dataId.getInt(1)));
					rs_lan = ps_lan.executeQuery();// db.executeQuery(String.format(lan_sql,
													// rs_dataId.getInt(1)));

					bean = new AnchorBean();

					bean.setAnchorId(rs.getString(2));
					bean.setAnchorKey(rs.getString(3));
					bean.setAnchor_x(rs.getInt(4));
					bean.setAnchor_y(rs.getInt(5));
					bean.setAnchor_width(rs.getInt(6));
					bean.setAnchor_height(rs.getInt(7));

					if (rs_lan.next())
					{
						bean.setAnchorName(rs_lan.getString(1));
					}

					list.add(bean);
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("++++++++++++++++123+++++++++++++");
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (rs != null)
					rs.close();
				if (rs_lan != null)
					rs_lan.close();
				if (rs_dataId != null)
					rs_dataId.close();
				if (ps != null)
					ps.close();
				if (ps_lan != null)
					ps_lan.close();
				if (ps_dataId != null)
					ps_dataId.close();
//				if (conn != null)
//					conn.close();
			}
			catch (SQLException e)
			{
				System.err.println("++++++++++++++++++++++++++++++");
				e.printStackTrace();
				System.err.println("++++++++++++++++++++++++++++++");
			}
		}
		return list;
	}

}
