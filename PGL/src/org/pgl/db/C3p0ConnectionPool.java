package org.pgl.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class C3p0ConnectionPool
{
	private static Log logger = LogFactory.getLog(C3p0ConnectionPool.class);
	private static ComboPooledDataSource dataSource;
	private static C3p0ConnectionPool instance;
	public static Statement stmt = null;
	public static ResultSet rs = null;

	public static String userName;
	public static String password;
	public static String jdbcURL;
	public static String driverClass;
	public static int initialPoolSize;
	public static int maxPoolSize;
	public static int minPoolSize;
	public static int maxIdleTime;
	public static int retryAttempts;
	public static int checkTimeout;
	public static int acquireIncrement;
	public static int acquireRetryDelay;
	public static boolean testConnectionOnCheckin;
	public static int	idleConnectionTestPeriod;
	public static String automaticTestTable;
	public static int unreturnedConnectionTimeout;
	public static int maxIdleTimeExcessConnections;
	public static int maxConnectionAge;
	public static C3p0ConnectionPool getInstance()
	{
		if (instance == null)
		{
			instance = new C3p0ConnectionPool();
		}
		return instance;
	}

	public static void init()
	{
		try
		{
			dataSource = new ComboPooledDataSource();
			dataSource.setUser(userName);
			dataSource.setPassword(password);
			dataSource.setJdbcUrl(jdbcURL);
			dataSource.setDriverClass(driverClass);
			dataSource.setInitialPoolSize(initialPoolSize);
			dataSource.setMinPoolSize(minPoolSize);
			dataSource.setMaxPoolSize(maxPoolSize);
			dataSource.setMaxStatements(50);
			dataSource.setMaxIdleTime(maxIdleTime);
			dataSource.setAcquireRetryAttempts(retryAttempts);
			dataSource.setAcquireIncrement(acquireIncrement);
			dataSource.setCheckoutTimeout(checkTimeout);
			dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
			dataSource.setAcquireRetryDelay(acquireRetryDelay);
			dataSource.setTestConnectionOnCheckin(testConnectionOnCheckin);
			dataSource.setAutomaticTestTable(automaticTestTable);
			dataSource.setUnreturnedConnectionTimeout(unreturnedConnectionTimeout);
			dataSource.setMaxIdleTimeExcessConnections(maxIdleTimeExcessConnections);
			dataSource.setMaxConnectionAge(maxConnectionAge);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("C3p0ConnectionPool Run static block Exception", e);
		}
	}

	public static synchronized Connection getConnection()
	{
		try
		{
			return dataSource.getConnection();
		}
		catch (SQLException e)
		{
			logger.error("ConnectionPool Get Connection Exception:", e);
			e.printStackTrace();
			return null;
		}
	}

	public static ResultSet executeQuery(String sql)
	{
		try
		{
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(sql);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}
	
	protected void finalize() throws Throwable {
        DataSources.destroy(dataSource);
        super.finalize();
  }
}
