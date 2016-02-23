package ebills.tools.webService.cxf.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement( name = "msg_bean" )
public class MsgRegBean {
	private DataBean dataBean;
	private List<RegBean> regBeans;
	public DataBean getDataBean() {
		return dataBean;
	}
	public void setDataBean(DataBean dataBean) {
		this.dataBean = dataBean;
	}
	public List<RegBean> getRegBeans() {
		return regBeans;
	}
	public void setRegBeans(List<RegBean> regBeans) {
		this.regBeans = regBeans;
	}
}
