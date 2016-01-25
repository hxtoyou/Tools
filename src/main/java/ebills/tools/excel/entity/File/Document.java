package ebills.tools.excel.entity.File;

import java.sql.Date;

import javax.persistence.Entity;

import ebills.tools.excel.entity.IDEntity;

@Entity
public class Document extends IDEntity {
	private String name;
	private String path;
	private Date time;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
