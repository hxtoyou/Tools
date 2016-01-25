package ebills.tools.excel.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ebills.tools.excel.service.ReportsGeneratorService;
import ebills.tools.util.CommonUtil;
import ebills.tools.util.Response;




/**
 * Excel导出
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间�?015�?�?9�?下午2:48:50
 * 
 */
@Controller
public class ExcelExportAction{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6953963279404708654L;
	@Autowired
	private ReportsGeneratorService genaratorService;
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	
	@RequestMapping(value="/excel/export", method = RequestMethod.GET)
	@ResponseBody
	public Response export(HttpServletResponse response) throws Exception {
		//取组件名称，
		Map<String,String> req = CommonUtil.getParametersMap(httpServletRequest);
		Map<String,Object> context = new HashMap<String,Object>();
		context.putAll(req);
		genaratorService.export(context);
		return new Response();
	}
}
