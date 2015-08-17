package org.pgl.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.AnchorBean;
import org.pgl.Model.User;
import org.pgl.mail.SendMail;
import org.pgl.service.CopyWriterService;
import org.pgl.service.PreviewAnchorService;
import org.pgl.util.DBLoggerUtil;
import org.pgl.util.LM_Util.OperationType;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ɾ���İ�
 * 
 * @author mazhanghui
 * 
 */
public class RemoveCopyWriterAction extends ActionSupport
{

	private static final long serialVersionUID = 412604839031270304L;
	private static Log logger = LogFactory.getLog(RemoveCopyWriterAction.class);
	private static DBLoggerUtil dblog = DBLoggerUtil.getInstance();
	private int projectId;
	private int menuId;
	private int cpId;
	private String fileName;
	private String message;
	private int copyWriterCount; // ͨ���İ�������̬����ҳ��߶�
	private String cpName;// �İ�����
	private List<AnchorBean> anchorList = new ArrayList<AnchorBean>();
	private Map<Integer, String> copyWriterMap = new HashMap<Integer, String>();

	private PreviewAnchorService previewService = new PreviewAnchorService();
	private CopyWriterService copyService = new CopyWriterService();
	private CopyWriterService cpService = new CopyWriterService();

	public String execute()
	{
		try
		{
			boolean flag = cpService.removeCopyWriter(cpId);

			int clientTypeCode = 0;
			// ��ȡê����Ϣ
			anchorList = previewService.getAnchorInfo(projectId, menuId, 1, clientTypeCode);

			// �����İ���Ϣ
			copyWriterMap = copyService.getCopyWriter(projectId, menuId, clientTypeCode);
			if (copyWriterMap != null && copyWriterMap.size() > 0)
			{
				copyWriterCount = copyWriterMap.size();
			}
			if (flag)
			{
				message = getText("copywritedeletesuc");//"ɾ���İ��ɹ�...";

				// ���ʼ�֪ͨ
				User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
				String mess = String.format("[%s]ɾ�����İ���%s.��ĿID��%s,�˵�ID��%s.", user.getName(), new String(cpName.getBytes("ISO-8859-1"), "UTF-8"), projectId, menuId);
				dblog.logger((int)user.getId(), OperationType.DELETE.getValue(), String.format("User %s delete  copyWriter %s.projecr ID��%s,menu ID��%s.", user.getName(), new String(cpName.getBytes("ISO-8859-1"), "UTF-8"), projectId, menuId));
				SendMail sm = new SendMail();
				sm.sendEmail(mess);

				return SUCCESS;
			}
			message = getText("copywritedeletefail");//"ɾ���İ�ʧ��...";
		}
		catch (Exception e)
		{
			logger.error(String.format("ɾ���İ��쳣���İ�ID:%s", cpId), e);
			e.printStackTrace();
		}
		return ERROR;
	}

	public int getCpId()
	{
		return cpId;
	}

	public void setCpId(int cpId)
	{
		this.cpId = cpId;
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

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getCpName()
	{
		return cpName;
	}

	public void setCpName(String cpName)
	{
		this.cpName = cpName;
	}

}
