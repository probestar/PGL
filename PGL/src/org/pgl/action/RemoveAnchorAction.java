package org.pgl.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.User;
import org.pgl.mail.SendMail;
import org.pgl.service.RemoveAnchorService;
import org.pgl.util.DBLoggerUtil;
import org.pgl.util.LM_Util.OperationType;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ɾ��ê�㼰ê���Ӧ��������Ϣ
 * 
 * @author mazhanghui
 * 
 */
public class RemoveAnchorAction extends ActionSupport
{
	private static final long serialVersionUID = -5575055130571277937L;
	private static Log logger = LogFactory.getLog(RemoveAnchorAction.class);
	private static DBLoggerUtil dblog = DBLoggerUtil.getInstance();
	private int projectId;
	private int menuId;
	private String anchorId;
	private String fileName;
	private String message;

	private RemoveAnchorService removeAnchorService = new RemoveAnchorService();

	public String execute() throws Exception
	{
		try
		{
			// �ȿ�ê���Ƿ�������ݿ� ����ɾ������
			int anchorPrimeryKey = removeAnchorService.getAnchorPrimeryKey(projectId, menuId, anchorId);

			if (anchorPrimeryKey != 0)
			{
				// 1.ɾ��ê���
				removeAnchorService.removeAnchor(anchorPrimeryKey);

				// 2. ɾ����ϵӳ���
				removeAnchorService.removeMappingTableData(anchorPrimeryKey);
			}
			// 3. ɾ��ê��Ҫɾ��ê���Ӧ����������
			removeAnchorService.removeLanguageData(projectId, menuId, anchorId);

			message = getText("deleteanchorsuc");//"ɾ��ê��ɹ�...";
			
			// ���ʼ�֪ͨ
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
			String mess = String.format("[%s]ɾ����ê�㣺%s.��ĿID��%s,�˵�ID��%s.", user.getName(), anchorId, projectId, menuId);
			dblog.logger((int)user.getId(), OperationType.DELETE.getValue(), String.format("User %s delete  anchor %s.projecr ID��%s,menu ID��%s.", user.getName(), anchorId, projectId, menuId));
			SendMail sm = new SendMail();
			sm.sendEmail(mess);
		}
		catch (Exception e)
		{
			logger.error(String.format("ɾ��ê���쳣�� AnchorId:%s", anchorId), e);
			e.printStackTrace();
		}

		return SUCCESS;
	}

	// getter setter
	public String getAnchorId()
	{
		return anchorId;
	}

	public void setAnchorId(String anchorId)
	{
		this.anchorId = anchorId;
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

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

}
