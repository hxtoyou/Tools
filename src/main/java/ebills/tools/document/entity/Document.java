package ebills.tools.document.entity;

import java.sql.Date;

import javax.persistence.Entity;

import ebills.tools.util.IDEntity;

@Entity
public class Document extends IDEntity {
	private String name;
	private String path;
	private Date time;
	private String type;
	private Boolean isParase;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getIsParase() {
		return isParase;
	}
	public void setIsParase(Boolean isParase) {
		this.isParase = isParase;
	}
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
