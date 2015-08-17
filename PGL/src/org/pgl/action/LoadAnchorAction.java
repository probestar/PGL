package org.pgl.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.pgl.Model.AnchorBean;
import org.pgl.service.CopyWriterService;
import org.pgl.service.FileService;
import org.pgl.service.PreviewAnchorService;
import org.pgl.util.LM_Util;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ��ʼ����չ��ê��
 * 
 * @author mazhanghui
 * 
 */
public class LoadAnchorAction extends ActionSupport
{
	private static final long serialVersionUID = 6363096221891333750L;

	private int projectId;
	private int menuId;
	private int copyWriterCount; // ͨ���İ�������̬����ҳ��߶�
	private int id; // �İ�ID ��ê���İ��б�ҳ����ת�����ǵ�ͼ��չʾҳ�� for radio checked
	private List<AnchorBean> anchorList = new ArrayList<AnchorBean>();
	private Map<Integer, String> copyWriterMap = new HashMap<Integer, String>();
	private HttpServletRequest request;
	private int clientTypeCode;
	private PreviewAnchorService previewService = new PreviewAnchorService();
	private FileService fileService = new FileService();
	private CopyWriterService copyService = new CopyWriterService();
	private String fileName;// ͼƬ����

	public String execute() throws Exception
	{
		
		
		try
		{
			
			
			if (projectId == 0 || menuId == 0)
				return SUCCESS;
			request = ServletActionContext.getRequest();
			////TODO ϸ���ö��߼�
			Object obj = request.getSession().getAttribute("WW_TRANS_I18N_LOCALE");
			int _lc = 1;
			if(obj!=null){
				Locale l = (Locale)obj;
				if("CN".equals(l.getCountry()))
					_lc = 1;
				if("US".equals(l.getCountry()))
					_lc = 2;
					
			}
			// ��ȡê����Ϣ
			anchorList = previewService.getAnchorInfo(projectId, menuId, _lc, clientTypeCode);

			// ���ظò˵��µ�ͼƬ ��������չʾĬ��ͼƬ
			fileName = fileService.getFileName(projectId, menuId);
			if (fileName != null)
			{
				String fileDir = ServletActionContext.getServletContext().getRealPath("images");
				File file = new File(fileDir);
				if (!file.exists())
					file.mkdirs();

				fileService.readFile(projectId, menuId, fileDir, fileName);
			}

			// �����İ���Ϣ
			copyWriterMap = copyService.getCopyWriter(projectId, menuId, clientTypeCode);
			if (copyWriterMap != null && copyWriterMap.size() > 0)
			{
				copyWriterCount = copyWriterMap.size();
			}

			// request.setAttribute("copyWriterCount", copyWriterCount);
			// request.setAttribute("anchorList", anchorList);
			request.getSession().setAttribute("se_clientMap", LM_Util.map);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// getter setter

	public HttpServletRequest getRequest()
	{
		return request;
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

	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public List<AnchorBean> getAnchorList()
	{
		return anchorList;
	}

	public void setAnchorList(List<AnchorBean> anchorList)
	{
		this.anchorList = anchorList;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public Map<Integer, String> getCopyWriterMap()
	{
		return copyWriterMap;
	}

	public void setCopyWriterMap(Map<Integer, String> copyWriterMap)
	{
		this.copyWriterMap = copyWriterMap;
	}

	public int getCopyWriterCount()
	{
		return copyWriterCount;
	}

	public void setCopyWriterCount(int copyWriterCount)
	{
		this.copyWriterCount = copyWriterCount;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(int clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
	}

}
