package org.pgl.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.pgl.service.DownloadService;
import org.pgl.service.ZipCompressor;

import com.opensymphony.xwork2.ActionSupport;

public class DownLoadAction extends ActionSupport
{
	private static final long serialVersionUID = -3945761248906731479L;
	private static Log logger = LogFactory.getLog(DownLoadAction.class);
	private String clientTypeCode;
	private OutputStream res;
	private ZipOutputStream zos;
	private HttpServletRequest request;
	private String selectValue;// Ҫ�����Ŀͻ������� ����:0,1,2,
	private String zipFilepath;

	public String execute() throws Exception
	{
		if (selectValue == null || selectValue.length() <= 0 || clientTypeCode == null || clientTypeCode.length() <= 0)
		{
			return ERROR;
		}
		
		
		return SUCCESS;
	}

	public InputStream getDownloadFile()
	{
		logger.info("Expert File!");
		String[] arrayLanguage = selectValue.split(",");
		String projectId = (String) ServletActionContext.getRequest().getSession().getAttribute("projectid");

		// ���������ļ�
		DownloadService objService = new DownloadService();
		String filePath = objService.createFileAndDownload(arrayLanguage, Integer.parseInt(clientTypeCode), Long.parseLong(projectId));

		// �ļ����
		zipFilepath = objService.filepath + "multi-language.zip";
		ZipCompressor zc = new ZipCompressor(zipFilepath);
		zc.compress(filePath);
		File file = new File(zipFilepath);
		InputStream is = null;
		try
		{
			is = new FileInputStream(file);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		return is;
	}

	public HttpServletRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpServletRequest request)
	{
		this.request = request;
	}

	public OutputStream getRes()
	{
		return res;
	}

	public void setRes(OutputStream res)
	{
		this.res = res;
	}

	public ZipOutputStream getZos()
	{
		return zos;
	}

	public void setZos(ZipOutputStream zos)
	{
		this.zos = zos;
	}

	public String getSelectValue()
	{
		return selectValue;
	}

	public void setSelectValue(String selectValue)
	{
		this.selectValue = selectValue;
	}

	public String getClientTypeCode()
	{
		return clientTypeCode;
	}

	public void setClientTypeCode(String clientTypeCode)
	{
		this.clientTypeCode = clientTypeCode;
	}
}
