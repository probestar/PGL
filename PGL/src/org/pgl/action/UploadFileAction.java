package org.pgl.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.AnchorBean;
import org.pgl.Model.User;
import org.pgl.service.CopyWriterService;
import org.pgl.service.FileService;
import org.pgl.service.PreviewAnchorService;
import org.pgl.util.DBLoggerUtil;
import org.pgl.util.LM_Util.OperationType;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * �ϴ�����ͼƬ
 * 
 * @author mazhanghui
 * 
 */
public class UploadFileAction extends ActionSupport
{
	private static final long serialVersionUID = -4307089805025414126L;
	private static Log logger = LogFactory.getLog(UploadFileAction.class);
	private static DBLoggerUtil dblog = DBLoggerUtil.getInstance();
	private static final int FILE_SIZE = 16 * 1024;
	private File upFile;
	private String upFileContentType;
	private String upFileFileName;// �� File upFile��Ӧ ����fileName���ܻ�ȡ�ϴ��ļ����ļ���
	private String title;
	private int projectId;
	private int menuId;
	private String fileName;
	private double time;
	private String message;// ��Ϣ��ʾ
	private HttpServletRequest request;
	private int copyWriterCount; // ͨ���İ�������̬����ҳ��߶�
	private List<AnchorBean> anchorList = new ArrayList<AnchorBean>();
	private Map<Integer, String> copyWriterMap = new HashMap<Integer, String>();

	private FileService fileService = new FileService();
	private PreviewAnchorService previewService = new PreviewAnchorService();
	private CopyWriterService copyService = new CopyWriterService();

	/*
	 * �ϴ�ͼƬ (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception
	{
		request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute("loginuser");
		if (user == null)
			return Action.LOGIN;
		try
		{
			fileName = projectId + "_" + menuId + upFileFileName.substring(upFileFileName.lastIndexOf("."));

			logger.info(String.format("�ϴ�����ͼƬ,ͼƬ����:%s", fileName));

			// �ϴ�����Ƭ����Ŀһ��
			String filePath = ServletActionContext.getServletContext().getRealPath("images") + "/" + fileName;

			File targetFile = new File(filePath);
			upLoadFile(upFile, targetFile);

			// ����ͼ���һ��
			fileService.saveFile(projectId, menuId, fileName, upFile);
			dblog.logger((int)user.getId(), OperationType.INSERT.getValue(), String.format("User %s add background image projectId:%s, menuId:%s, fileName:%s", user.getName(),projectId, menuId, fileName));
			time = Math.random();

			anchorList = previewService.getAnchorInfo(projectId, menuId, 1, 0);

			
			copyWriterMap = copyService.getCopyWriter(projectId, menuId, 0);
			if (copyWriterMap != null && copyWriterMap.size() > 0)
			{
				copyWriterCount = copyWriterMap.size();
			}

			request.setAttribute("copyWriterCount", copyWriterCount);
			request.setAttribute("anchorList", anchorList);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			logger.error(String.format("�ϴ�����ͼƬ,ͼƬ����:%s,ͼƬ���󳬹�10M", fileName), e);
			message = getText("uploadfiletoobig");//"�ϴ��ļ�����";
			return ERROR;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("�ϴ�����ͼƬʧ��!", e);
			message = getText("uploadfilefail");
			return ERROR;
		}

		return SUCCESS;
	}

	private void upLoadFile(File source, File target)
	{
		InputStream in = null;
		OutputStream out = null;
		try
		{
			// �ò˵��±���ͼƬ�������滻Ϊ�µ�
			if (target.exists())
				target.delete();

			in = new BufferedInputStream(new FileInputStream(source), FILE_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(target), FILE_SIZE);
			byte[] image = new byte[FILE_SIZE];
			while (in.read(image) > 0)
			{
				out.write(image);
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				in.close();
				out.close();
			}
			catch (IOException ex)
			{
			}
		}
	}

	/*
	 * ɾ��ͼƬ
	 */
	public String deletePic()
	{
		try
		{
			logger.info("ɾ��ͼƬ..");
			String fileDir = ServletActionContext.getServletContext().getRealPath("images");
			fileService.removePicture(projectId, menuId, fileDir + "/" + fileName);

			anchorList = previewService.getAnchorInfo(projectId, menuId, 1, 0);
			copyWriterMap = copyService.getCopyWriter(projectId, menuId, 0);

			if (copyWriterMap != null && copyWriterMap.size() > 0)
			{
				copyWriterCount = copyWriterMap.size();
			}
			if (fileName == "")
				fileName = null;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("ɾ��ͼƬʧ��..", e);
			message = getText("deleteimgfail");//"ɾ��ͼƬʧ��";
			return ERROR;
		}
		return SUCCESS;
	}

	// getter setter
	public File getUpFile()
	{
		return upFile;
	}

	public void setUpFile(File upFile)
	{
		this.upFile = upFile;
	}

	public String getUpFileContentType()
	{
		return upFileContentType;
	}

	public void setUpFileContentType(String upFileContentType)
	{
		this.upFileContentType = upFileContentType;
	}

	public String getUpFileFileName()
	{
		return upFileFileName;
	}

	public void setUpFileFileName(String upFileFileName)
	{
		this.upFileFileName = upFileFileName;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getProjectId()
	{
		return projectId;
	}

	public void setProjectId(int projectId)
	{
		this.projectId = projectId;
	}

	public int getMenuId()
	{
		return menuId;
	}

	public void setMenuId(int menuId)
	{
		this.menuId = menuId;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public double getTime()
	{
		return time;
	}

	public void setTime(double time)
	{
		this.time = time;
	}

	public int getCopyWriterCount()
	{
		return copyWriterCount;
	}

	public void setCopyWriterCount(int copyWriterCount)
	{
		this.copyWriterCount = copyWriterCount;
	}

	public List<AnchorBean> getAnchorList()
	{
		return anchorList;
	}

	public void setAnchorList(List<AnchorBean> anchorList)
	{
		this.anchorList = anchorList;
	}

	public Map<Integer, String> getCopyWriterMap()
	{
		return copyWriterMap;
	}

	public void setCopyWriterMap(Map<Integer, String> copyWriterMap)
	{
		this.copyWriterMap = copyWriterMap;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}
