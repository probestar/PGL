package org.pgl.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.pgl.db.DataBase;

import com.mysql.jdbc.Blob;

public class FileService
{
	Blob img;

	public String getFileName(int projectId, int menuId)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement("select file_name from backgroundimage where menu_id=" + menuId + " AND project_id=" + projectId);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				return rs.getString(1);
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
				e.printStackTrace();
			}

		}
		return null;
	}

	/**
	 * ���û��ϴ���ͼƬ���
	 * 
	 * @param porjectId
	 * @param menuId
	 * @param fileName
	 * @param file
	 */
	public void saveFile(int projectId, int menuId, String fileName, File file) throws Exception
	{
		Connection con = null;
		PreparedStatement pre = null;

		try
		{
			String tmpName = getFileName(projectId, menuId);
			con = new DataBase().getConnect();

			FileInputStream fis = new FileInputStream(file);

			if (tmpName == null)
			{
				System.out.println("insert>backgroundimage>" + fileName);
				pre = con.prepareStatement("insert into backgroundimage (project_id,menu_id,file_name,file_content) VALUES (?,?,?,?)");

				pre.setInt(1, projectId);
				pre.setInt(2, menuId);
				pre.setString(3, fileName);
				pre.setBinaryStream(4, fis, fis.available());
			}
			else
			{
				System.out.println("update>backgroundimage>" + fileName);
				pre = con.prepareStatement("update backgroundimage set file_content=? where project_id=? AND menu_id=?");

				pre.setBinaryStream(1, fis, fis.available());
				pre.setInt(2, projectId);
				pre.setInt(3, menuId);
			}

			pre.executeUpdate();
			pre.close();
			fis.close();

		}
		// �˴�catch��Ҫ�ϲ�
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				if (con != null)
					con.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

		}
	}

	
	
	private InputStream getBackGroundImg(int projectId, int menuId, String fileDir, String fileName){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		InputStream fis = null;
		try
		{
			conn = new DataBase().getConnect();

			String sql = "select file_content from backgroundimage where menu_id=? and project_id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, menuId);
			ps.setInt(2, projectId);

			rs = ps.executeQuery();

			while (rs.next())
			{
				fis = rs.getBinaryStream(1);
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
		return fis;
	}
	/**
	 * �����ݿ���ͼƬת�浽��ĿĿ¼��
	 * 
	 * @param porjectId
	 * @param menuId
	 */
	public void readFile(int projectId, int menuId, String fileDir, String fileName)
	{
		
		InputStream fis= null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try
		{
			
				fis = getBackGroundImg(projectId,menuId,fileDir,fileName);
				File file = new File(fileDir + "/" + fileName);
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos);
				byte[] buff = new byte[1024];
				int i = 0;
				while ((i = fis.read(buff)) > 0)
				{
					bos.write(buff);
				}

				
			
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
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
				bos.close();
				fos.close();
				fis.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/*
	 * ɾ��ͼƬ
	 */
	public void removePicture(int projectId, int menuId, String filePath)
	{
		// ɾ��Ŀ¼�µ�ͼƬ
		File file = new File(filePath);
		if (file.exists())
			file.delete();
		// ɾ����¼
		String sql = "DELETE FROM backgroundimage WHERE menu_id=? AND project_id=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try
		{
			conn = new DataBase().getConnect();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, menuId);
			ps.setInt(2, projectId);
			ps.executeUpdate();
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
				if (conn != null)
					conn.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

}
