package org.pgl.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.pgl.Model.AllLanguage;
import org.pgl.Model.CheckModel;
import org.pgl.Model.LanguageBean;
import org.pgl.Model.MenuModel;
import org.pgl.db.DataBase;
import org.pgl.util.LM_Util;

/**
 * ���ݵĵ�������
 * 
 * @author mazhanghui
 */
public class DownloadService
{
	public static String filepath = "";
	public List<String> lstFilePath = new ArrayList<String>();
	private String xlsFile = "language.xls";// Excel�ļ�����
	private static Log logger = LogFactory.getLog(DownloadService.class);

	public String createFileAndDownload(String[] languageId, int clientId, long projectId)
	{
		long nowTime = System.currentTimeMillis();
		String strNowPath = filepath + nowTime + "/";

		switch (clientId)
		{
			case 1:
				createAndroidFile(languageId, projectId, strNowPath);
			break;
			case 2:
				createIosFile(languageId, projectId, strNowPath);
			break;
			case 3:
				createPCFile(languageId, projectId, strNowPath);
			break;
			case 4:
				createServerFile(projectId, strNowPath);
			break;
			case 5:
				createJavaFile(languageId, projectId, strNowPath);
			break;
			default:
				createAndroidFile(languageId, projectId, strNowPath);
				createIosFile(languageId, projectId, strNowPath);
				createPCFile(languageId, projectId, strNowPath);
				createServerFile(projectId, strNowPath);
				createJavaFile(languageId, projectId, strNowPath);
			break;
		}

		exportLanguage(projectId, strNowPath);

		return strNowPath;
	}

