package ebills.tools.webService.cxf;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import ebills.tools.webService.cxf.entity.DataBean;
import ebills.tools.webService.cxf.entity.MsgRegBean;
import ebills.tools.webService.cxf.entity.RegBean;
import ebills.tools.webService.cxf.util.HttpUtil;
import ebills.tools.webService.cxf.util.JaxbObjectAndXmlUtil;

public class Client {
	public String doRegXml() throws Exception
	{ 
	    /** 构造测试报文头对象 */
	    String randNum = RandomStringUtils.randomNumeric(8);//八位
	    DataBean dataBean = new DataBean();
	    dataBean.setBatch_no("N20150204");
	    dataBean.setData_type("000001");
	    dataBean.setVersion("v1.0");
	    dataBean.setUser_name("13957706713");
	    dataBean.setMsg_sign("未知");
	    dataBean.setRd_num(randNum);

	    /** 构造测试报文体对象 */
	    RegBean regBean = new RegBean();
	    regBean.setReg_sn("REG20150204");
	    regBean.setUser_id(15);
	    regBean.setReg_no("33");
	    regBean.setReg_way("pc");
	    regBean.setRet_url("未知");
	    regBean.setRemarks("无备注");
	     
	    RegBean regBean2 = new RegBean();
	    regBean2.setReg_sn("REG20150203");
	    regBean2.setUser_id(13);
	    regBean2.setReg_no("44");
	    regBean2.setReg_way("mobile");
	    regBean2.setRet_url("未知");
	    regBean2.setRemarks("无备注");
	     
	    List<RegBean> regBeans = new ArrayList<RegBean>();
	    regBeans.add(regBean);
	    regBeans.add(regBean2);
	     
	    MsgRegBean msgRegBean = new MsgRegBean();
	    msgRegBean.setDataBean(dataBean);
	    msgRegBean.setRegBeans(regBeans);
	     
	    String regRequestXml = JaxbObjectAndXmlUtil.object2Xml(msgRegBean);//构造报文 XML 格式的字符串
	     
	    System.out.println("\n 请求报文XML: \n"+regRequestXml);
	     
	    /** 获取的Result报文，然后客户端处理业务。 */
	    String resultString = HttpUtil.sendPost(" http://localhost:8081/restful/user/doTest",regRequestXml);
	     
	    System.out.println("\n 获取的Result报文: \n"+resultString);
	    return  resultString;
	     
	}

}
