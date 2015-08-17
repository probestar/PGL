package org.pgl.Model;

public class Language {
	public long id;
	public String name;
	public long languageCode;
	public String filename;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(long languageCode) {
		this.languageCode = languageCode;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
