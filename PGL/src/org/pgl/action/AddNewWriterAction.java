package org.pgl.action;

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
import org.pgl.service.PreviewAnchorService;
import org.pgl.util.DBLoggerUtil;
import org.pgl.util.LM_Util.OperationType;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ������İ�--�ڶ�����
 * 
 * @author mazhanghui
 * 
 */
public class AddNewWriterAction extends ActionSupport
{
	private static final long serialVersionUID = -9178545208626783041L;
	private static Log logger = LogFactory.getLog(AddNewWriterAction.class);
	private static DBLoggerUtil dblog = DBLoggerUtil.getInstance();
	private int projectId;
	private int menuId;
	private String copyWriterName;
	private String message; // ��ʾ��Ϣ
	private String fileName;// ͼƬ����
	private int copyWriterCount; // ͨ���İ�������̬����ҳ��߶�

	private HttpServletRequest request;

	private List<AnchorBean> anchorList = new ArrayList<AnchorBean>();
	private Map<Integer, String> copyWriterMap = new HashMap<Integer, String>();

	private PreviewAnchorService previewService = new PreviewAnchorService();
	private CopyWriterService copyService = new CopyWriterService();

	/*
	 * ������İ� Ĭ��clientCodeΪ0
	 */
	public String execute() throws Exception
	{
		try
		{
			request = ServletActionContext.getRequest();

			// ���⺺������
			copyWriterName = new String(copyWriterName.getBytes("ISO-8859-1"), "UTF-8");
			// ����
			boolean _r = copyService.saveNewWriter(projectId, menuId, copyWriterName, 0);
			//��Ӳ�����־
			if(_r){
				User user = (User) request.getSession().getAttribute("loginuser");
				dblog.logger((int)user.getId(), OperationType.INSERT.getValue(), String.format("User:%s Add CopyWriter, projectId:%s,menuId:%s,cpName:%s",user.getName(), projectId, menuId, copyWriterName));
			}
			anchorList = previewService.getAnchorInfo(projectId, menuId, 1, 0);
			copyWriterMap = copyService.getCopyWriter(projectId, menuId, 0);

			if (copyWriterMap != null && copyWriterMap.size() > 0)
			{
				copyWriterCount = copyWriterMap.size();
			}

			request.setAttribute("copyWriterCount", copyWriterCount);
			request.setAttribute("anchorList", anchorList);
			message = getText("addcopywritesuc");// "����İ��ɹ�...";
		}
		catch (Exception e)
		{
			logger.error(String.format("Add CopyWriter projectId:%s,menuId:%s,cpName:%s", projectId, menuId, copyWriterName), e);
			e.printStackTrace();
			message = getText("addcopywritefail");// "����İ�ʧ��";
			return ERROR;
		}

		return SUCCESS;
	}

	// getter setter
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

	public String getCopyWriterName()
	{
		return copyWriterName;
	}

	public void setCopyWriterName(String copyWriterName)
	{
		this.copyWriterName = copyWriterName;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
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

	
}
