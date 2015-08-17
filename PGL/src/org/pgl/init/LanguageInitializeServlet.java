package org.pgl.init;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.pgl.Model.LanguageBean;
import org.pgl.db.C3p0ConnectionPool;
import org.pgl.db.DataBase;
import org.pgl.service.DownloadService;
import org.pgl.util.LM_Util;

public class LanguageInitializeServlet extends HttpServlet
{
	private static final long serialVersionUID = 4459008762305580283L;

	public void init() throws ServletException
	{

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DataBase db = null;
		try
		{
			System.out.println("-------------initialize---------------");
			String url = this.getClass().getResource("").getPath().replaceAll("%20", " ");
			// String path = url.substring(0, url.indexOf("WEB-INF")) +
			// "WEB-INF/config.properties";
			// Properties config = new Properties();
			// config.load(new FileInputStream(path));
			// DataBase.jdbcUrl = config.getProperty("url");
			// DataBase.username = config.getProperty("username");
			// DataBase.password = config.getProperty("password");

			String path = url.substring(0, url.indexOf("WEB-INF")) + "WEB-INF/c3p0.properties";
			Properties config = new Properties();

			config.load(new FileInputStream(path));
			DownloadService.filepath = config.getProperty("filepath");
			C3p0ConnectionPool.userName = config.getProperty("username");
			C3p0ConnectionPool.password = config.getProperty("password");
			C3p0ConnectionPool.jdbcURL = config.getProperty("url");
			C3p0ConnectionPool.driverClass = config.getProperty("driver");
			C3p0ConnectionPool.initialPoolSize = Integer.valueOf(config.getProperty("initialPoolSize"));
			C3p0ConnectionPool.maxPoolSize = Integer.valueOf(config.getProperty("maxPoolSize"));
			C3p0ConnectionPool.minPoolSize = Integer.valueOf(config.getProperty("minPoolSize"));
			C3p0ConnectionPool.maxIdleTime = Integer.valueOf(config.getProperty("maxIdleTime"));
			C3p0ConnectionPool.retryAttempts = Integer.valueOf(config.getProperty("acquireRetryAttempts"));
			C3p0ConnectionPool.checkTimeout = Integer.valueOf(config.getProperty("checkoutTimeout"));
			C3p0ConnectionPool.acquireIncrement = Integer.valueOf(config.getProperty("acquireIncrement"));
			C3p0ConnectionPool.acquireRetryDelay = Integer.valueOf(config.getProperty("acquireRetryDelay"));
			C3p0ConnectionPool.testConnectionOnCheckin = Boolean.valueOf(config.getProperty("testConnectionOnCheckin"));
			C3p0ConnectionPool.automaticTestTable = config.getProperty("automaticTestTable");
			C3p0ConnectionPool.idleConnectionTestPeriod = Integer.valueOf(config.getProperty("idleConnectionTestPeriod"));
			C3p0ConnectionPool.unreturnedConnectionTimeout = Integer.valueOf(config.getProperty("unreturnedConnectionTimeout"));
			C3p0ConnectionPool.maxIdleTimeExcessConnections = Integer.valueOf(config.getProperty("maxIdleTimeExcessConnections"));
			C3p0ConnectionPool.maxConnectionAge =  Integer.valueOf(config.getProperty("maxConnectionAge"));
			C3p0ConnectionPool.init();

			try
			{
				db = new DataBase();
				conn = db.getConnect();
				// ��ʼ���ͻ�������
				ps = conn.prepareStatement("SELECT client_code,client_type FROM clienttype");
				rs = ps.executeQuery();// db.executeQuery("SELECT client_code,client_type FROM clienttype");
				while (rs.next())
				{
					LM_Util.map.put(rs.getInt(1), rs.getString(2));
				}
				ps = conn.prepareStatement("SELECT client_code,client_type FROM clienttype WHERE mixtrue_type=0");
				rs = ps.executeQuery();// db.executeQuery("SELECT client_code,client_type FROM clienttype WHERE mixtrue_type=0");
				while (rs.next())
				{
					LM_Util.noMixtureMap.put(rs.getInt(1), rs.getString(2));
				}

				// ��ʼ��������
				ps = conn.prepareStatement("SELECT language_code,language_name FROM language");
				rs = ps.executeQuery();// db.executeQuery("SELECT language_code,language_name FROM language");

				LanguageBean bean = new LanguageBean();
				while (rs.next())
				{
					bean = new LanguageBean();
					bean.setLanguageCode(rs.getInt(1));
					bean.setLanguageName(rs.getString(2));
					LM_Util.languageList.add(bean);
				}

				// ��ʼ���ʼ�����������
				ps = conn.prepareStatement("SELECT email FROM email WHERE state=1");
				rs = ps.executeQuery();// db.executeQuery("SELECT email FROM email WHERE state=1");
				while (rs != null && rs.next())
				{
					LM_Util.mailReceiverList.add(rs.getString(1));
				}

				System.out.println("Num of clientType��" + LM_Util.map.size());
				System.out.println("The number of non-hybrid types of clients:" + LM_Util.noMixtureMap.size());
				System.out.println("The number of language��" + LM_Util.languageList.size());
				System.out.println("Email:" + LM_Util.mailReceiverList.size());

				System.out.println("-------------initialize   complele!!!---------------");

			}
			catch (Exception e)
			{
				// TODO: handle exception
			}
			finally
			{
				try
				{
					if (rs != null)
						rs.close();
					if (ps != null)
						ps.close();
//					if (conn != null)
//						conn.close();
					// db.connClose();
				}
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