	/**
	 * JAVA 
	 * @param languageId
	 * @param projectId
	 * @param strNowPath
	 */
	private void createJavaFile(String[] languageId, long projectId, String strNowPath)
	{
		logger.info("Export Java File!");
		List<LanguageBean> lstLangData = null;
		List<LanguageBean> globalKeyList = null;
		List<LanguageBean> cpList = new ArrayList<LanguageBean>();

		File file = null;
		String strFilePath = "";

		try
		{
			for (int i = 0; i < languageId.length; i++)
			{
				try
				{
					lstLangData = new ArrayList<LanguageBean>();
					strFilePath = strNowPath + "java/";
					String StrFilePathName = strFilePath + "j2mestrres." + getFileName(languageId[i], "java_filename");
					file = new File(StrFilePathName);
					File parent = file.getParentFile();
					if (parent != null && !parent.exists())
					{
						parent.mkdirs();
					}
					file.createNewFile();

					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
					lstLangData = getAllLanguageData(languageId[i], LM_Util.JAVA_CLIENT_CODE, projectId);
					LanguageBean objLanguageBean = null;
					for (int j = 0; j < lstLangData.size(); j++)
					{
						objLanguageBean = lstLangData.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						out.write(objLanguageBean.getAnchorKey().toLowerCase() + "=" + objLanguageBean.getLanguageValue().replaceAll("\\$s", "\\$@").replaceAll("\"", "\\\\\"") + "\r\n");
					}

					globalKeyList = getGlobalKey(languageId[i], projectId, LM_Util.JAVA_CLIENT_CODE);
					for (int j = 0; j < globalKeyList.size(); j++)
					{
						objLanguageBean = globalKeyList.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						out.write(objLanguageBean.getAnchorKey().toLowerCase() + "=" + objLanguageBean.getLanguageValue().replaceAll("\"", "\\\\\"") + "\r\n");
					}

					// �İ� GlobalKey=1
					cpList = GetCopyWriter(languageId[i], projectId, LM_Util.JAVA_CLIENT_CODE);
					for (int j = 0; j < cpList.size(); j++)
					{
						objLanguageBean = cpList.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey() == null || objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue() == null || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						out.write(objLanguageBean.getAnchorKey().toLowerCase() + "=" + objLanguageBean.getLanguageValue().replaceAll("\\$s", "\\$@").replaceAll("\"", "\\\\\"") + "\r\n");
					}

					out.flush();
					out.close();
					lstFilePath.add(StrFilePathName);
				}
				catch (Exception e)
				{
					logger.error("Export JAVA File Exception!", e);
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Export JAVA File Exception!!", e);
		}
	}

	/**
	 * ����Android��Դ�ļ�
	 * 
	 * @param languageId
	 * @param projectId
	 * @param strNowPath
	 */
	private void createAndroidFile(String[] languageId, long projectId, String strNowPath)
	{
		logger.info("Export Android File!");
		List<LanguageBean> lstLangData = null;
		List<LanguageBean> globalKeyList = null;
		List<LanguageBean> cpList = new ArrayList<LanguageBean>();
		strNowPath = strNowPath + "android/";
		File file = null;

		try
		{
			for (int i = 0; i < languageId.length; i++)
			{
				try
				{
					lstLangData = new ArrayList<LanguageBean>();
					String StrFilePathName = strNowPath + getFileName(languageId[i], "android_filename") + ".xml";
					file = new File(StrFilePathName);
					File parent = file.getParentFile();

					// ����Ŀ¼
					if (parent != null && !parent.exists())
					{
						parent.mkdirs();
					}

					file.createNewFile();

					PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
					lstLangData = getAllLanguageData(languageId[i], LM_Util.ANDROID_CLIENT_CODE, projectId);
					output.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
					output.write("<resources xmlns:android=\"http://schemas.android.com/apk/res/android\" xmlns:xliff=\"urn:oasis:names:tc:xliff:document:1.2\">\r\n");

					LanguageBean objLanguageBean = null;
					for (int j = 0; j < lstLangData.size(); j++)
					{
						objLanguageBean = lstLangData.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey() == null || objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue() == null || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						output.write("<string name=\"" + objLanguageBean.getAnchorKey().toLowerCase() + "\">\"" + objLanguageBean.getLanguageValue().replaceAll("<", "&lt") + "\"</string>\r\n");

					}

					globalKeyList = getGlobalKey(languageId[i], projectId, LM_Util.ANDROID_CLIENT_CODE);
					for (int j = 0; j < globalKeyList.size(); j++)
					{
						objLanguageBean = globalKeyList.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey() == null || objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue() == null || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						output.write("<string name=\"" + objLanguageBean.getAnchorKey().toLowerCase() + "\">\"" + objLanguageBean.getLanguageValue().replaceAll("<", "&lt") + "\"</string>\r\n");
					}

					// �İ� GlobalKey=1
					cpList = GetCopyWriter(languageId[i], projectId, LM_Util.ANDROID_CLIENT_CODE);
					for (int j = 0; j < cpList.size(); j++)
					{
						objLanguageBean = cpList.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey() == null || objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue() == null || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						output.write("<string name=\"" + objLanguageBean.getAnchorKey().toLowerCase() + "\">\"" + objLanguageBean.getLanguageValue().replaceAll("<", "&lt") + "\"</string>\r\n");
					}

					output.write("</resources>");
					output.flush();
					output.close();
					lstFilePath.add(StrFilePathName);
				}
				catch (Exception e)
				{
					logger.error("Export Android File Exception!", e);
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Export Android File Exception!!", e);
		}
	}

	/**
	 * ����iOS��Դ�ļ�
	 * 
	 * @param languageId
	 * @param projectId
	 * @param strNowPath
	 */
	private void createIosFile(String[] languageId, long projectId, String strNowPath)
	{
		logger.info("Export IOS File!");
		List<LanguageBean> lstLangData = null;
		List<LanguageBean> globalKeyList = null;
		List<LanguageBean> cpList = new ArrayList<LanguageBean>();

		File file = null;
		String strFilePath = "";

		try
		{
			for (int i = 0; i < languageId.length; i++)
			{
				try
				{
					lstLangData = new ArrayList<LanguageBean>();
					strFilePath = strNowPath + "ios/" + getFileName(languageId[i], "ios_filename") + "/";
					String StrFilePathName = strFilePath + "Localizable.strings";
					file = new File(StrFilePathName);
					File parent = file.getParentFile();
					if (parent != null && !parent.exists())
					{
						parent.mkdirs();
					}
					file.createNewFile();

					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
					lstLangData = getAllLanguageData(languageId[i], LM_Util.IOS_CLIENT_CODE, projectId);
					LanguageBean objLanguageBean = null;
					for (int j = 0; j < lstLangData.size(); j++)
					{
						objLanguageBean = lstLangData.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						out.write("\"" + objLanguageBean.getAnchorKey().toLowerCase() + "\"=\"" + objLanguageBean.getLanguageValue().replaceAll("%S", "%@").replaceAll("%s", "%@").replaceAll("\\$s", "\\$@").replaceAll("\"", "\\\\\"") + "\";\r\n");
					}

					globalKeyList = getGlobalKey(languageId[i], projectId, LM_Util.IOS_CLIENT_CODE);
					for (int j = 0; j < globalKeyList.size(); j++)
					{
						objLanguageBean = globalKeyList.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						out.write("\"" + objLanguageBean.getAnchorKey().toLowerCase() + "\"=\"" + objLanguageBean.getLanguageValue().replaceAll("%S", "%@").replaceAll("%s", "%@").replaceAll("\\$s", "\\$@").replaceAll("\"", "\\\\\"") + "\";\r\n");
					}

					// �İ� GlobalKey=1
					cpList = GetCopyWriter(languageId[i], projectId, LM_Util.IOS_CLIENT_CODE);
					for (int j = 0; j < cpList.size(); j++)
					{
						objLanguageBean = cpList.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey() == null || objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue() == null || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						out.write("\"" + objLanguageBean.getAnchorKey().toLowerCase() + "\"=\"" + objLanguageBean.getLanguageValue().replaceAll("%S", "%@").replaceAll("%s", "%@").replaceAll("\\$s", "\\$@").replaceAll("\"", "\\\\\"") + "\";\r\n");
					}

					out.flush();
					out.close();
					lstFilePath.add(StrFilePathName);
				}
				catch (Exception e)
				{
					logger.error("Export iOS File Exception!", e);
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Export iOS File Exception!!", e);
		}
	}

	/**
	 * ����PC��Դ�ļ�
	 * 
	 * @param languageId
	 * @param projectId
	 * @param strNowPath
	 */
	private void createPCFile(String[] languageId, long projectId, String strNowPath)
	{
		logger.info("Export PC File!");
		List<LanguageBean> lstLangData = null;
		List<LanguageBean> globalKeyList = null;
		List<LanguageBean> cpList = new ArrayList<LanguageBean>();
		strNowPath = strNowPath + "pc/";
		File file = null;

		try
		{
			for (int i = 0; i < languageId.length; i++)
			{
				try
				{
					lstLangData = new ArrayList<LanguageBean>();
					globalKeyList = new ArrayList<LanguageBean>();

					String StrFilePathName = strNowPath + getFileName(languageId[i], "pc_filename") + ".xml";
					file = new File(StrFilePathName);
					File parent = file.getParentFile();
					if (parent != null && !parent.exists())
					{
						parent.mkdirs();
					}

					file.createNewFile();
					PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
					lstLangData = getAllLanguageData(languageId[i], LM_Util.PC_CLIENT_CODE, projectId);
					output.write("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n");
					output.write("<strings xmlns:xliff=\"urn:oasis:names:tc:xliff:document:1.2\">\r\n");
					LanguageBean objLanguageBean = null;
					for (int j = 0; j < lstLangData.size(); j++)
					{
						objLanguageBean = lstLangData.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey() == null || objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue() == null || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						output.write("<string id=\"" + objLanguageBean.getAnchorKey().toLowerCase() + "\">" + objLanguageBean.getLanguageValue() + "</string>\r\n");
					}
					Thread.sleep(300);
					globalKeyList = getGlobalKey(languageId[i], projectId, LM_Util.PC_CLIENT_CODE);
					for (int j = 0; j < globalKeyList.size(); j++)
					{
						objLanguageBean = globalKeyList.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey() == null || objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue() == null || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						output.write("<string id=\"" + objLanguageBean.getAnchorKey().toLowerCase() + "\">" + objLanguageBean.getLanguageValue() + "</string>\r\n");
					}
					Thread.sleep(300);
					// �İ� GlobalKey=1��
					cpList = GetCopyWriter(languageId[i], projectId, LM_Util.PC_CLIENT_CODE);
					for (int j = 0; j < cpList.size(); j++)
					{
						objLanguageBean = cpList.get(j);
						if (objLanguageBean == null)
						{
							continue;
						}
						if (objLanguageBean.getAnchorKey() == null || objLanguageBean.getAnchorKey().length() <= 0 || objLanguageBean.getLanguageValue() == null || objLanguageBean.getLanguageValue().length() <= 0)
						{
							continue;
						}
						output.write("<string id=\"" + objLanguageBean.getAnchorKey().toLowerCase() + "\">" + objLanguageBean.getLanguageValue() + "</string>\r\n");
					}

					output.write("</strings>");
					output.flush();
					output.close();
					lstFilePath.add(StrFilePathName);

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ���з�ͨ��KEY-<��ָ���ͻ�������>
	 * 
	 * @param languageId
	 * @param clientCode
	 * @param projectId
	 * @return
	 */
	private List<LanguageBean> getAllLanguageData(String languageId, int clientCode, long projectId)
	{
		List<LanguageBean> lstLangData = new ArrayList<LanguageBean>();
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "SELECT anchor_key,language_" + languageId + " FROM languageData WHERE global_type=0 AND project_id=? AND(client_code=? || client_code=?) ORDER BY anchor_key";
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, projectId);
			ps.setInt(2, clientCode);
			ps.setInt(3, LM_Util.ALL_CLIENT_CODE);
			rs = ps.executeQuery();

			LanguageBean objLang = null;
			while (rs != null && rs.next())
			{
				objLang = new LanguageBean();

				if (rs.getString("anchor_key") == null || rs.getString("anchor_key").length() <= 0 || rs.getString("language_" + languageId) == null || rs.getString("language_" + languageId).length() <= 0)
				{
					continue;
				}
				objLang.setAnchorKey(rs.getString("anchor_key"));
				objLang.setLanguageValue(rs.getString("language_" + languageId));
				lstLangData.add(objLang);
			}
		}
		catch (Exception e)
		{
			logger.error("Get AllLanguageData Exception:", e);
		}
		finally
		{
			closeResultSet(rs);
			closeStmt(ps);
			closeConn(conn);
		}

		return lstLangData;
	}

	/**
	 * ����Server�ļ�-<SQL�ļ�Ŀǰ��BUG���������д�����û��ת��>
	 * 
	 * @param projectId
	 * @param strNowPath
	 */
	private void createServerFile(long projectId, String strNowPath)
	{
		logger.info("Export Server File!");
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			strNowPath = strNowPath + "server/";
			String StrFilePathName = strNowPath + "language.sql";
			File file = new File(StrFilePathName);
			File parent = file.getParentFile();
			if (parent != null && !parent.exists())
			{
				parent.mkdirs();
			}
			file.createNewFile();

			PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));

			String sql = "SELECT anchor_key,language_1,language_2 FROM languageData WHERE (client_code=4||client_code=0) AND project_id=?";

			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, projectId);
			rs = ps.executeQuery();

			while (rs != null && rs.next())
			{
				output.write(String.format("INSERT INTO cin_language (textkey,servicename,chs,en) VALUES ('%s','xxx','%s','%s');\r\n", rs.getString(1), rs.getString(2), rs.getString(3)));
			}
			output.flush();
			output.close();
			lstFilePath.add(StrFilePathName);
		}
		catch (Exception e)
		{
			logger.error("Export Server File Exception!", e);
		}
		finally
		{
			closeResultSet(rs);
			closeStmt(ps);
			closeConn(conn);
		}
	}

	/**
	 * ��ȡȫ��ͨ��KEY
	 * 
	 * @param languageCode
	 * @param projectId
	 * @param clientCode
	 * @return
	 */
	private List<LanguageBean> getGlobalKey(String languageCode, long projectId, int clientCode)
	{
		List<LanguageBean> lstLangData = new ArrayList<LanguageBean>();
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
//		String sql = "SELECT anchor_key,language_" + languageCode + " FROM languageData WHERE id IN (SELECT DISTINCT languagedata_id FROM anchor_languageData WHERE project_id=? AND quote=1 AND (client_code=?||client_code=?)) and anchor_id IS NOT NULL ORDER BY anchor_key";
		String sql = "SELECT anchor_key,language_" + languageCode + " FROM languageData WHERE id IN (SELECT DISTINCT languagedata_id FROM anchor_languageData WHERE project_id=? AND quote=1 AND (client_code=?||client_code=?)) and anchor_id IS NOT NULL ORDER BY anchor_key";
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, projectId);
			ps.setInt(2, clientCode);
			ps.setInt(3, 0);
			rs = ps.executeQuery();

			LanguageBean objLang = null;
			while (rs.next())
			{
				objLang = new LanguageBean();

				if (rs.getString("anchor_key") == null || rs.getString("anchor_key").length() <= 0 || rs.getString("language_" + languageCode) == null || rs.getString("language_" + languageCode).length() <= 0)
				{
					continue;
				}
				objLang.setAnchorKey(rs.getString("anchor_key"));
				objLang.setLanguageValue(rs.getString("language_" + languageCode));
				lstLangData.add(objLang);
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

		return lstLangData;
	}

	/**
	 * ��ȡ�������ļ�����
	 * 
	 * @param languageCode
	 * @param projectId
	 * @param clientCode
	 * @return
	 */

	private List<LanguageBean> GetCopyWriter(String languageCode, long projectId, int clientCode)
	{
		List<LanguageBean> lstLangData = new ArrayList<LanguageBean>();
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "SELECT anchor_key,language_" + languageCode + " FROM languageData WHERE anchor_id IS NULL AND global_type=1 AND project_id=? ORDER BY anchor_key";
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, projectId);
			rs = ps.executeQuery();

			LanguageBean objLang = null;
			while (rs.next())
			{
				objLang = new LanguageBean();

				if (rs.getString("anchor_key") == null || rs.getString("anchor_key").length() <= 0 || rs.getString("language_" + languageCode) == null || rs.getString("language_" + languageCode).length() <= 0)
				{
					continue;
				}
				objLang.setAnchorKey(rs.getString("anchor_key"));
				objLang.setLanguageValue(rs.getString("language_" + languageCode));
				lstLangData.add(objLang);
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

		return lstLangData;
	}

	private String getFileName(String languageId, String clientName)
	{
		String strFileName = "";
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "select " + clientName + " from language where language_code=?";

		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, Long.valueOf(languageId));
			rs = ps.executeQuery();
			if (rs.next())
			{
				strFileName = rs.getString(clientName);
			}
		}
		catch (Exception e)
		{
			logger.error("[Export File] GetFileName Exception:", e);
		}
		finally
		{
			closeResultSet(rs);
			closeStmt(ps);
			closeConn(conn);
		}

		return strFileName;
	}

	// private String escapeAndroid(String val)
	// {
	// if (val == null || val.equals(""))
	// return "";
	//
	// // &����ڵ�һλ
	// if (val.contains("&"))
	// val = val.replaceAll("&", "&amp;");
	// if (val.contains("\""))
	// val = val.replaceAll("\"", "&quot;");
	// if (val.contains("\'"))
	// val = val.replaceAll("\'", "&apos;");
	// if (val.contains(">"))
	// val = val.replaceAll(">", "&gt;");
	// if (val.contains("<"))
	// val = val.replaceAll("<", "&lt;");
	//
	// return val;
	// }

	/**
	 * ����languageData������-�����ݵĲ㼶<Excel>
	 */
	private void exportLanguage(long projectId, String strNowPath)
	{
		PreparedStatement ps = null;
		Connection conn = null;
		CheckModel model = null;
		AllLanguage objLang = null;
		List<AllLanguage> lstData = null;
		List<CheckModel> languageList = new ArrayList<CheckModel>();
ResultSet rs = null;
		try
		{
			List<MenuModel> menuList = getAllMenu(projectId);

			// ƴSQL
			StringBuffer sql = new StringBuffer("SELECT menu_id,anchor_id,anchor_key,client_code");
			for (LanguageBean bean : LM_Util.languageList)
			{
				sql.append(",language_").append(bean.getLanguageCode());
			}
			sql.append(" FROM languageData WHERE project_id=?");

			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, projectId);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				model = new CheckModel();
				model.setMenuid(rs.getInt(1));
				model.setAnchor_id(rs.getString(2));
				model.setAnchor_key(rs.getString(3));
				model.setClientName(LM_Util.map.get(rs.getInt(4)));

				for (MenuModel menu : menuList)
				{
					if (menu.getId() == rs.getInt("menu_id"))
					{
						model.setMenuname(menu.getName());
						model.setMenuid(menu.getId());
						model.setMenuParentId(menu.parentid);
						break;
					}
				}

				lstData = new ArrayList<AllLanguage>();
				for (int i = 0; i < LM_Util.languageList.size(); i++)
				{
					objLang = new AllLanguage();
					int code = LM_Util.languageList.get(i).getLanguageCode();
					objLang.setLanguagekey("language_" + code);
					objLang.setLanguagevalue(rs.getString("language_" + code));
					lstData.add(objLang);
				}
				model.setLstLanguage(lstData);
				languageList.add(model);
			}

			// �������滻Ϊ�㼶����
			for (CheckModel tmp : languageList)
			{
				tmp.setPath(resetPath(tmp.getMenuParentId(), tmp.menuname, menuList));
			}
			// ����ΪEXCEL
			createExcelFile(languageList, strNowPath);

		}
		catch (SQLException e)
		{
			logger.error("[Export File] SQLException:", e);
		}
		catch (Exception e)
		{
			logger.error("[Export File] Exception:", e);
		}
		finally
		{
			closeResultSet(rs);
			closeStmt(ps);
			closeConn(conn);
		}

	}

