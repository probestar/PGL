package org.pgl.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.ClientTypeBean;
import org.pgl.Model.User;
import org.pgl.service.ManageClientService;
import org.pgl.util.DBLoggerUtil;
import org.pgl.util.LM_Util;
import org.pgl.util.LM_Util.OperationType;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ����ͻ��˰汾
 * 
 * @author mazhanghui
 * 
 */
public class ManageClientAction extends ActionSupport
{
	private static final long serialVersionUID = 3150387026123956702L;
	private static Log logger = LogFactory.getLog(ManageClientAction.class);
	private static DBLoggerUtil dblog = DBLoggerUtil.getInstance();
	private int clientId;
	private String clientTypeName;
	private int clientTypeCode;
	private int mixtrueType;// �ͻ��������Ƿ�ΪA&B���� 0�� 1��
	private List<ClientTypeBean> clientList = new ArrayList<ClientTypeBean>();
	private List<ClientTypeBean> noMixtrueclientList = new ArrayList<ClientTypeBean>();

	HttpServletResponse response;
	ManageClientService service = new ManageClientService();

	/*
	 * ��ȡ���еĿͻ�����Ϣ�б� (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	public String execute() throws Exception
	{
		try
		{
			clientList = service.getAllClient();
		}
		catch (Exception e)
		{
			logger.error("��ȡ���еĿͻ�����Ϣ�б�-�쳣", e);
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/*
	 * ��ӿͻ�����Ϣ
	 */
	public String addClient() throws Exception
	{
		try
		{
			// ���⺺������
			clientTypeName = new String(clientTypeName.getBytes("ISO-8859-1"), "UTF-8");

			logger.info(String.format("���һ���µĿͻ���, ����:%s", clientTypeName));

			service.saveClient(clientTypeName, mixtrueType);
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
			if (user == null)
				return Action.LOGIN; 
			dblog.logger((int)user.getId(), OperationType.INSERT.getValue(), String.format("User %s add a new client named:%s", user.getName(),clientTypeName));
			clientList = service.getAllClient();
			noMixtrueclientList = service.getClientByType(0);
			syncClientUtil();
			syncNoMixtrueClient();
		}
		catch (Exception e)
		{
			logger.error(String.format("��ӿͻ���-�쳣, Name:%s", clientTypeName), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * ɾ���ͻ�����Ϣ
	 */
	public String deleteClient() throws Exception
	{
		try
		{
			logger.info(String.format("ɾ���ͻ���, clientId:%s", clientId));
			service.deleteClient(clientId, clientTypeCode);
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
			if (user == null)
				return Action.LOGIN; 
			dblog.logger((int)user.getId(), OperationType.DELETE.getValue(), String.format("User %s delete a client and client id:%s", user.getName(),clientId));
			clientList = service.getAllClient();
			noMixtrueclientList = service.getClientByType(0);
			syncClientUtil();
			syncNoMixtrueClient();
		}
		catch (Exception e)
		{
			logger.error(String.format("ɾ���ͻ����쳣, clientId:%s", clientId), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * �޸Ŀͻ�����Ϣ
	 */
	public String updateClient() throws Exception
	{
		try
		{
			// ���⺺������
			clientTypeName = new String(clientTypeName.getBytes("ISO-8859-1"), "UTF-8");

			logger.info(String.format("Update Client, clientId:%s clientTypeName:%s", clientId, clientTypeName));

			service.updateClient(clientId, clientTypeName);
			
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
			if (user == null)
				return Action.LOGIN; 
			dblog.logger((int)user.getId(), OperationType.UPDATE.getValue(), String.format("User %s update a client and client id:%s,client name: %s", user.getName(),clientId,clientTypeName));
			
			clientList = service.getAllClient();
			noMixtrueclientList = service.getClientByType(0);
			syncClientUtil();
			syncNoMixtrueClient();
		}
		catch (Exception e)
		{
			logger.error(String.format("�޸Ŀͻ����쳣, clientId:%s", clientId), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/*
	 * AJAXУ��ͻ��������ظ���
	 */
	public String checkClientName() throws Exception
	{
		try
		{
			boolean flag = service.checkClientName(clientTypeName);

			// ������Ϣ
			response = ServletActionContext.getResponse();
			PrintWriter out = response.getWriter();
			out.println(flag);
			out.flush();
			out.close();

			if (flag)
			{
				return ERROR;// �ظ���
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/*
	 * ͬ�����³������еĿͻ�����Ϣ--���пͻ�������Map
	 */
	private void syncClientUtil()
	{
		LM_Util.map.clear();
		for (int i = 0; i < clientList.size(); i++)
		{
			LM_Util.map.put(clientList.get(i).getClientTypeCode(), clientList.get(i).getClientTypeName());
		}
	}

	/*
	 * ͬ�����³������еĿͻ�����Ϣ--���пͻ�������Map
	 */
	private void syncNoMixtrueClient()
	{
		LM_Util.noMixtureMap.clear();
		for (int i = 0; i < clientList.size(); i++)
		{
			LM_Util.noMixtureMap.put(clientList.get(i).getClientTypeCode(), clientList.get(i).getClientTypeName());
		}
	}

	// getter setter
	public List<ClientTypeBean> getClientList()
	{
		return clientList;
	}

	public void setClientList(List<ClientTypeBean> clientList)
	{
		this.clientList = clientList;
	}

	public int getClientId()
	{
		return clientId;
	}

	public void setClientId(int clientId)
	{
		this.clientId = clientId;
	}

	public String getClientTypeName()
	{
		return clientTypeName;
	}

	public void setClientTypeName(String clientTypeName)
	{
		this.clientTypeName = clientTypeName;
	}

	public int getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(int clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
	}

	public int getMixtrueType()
	{
		return mixtrueType;
	}

	public void setMixtrueType(int mixtrueType)
	{
		this.mixtrueType = mixtrueType;
	}

	public List<ClientTypeBean> getNoMixtrueclientList()
	{
		return noMixtrueclientList;
	}

	public void setNoMixtrueclientList(List<ClientTypeBean> noMixtrueclientList)
	{
		this.noMixtrueclientList = noMixtrueclientList;
	}

}
