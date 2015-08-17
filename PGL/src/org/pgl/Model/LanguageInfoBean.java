package org.pgl.Model;

public class LanguageInfoBean
{
	private int id;
	private String languageName;
	private int languageCode;
	private String ios_fileName;
	private String android_fileName;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getLanguageName()
	{
		return languageName;
	}

	public void setLanguageName(String languageName)
	{
		this.languageName = languageName;
	}

	public int getLanguageCode()
	{
		return languageCode;
	}

	public void setLanguageCode(int languageCode)
	{
		this.languageCode = languageCode;
	}

	public String getIos_fileName()
	{
		return ios_fileName;
	}

	public void setIos_fileName(String ios_fileName)
	{
		this.ios_fileName = ios_fileName;
	}

	public String getAndroid_fileName()
	{
		return android_fileName;
	}

	public void setAndroid_fileName(String android_fileName)
	{
		this.android_fileName = android_fileName;
	}



}
