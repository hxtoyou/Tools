package ebills.tools.excel.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Test {
	private Long Id;
	private String name;
	private String value;
	private String ckey;
	private String num;
	
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Id
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCkey() {
		return ckey;
	}
	public void setCkey(String ckey) {
		this.ckey = ckey;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
}
