package ebills.tools.webService.cxf.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="data_bean")
public class DataBean {
	private String batch_no;
	private String data_type;
	private String version;
	private String user_name;
	private String msg_sign;
	private String rd_name;
	private String rd_num;
	public String getRd_num() {
		return rd_num;
	}
	public void setRd_num(String rd_num) {
		this.rd_num = rd_num;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getMsg_sign() {
		return msg_sign;
	}
	public void setMsg_sign(String msg_sign) {
		this.msg_sign = msg_sign;
	}
	public String getRd_name() {
		return rd_name;
	}
	public void setRd_name(String rd_name) {
		this.rd_name = rd_name;
	}
	
}