	/**
	 * ����Excel�ļ�
	 * 
	 * @param list
	 * @param strNowPath
	 */
	private void createExcelFile(List<CheckModel> list, String strNowPath)
	{
		try
		{
			if (list == null || list.size() == 0)
			{
				return;
			}

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();

			// ������
			int col = 4 + LM_Util.languageList.size();

			HSSFRow title_row = sheet.createRow(0);
			HSSFCell title_cell = title_row.createCell(0);
			title_cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			title_cell.setCellValue("�㼶");// �㼶

			// HSSFCell title_cell2 = title_row.createCell(1);
			// title_cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
			// title_cell2.setCellValue("ID");// ID

			HSSFCell title_cell3 = title_row.createCell(1);
			title_cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
			title_cell3.setCellValue("KEY");// KEY

			HSSFCell title_cell4 = title_row.createCell(2);
			title_cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
			title_cell4.setCellValue("�ͻ�������");// �ͻ�������

			for (int k = 0; k < list.get(0).getLstLanguage().size(); k++)
			{
				HSSFCell cell5 = title_row.createCell(3 + k);
				cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell5.setCellValue(list.get(0).getLstLanguage().get(k).getLanguagekey());// ����-����:language_1���޸�
			}

			// ����һ��
			for (int i = 0; i < list.size(); i++)
			{
				HSSFRow row = sheet.createRow(i + 1);
				for (int j = 0; j < col; j++)
				{

					HSSFCell cell = row.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(list.get(i).getPath());// �㼶

					// HSSFCell cell2 = row.createCell(1);
					// cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
					// cell2.setCellValue(list.get(i).getAnchor_id());// ID

					HSSFCell cell3 = row.createCell(1);
					cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell3.setCellValue(list.get(i).getAnchor_key());// KEY

					HSSFCell cell4 = row.createCell(2);
					cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell4.setCellValue(list.get(i).getClientName());// �ͻ�������

					for (int k = 0; k < list.get(i).getLstLanguage().size(); k++)
					{
						HSSFCell cell5 = row.createCell(3 + k);
						cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell5.setCellValue(list.get(i).getLstLanguage().get(k).getLanguagevalue());// �ͻ�������
					}
				}

			}
			// ����һ����Ԫ����д����Ϣ
			FileOutputStream fOut = new FileOutputStream(strNowPath + xlsFile);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		}
		catch (Exception e)
		{
			logger.error("[Export File] createExcelFile Exception:", e);
		}
	}

