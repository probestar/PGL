package org.pgl.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.CheckModel;
import org.pgl.Model.DBLogger;
import org.pgl.Model.User;
import org.pgl.service.OperationService;
import org.pgl.service.UserManageService;
import org.pgl.util.LM_Util;

import com.opensymphony.xwork2.ActionSupport;

public class OperationLogAction extends ActionSupport
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1747722538539351258L;
	private static Log logger = LogFactory.getLog(OperationLogAction.class);
	private HttpServletRequest request;
	private List<CheckModel> lstCheck = new ArrayList<CheckModel>();
	private List<DBLogger> listLogger = new ArrayList<DBLogger>();
	private HashMap userMap = new LinkedHashMap();
	private UserManageService ums = new UserManageService();

	private String searchkey;
	private int opertype;
	private int searchuserid;
	
	private String operdate;
	
	private int startPage;// ��ҳ-Ҫ��ȡ��һҳ
	private int totlePage;// ��ҳ��
	private int currentPage;// ��ǰҳ

	/*
	 * ��ȡȫ���б���Ϣ (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	
	
	public String execute() throws Exception
	{
		request = ServletActionContext.getRequest();
		OperationService objService = new OperationService();
		
		// Ϊ��ҳ��ѯ������׼��
		int count = objService.getLogCount(0, 0,"", "");
		startPage = 1;
		currentPage = 1;
		totlePage = count % LM_Util.SHOW_COUNT == 0 ? count / LM_Util.SHOW_COUNT : count / LM_Util.SHOW_COUNT + 1;
		if (totlePage == 0)
			totlePage = 1;
		listLogger = objService.getDbLogger(0, 0, "","", 0);//
		List<User> users = ums.getAllUser();
		if(users!=null && users.size()>0){
			for (User user : users)
			{
				userMap.put(user.getId(), user.getName());
			}
		}
		
		request.setAttribute("usermap", userMap);
		request.setAttribute("key", "");
		request.setAttribute("searchkey", "");
		request.setAttribute("opertype", "");
		request.setAttribute("searchuserid", searchuserid);
		request.setAttribute("operdate", "");
		return SUCCESS;
	}

	/*
	 * ��ǰ��Ŀ�¸���searchKey��ѯ����
	 */
	public String searchOperLogger()
	{
		try
		{
			OperationService objService = new OperationService();
			if (searchkey == null)
				searchkey = "";
			if(operdate == null)
				operdate = "";
			searchkey = new String(searchkey.getBytes("ISO-8859-1"), "UTF-8");
			int count = objService.getLogCount(searchuserid, opertype, searchkey,operdate);
			startPage = 1;
			currentPage = 1;
			totlePage = count % LM_Util.SHOW_COUNT == 0 ? count / LM_Util.SHOW_COUNT : count / LM_Util.SHOW_COUNT + 1;
			if (totlePage == 0)
				totlePage = 1;
			listLogger = objService.getDbLogger(searchuserid, opertype, searchkey,operdate, 0);//
			request = ServletActionContext.getRequest();
			request.setAttribute("key", searchkey);
			request.setAttribute("opertype", opertype);
			request.setAttribute("searchuserid", searchuserid);
			request.setAttribute("operdate", operdate);
			List<User> users = ums.getAllUser();
			if(users!=null && users.size()>0){
				for (User user : users)
				{
					userMap.put(user.getId(), user.getName());
				}
			}
			
			request.setAttribute("usermap", userMap);
		}
		catch (UnsupportedEncodingException e)
		{
			return ERROR;
		}
		return SUCCESS;
	}

	/*
	 * ��ҳ
	 */
	public String getOperListPagging() throws Exception
	{
		try
		{
			request = ServletActionContext.getRequest();
			if (searchkey != null && !searchkey.equals("null"))
			{
				searchkey = new String(searchkey.getBytes("ISO-8859-1"), "UTF-8");
				request.getSession().setAttribute("searchkey", searchkey);
			}
			else
			{
				if (request.getSession().getAttribute("searchkey") != null)
				{
					searchkey = String.valueOf(request.getSession().getAttribute("searchkey"));
				}
			}

			
			OperationService objService = new OperationService();

			int startCount = (startPage - 1) * LM_Util.SHOW_COUNT;
			currentPage = startPage;

			listLogger = objService.getDbLogger(searchuserid, opertype, searchkey,operdate, startCount);
			List<User> users = ums.getAllUser();
			if(users!=null && users.size()>0){
				for (User user : users)
				{
					userMap.put(user.getId(), user.getName());
				}
			}
			
			request.setAttribute("usermap", userMap);
			request.setAttribute("key", searchkey);
			request.setAttribute("opertype", opertype);
			request.setAttribute("searchuserid", searchuserid);
			request.setAttribute("operdate", operdate);
			request.setAttribute("searchkey", searchkey);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}

	// GETTER SETTER
	
	

	public List<DBLogger> getListLogger()
	{
		return listLogger;
	}

	public void setListLogger(List<DBLogger> listLogger)
	{
		this.listLogger = listLogger;
	}



	public List<CheckModel> getLstCheck()
	{
		return lstCheck;
	}

	public void setLstCheck(List<CheckModel> lstCheck)
	{
		this.lstCheck = lstCheck;
	}

	


	
	public String getSearchkey()
	{
		return searchkey;
	}

	public void setSearchkey(String searchkey)
	{
		this.searchkey = searchkey;
	}

	public int getStartPage()
	{
		return startPage;
	}

	public void setStartPage(int startPage)
	{
		this.startPage = startPage;
	}

	public int getTotlePage()
	{
		return totlePage;
	}

	public void setTotlePage(int totlePage)
	{
		this.totlePage = totlePage;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public int getOpertype()
	{
		return opertype;
	}

	public void setOpertype(int opertype)
	{
		this.opertype = opertype;
	}

	public int getSearchuserid()
	{
		return searchuserid;
	}

	public void setSearchuserid(int searchuserid)
	{
		this.searchuserid = searchuserid;
	}

	public String getOperdate()
	{
		return operdate;
	}

	public void setOperdate(String operdate)
	{
		this.operdate = operdate;
	}
	

}
