package ebills.tools.webService.cxf.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="reg_bean")
public class RegBean {
	private String reg_sn;
	private Integer user_id;
	private String reg_no;
	private String reg_way;
	private String ret_url;
	private String remarks;
	public String getReg_sn() {
		return reg_sn;
	}
	public void setReg_sn(String reg_sn) {
		this.reg_sn = reg_sn;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getReg_no() {
		return reg_no;
	}
	public void setReg_no(String reg_no) {
		this.reg_no = reg_no;
	}
	public String getReg_way() {
		return reg_way;
	}
	public void setReg_way(String reg_way) {
		this.reg_way = reg_way;
	}
	public String getRet_url() {
		return ret_url;
	}
	public void setRet_url(String ret_url) {
		this.ret_url = ret_url;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
