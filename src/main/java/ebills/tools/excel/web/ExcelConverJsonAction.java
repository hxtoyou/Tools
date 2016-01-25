package ebills.tools.excel.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

import ebills.tools.excel.service.ReportsGeneratorService;
import ebills.tools.util.CommonUtil;
import ebills.tools.util.Response;


/**
 * 返回�?��Excel显示数据
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间�?015�?�?�?下午3:42:08
 * 
 */
@Controller
public class ExcelConverJsonAction{
	private static final long	serialVersionUID	= -8477388752748427429L;
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 
	 */
	@Autowired
	private ReportsGeneratorService genaratorService;
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	
	@RequestMapping(value = "/excel/convert/detail", method = RequestMethod.GET)
	@ResponseBody
	public Response getExcelHtml(HttpSession httpSession,String path) throws Exception {
		//取组件名称，
		Map<String,String> req = CommonUtil.getParametersMap(httpServletRequest);
		Map<String, Object> context = Maps.newHashMap();
		context.putAll(req);
		String	results = (String) genaratorService.generateReport(context).get("output");
		return new Response(results);
	}
}