	/**
	 * ��ȡ��Ŀ�����в˵�
	 * 
	 * @param projectId
	 * @return
	 */
	private List<MenuModel> getAllMenu(long projectId)
	{
		List<MenuModel> lstMenu = new ArrayList<MenuModel>();
		String sql = "SELECT id,name,parentid FROM menu WHERE projectid =? ";
		MenuModel objMenu = null;
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, projectId);
			rs = ps.executeQuery();
			while (rs != null && rs.next())
			{
				objMenu = new MenuModel();
				objMenu.setId(rs.getLong("id"));
				objMenu.setName(rs.getString("name"));
				objMenu.setParentid(rs.getLong("parentid"));
				lstMenu.add(objMenu);
			}
		}
		catch (Exception e)
		{
			logger.error("[Export File] getAllMenu Exception:", e);
		}
		finally
		{
			closeResultSet(rs);
			closeStmt(ps);
			closeConn(conn);
		}
		return lstMenu;
	}

	/**
	 * �㼶������תΪ·��--�ݹ�
	 * 
	 * @param parentId
	 * @param menuName
	 * @param lstMenu
	 * @return
	 */
	private String resetPath(long parentId, String menuName, List<MenuModel> lstMenu)
	{
		if (parentId == -1)
		{
			return menuName + ">";
		}
		else
		{
			for (MenuModel tmp : lstMenu)
			{
				if (parentId == tmp.id)
				{
					return resetPath(tmp.parentid, tmp.name, lstMenu) + menuName;
				}
			}
			return menuName + ">";
		}
	}

	// ����---------------------Start----------------------
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
				rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	// ����--------------------End-----------------------

}
