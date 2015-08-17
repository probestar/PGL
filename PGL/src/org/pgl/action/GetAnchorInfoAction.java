package org.pgl.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.Model.AnchorInfoBean;
import org.pgl.Model.LanguageBean;
import org.pgl.Model.User;
import org.pgl.mail.SendMail;
import org.pgl.service.GetAllLanguageService;
import org.pgl.util.DBLoggerUtil;
import org.pgl.util.LM_Util;
import org.pgl.util.LM_Util.OperationType;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class GetAnchorInfoAction extends ActionSupport
{
	private static final long serialVersionUID = 4059493437732499967L;
	private static Log logger = LogFactory.getLog(GetAnchorInfoAction.class);
	private static DBLoggerUtil dblog = DBLoggerUtil.getInstance();
	private int projectId;
	private int menuId;
	private String anchorId;
	private int anchor_x;
	private int anchor_y;
	private int anchor_width;
	private int anchor_height;
	private String fileName;
	private int copyWriterCount;
	private int anchorTableId;// ê�������ݿ���е�����id
	private String lanData;// ���еķ�NEW Tabҳչʾ���ݴ�
	private String newTabData;// NEW TAB����
	private String message;// ��ʾ��Ϣ
	private int quote;// �Ƿ���������KEY��0������ ,1����
	private int quoteLanguageDataId;// ���õ�languageDataId
	private String anchorKey;// �������õ�
	private int clientTypeCode;// �������õ�
	private int languageDataId;// ɾ��Tabʱ�õģ���idɾ��
	private int tabCoount;// ɾ��Tabʱ����tab�ĸ��������Ƿ�ɾ����ê��tabCoount=2��ʱ��ɾ��ê��
	private int newTabClientCode;// ����newTabʱ�Ŀͻ�������

	// ���û���NEW TAB������ֵ����ʾ������������
	private List<AnchorInfoBean> list;

	// �����б�--���Է��ڳ���������������͵�ʱ�����һ��
	private List<AnchorInfoBean> anchorInfoBeanList = new ArrayList<AnchorInfoBean>();
	private GetAllLanguageService languageService = new GetAllLanguageService();

	// ����չʾ�¼�Tab<NEW Tab>ҳ������������
	private List<LanguageBean> newLanguageList = new ArrayList<LanguageBean>();

	/**
	 * ˫��ê���ȡ����KEY��Ϣ
	 */
	public String execute() throws Exception
	{
		try
		{
			anchorTableId = languageService.getAnchorId(projectId, menuId, anchorId);// anchorTableId=0
																						// ��ʾ����ê�����ݿ��޶�Ӧ��¼
			LanguageBean bean;
			for (LanguageBean tmp : LM_Util.languageList)
			{
				bean = new LanguageBean();
				bean.setLanguageCode(tmp.getLanguageCode());
				bean.setLanguageName(tmp.getLanguageName());
				newLanguageList.add(bean);
			}

			if (anchorTableId != 0)
			{
				anchorInfoBeanList = languageService.getLanguageInfoByAnchorTableId(anchorTableId);
			}
		}
		catch (Exception e)
		{
			logger.error(String.format("˫����ȡ��Ϣ�쳣��ê��������%s,ê��ID:%s,��ê����KEY������%s", anchorTableId, anchorId, anchorInfoBeanList.size()), e);
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * AJAX ����������KEYǰ�Ĳ�ѯKEY��Ϣ
	 */
	public String quoteAnchorKey() throws Exception
	{
		try
		{
			list = languageService.quoteAnchorKey(anchorKey, projectId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("quoteAnchorKey Exception", e);
			return ERROR;
		}
		return SUCCESS;
	}

	// ����ָ����languageDataId��ȡ������Ϣ
	public String quoteAnchorKeyById() throws Exception
	{
		try
		{
			list = languageService.quoteAnchorKeyById(quoteLanguageDataId);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("quoteAnchorKey Exception", e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * Ajax�ύ��������
	 */
	public String saveAnchorInfo() throws Exception
	{
		try
		{
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
			if (user == null)
				return Action.LOGIN;

			// 1. �κ�һ��TABû��д����<��KEY��Ϊ����>�򲻱����κ�����
			if (lanData.equals("") && newTabData.equals(""))
				return SUCCESS;

			List<String> sqlList = new ArrayList<String>();
			String firsAnchorKey = null;

			logger.info(String.format("����ê���µ�KEY-->New Tab:%s<--->Old Tab:%s", newTabData, lanData));

			int newTabDataId = 0;
			boolean globalKeyFalg = false;
			int globalType = 0;
			// �Ƿ������ⲿKEY<ֻNEW TAB��������>0:������ 1:����
			if (!newTabData.equals(""))
			{
				firsAnchorKey = newTabData.substring(newTabData.indexOf("anchorKey`��=") + 12, newTabData.indexOf("`��!clientTypeCode`��="));

				// ��������KEY��Gerneral_��ͷ���Ϊͨ��KEY
				if (firsAnchorKey != null && firsAnchorKey.trim().toLowerCase().startsWith("general_"))
				{
					globalKeyFalg = true;
					globalType = 1;
				}
			}

			if (quote == 0)
			{
				// NEW TAB ���SQL
				if (!newTabData.equals(""))
				{
					// ���ж��Ƿ����ظ�KEY<�е��û�������г�ͨ��KEY�б�ʹ������Ҫ��������Ҳ���������,�ǵ��Լ�����>
					int dbDataId = languageService.checkDuplicateKeyReturnKey(projectId, firsAnchorKey);
					if (dbDataId > 0)
					{
						newTabDataId = dbDataId;
						if(firsAnchorKey!=null && firsAnchorKey.toLowerCase().startsWith("general_"))
						{
							globalKeyFalg = true;
							quote = 1;
							globalType = 1;
						}
						else
						{
							message = "KEY �ظ�";// ��ͨKEY�ظ�
							return SUCCESS;
						}
					}
					else
					{
						StringBuffer insertSql = new StringBuffer("INSERT INTO languageData(project_id,menu_id,anchor_id,anchor_key,client_code,remark,global_type");
						for (LanguageBean bean : LM_Util.languageList)
						{
							String code = String.valueOf(bean.getLanguageCode());
							insertSql.append(",language_").append(code);
						}

						String[] insertArray = newTabData.split(LM_Util.GROUP_SEPARATOR);
						Map<String, String> map = new HashMap<String, String>();
						for (String m : insertArray)
						{
							if (m.endsWith(LM_Util.DATA_SEPARATOR))
								map.put(m.split(LM_Util.DATA_SEPARATOR)[0], "");
							else
								map.put(m.split(LM_Util.DATA_SEPARATOR)[0], m.split(LM_Util.DATA_SEPARATOR)[1].replace("'", "\\'"));// ת��

						}
						if (globalKeyFalg)
						{
							map.put("clientTypeCode", "0");
						}
						insertSql.append(")VALUES(").append(projectId).append(",").append(menuId).append(",'").append(anchorId).append("','").append(map.get("anchorKey")).append("',").append(map.get("clientTypeCode")).append(",'").append(map.get("remark")).append("',").append(globalType);
						for (LanguageBean bean : LM_Util.languageList)
						{
							String code = String.valueOf(bean.getLanguageCode());
							insertSql.append(",'").append(map.get("newTab_language_" + code)).append("'");
						}
						insertSql.append(")");

						insertSql.toString();
						// ����NEW TAB����
						newTabDataId = languageService.insertDataReturnKey(insertSql.toString());
						if(newTabDataId>0){
							dblog.logger((int)user.getId(), OperationType.INSERT.getValue(), String.format("User %s add new languageData ,the details is: %s", user.getName(),insertSql.toString()));
						}
					}
					
					
				}
			}
			else
			{
				// ������������ֱ�ӽ�KEY��ID��������<ֻ��Ҫά���м��> �����õ����ݲ��ᱻ���κ��޸�
				newTabDataId = quoteLanguageDataId;
			}

			// ��ʷTab����UPDATE SQL
			if (!lanData.equals(""))
			{
				Map<String, String> map = new HashMap<String, String>();
				String[] updateTab = lanData.split(LM_Util.TAB_SEPARATOR);

				// ��ê���� ê������ʾ��KEYΪ��һ��ê���KEY
				if (updateTab[0] != null)
					firsAnchorKey = updateTab[0].substring(updateTab[0].indexOf("anchorKey`��=") + 12, updateTab[0].indexOf("`��!clientTypeCode`��="));

				for (String str : updateTab)
				{
					map = new HashMap<String, String>();
					String[] ele = str.split(LM_Util.GROUP_SEPARATOR);
					for (String k : ele)
					{
						if (k.endsWith(LM_Util.DATA_SEPARATOR))
							map.put(k.split(LM_Util.DATA_SEPARATOR)[0], "");
						else
						{
							map.put(k.split(LM_Util.DATA_SEPARATOR)[0], k.split(LM_Util.DATA_SEPARATOR)[1].replace("'", "\\'"));
						}
					}
					StringBuffer updateSql = new StringBuffer("UPDATE languageData SET ");
					for (LanguageBean bean : LM_Util.languageList)
					{
						String code = String.valueOf(bean.getLanguageCode());
						updateSql.append("language_").append(code).append("='").append(map.get("language_" + code)).append("',");
					}

					updateSql.append("anchor_key='").append(map.get("anchorKey")).append("',client_code=").append(map.get("clientTypeCode")).append(",remark='").append(map.get("remark")).append("' WHERE id=").append(map.get("id"));

					sqlList.add(updateSql.toString());
				}
			}
			// anchor ��Ϣ��������SQL
			String anchorSql = null;
			if (anchorTableId == 0 && !newTabData.equals(""))
			{
				anchorSql = String.format("INSERT INTO anchor(project_id,menu_id,anchor_id,anchor_key,anchor_x,anchor_y,anchor_width,anchor_height) VALUES(%s,%s,'%s','%s',%s,%s,%s,%s)", projectId, menuId, anchorId, firsAnchorKey, anchor_x, anchor_y, anchor_width, anchor_height);
				anchorTableId = languageService.insertDataReturnKey(anchorSql);
			}
			else
			{
				anchorSql = String.format("UPDATE anchor SET anchor_x=%s,anchor_y=%s,anchor_width=%s,anchor_height=%s WHERE id=%s", anchor_x, anchor_y, anchor_width, anchor_height, anchorTableId);
				sqlList.add(anchorSql);
			}

			// ������TABҳ<��ӻ�����>��Ҫά��ê������Ե�ӳ���ϵ��Ϊ��ֹһ��ê�����ظ�����ͬһ��KEY���Լ���Լ������,ע:�ظ����Ҳû��
			if (newTabDataId != 0)
			{
				// ALTER TABLE anchor_languageData ADD
				// UNIQUE(anchor_id,languageData_id);
				String mappingSQL = "";
				if (globalKeyFalg)
				{
					mappingSQL = String.format("insert into anchor_languageData (project_id,anchor_id,languageData_id,quote,client_code) VALUES (%s,%s,%s,%s,%s)", projectId, anchorTableId, newTabDataId, globalType, newTabClientCode);
				}
				else
				{
					mappingSQL = String.format("insert into anchor_languageData (project_id,anchor_id,languageData_id,quote,client_code) VALUES (%s,%s,%s,%s,%s)", projectId, anchorTableId, newTabDataId, quote, newTabClientCode);
				}

				sqlList.add(mappingSQL);
			}

			// �����������
			languageService.saveOrUpdateAnchorInfo(sqlList);
			if(sqlList.size()>0){
				for (String sql : sqlList)
				{
					int opertype = 0;
					if(sql.toLowerCase().indexOf("insert")!=-1){
						opertype = OperationType.INSERT.getValue();
					}else if(sql.toLowerCase().indexOf("update")!=-1){
						opertype = OperationType.UPDATE.getValue();
					}
					dblog.logger((int)user.getId(), opertype, String.format("User %s modfiy data,the detail is: %s", user.getName(),sql.toLowerCase()));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("Ajax����ê���쳣", e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * ɾ��Tabҳ
	 */
	public String deleteTabById() throws Exception
	{
		try
		{
			languageService.deleteTabById(anchorTableId, languageDataId, tabCoount);

			// ���ʼ�֪ͨ
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginuser");
			String mess = String.format("[%s]ɾ����Tab.��ĿID��%s,�˵�ID��%s.", user.getName(), projectId, menuId);
			dblog.logger((int)user.getId(), OperationType.DELETE.getValue(), mess);
			SendMail sm = new SendMail();
			sm.sendEmail(mess);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
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

	public String getAnchorId()
	{
		return anchorId;
	}

	public void setAnchorId(String anchorId)
	{
		this.anchorId = anchorId;
	}

	public int getAnchor_x()
	{
		return anchor_x;
	}

	public void setAnchor_x(int anchor_x)
	{
		this.anchor_x = anchor_x;
	}

	public int getAnchor_y()
	{
		return anchor_y;
	}

	public void setAnchor_y(int anchor_y)
	{
		this.anchor_y = anchor_y;
	}

	public int getAnchor_width()
	{
		return anchor_width;
	}

	public void setAnchor_width(int anchor_width)
	{
		this.anchor_width = anchor_width;
	}

	public int getAnchor_height()
	{
		return anchor_height;
	}

	public void setAnchor_height(int anchor_height)
	{
		this.anchor_height = anchor_height;
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

	public List<AnchorInfoBean> getAnchorInfoBeanList()
	{
		return anchorInfoBeanList;
	}

	public void setAnchorInfoBeanList(List<AnchorInfoBean> anchorInfoBeanList)
	{
		this.anchorInfoBeanList = anchorInfoBeanList;
	}

	public List<LanguageBean> getNewLanguageList()
	{
		return newLanguageList;
	}

	public void setNewLanguageList(List<LanguageBean> newLanguageList)
	{
		this.newLanguageList = newLanguageList;
	}

	public String getLanData()
	{
		return lanData;
	}

	public void setLanData(String lanData)
	{
		this.lanData = lanData;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public int getAnchorTableId()
	{
		return anchorTableId;
	}

	public void setAnchorTableId(int anchorTableId)
	{
		this.anchorTableId = anchorTableId;
	}

	public String getAnchorKey()
	{
		return anchorKey;
	}

	public void setAnchorKey(String anchorKey)
	{
		this.anchorKey = anchorKey;
	}

	public int getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(int clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
	}

	public String getNewTabData()
	{
		return newTabData;
	}

	public void setNewTabData(String newTabData)
	{
		this.newTabData = newTabData;
	}

	public int getQuote()
	{
		return quote;
	}

	public void setQuote(int quote)
	{
		this.quote = quote;
	}

	public int getQuoteLanguageDataId()
	{
		return quoteLanguageDataId;
	}

	public void setQuoteLanguageDataId(int quoteLanguageDataId)
	{
		this.quoteLanguageDataId = quoteLanguageDataId;
	}

	public List<AnchorInfoBean> getList()
	{
		return list;
	}

	public void setList(List<AnchorInfoBean> list)
	{
		this.list = list;
	}

	public int getTabCoount()
	{
		return tabCoount;
	}

	public void setTabCoount(int tabCoount)
	{
		this.tabCoount = tabCoount;
	}

	public int getLanguageDataId()
	{
		return languageDataId;
	}

	public void setLanguageDataId(int languageDataId)
	{
		this.languageDataId = languageDataId;
	}

	public int getNewTabClientCode()
	{
		return newTabClientCode;
	}

	public void setNewTabClientCode(int newTabClientCode)
	{
		this.newTabClientCode = newTabClientCode;
	}

}
