package ebills.tools.excel.web;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import ebills.tools.excel.entity.SystemSetting;
import ebills.tools.excel.entity.inter.ExcelForm;
import ebills.tools.excel.service.ReportsGeneratorService;
import ebills.tools.util.ExcelToHtmlUtil;


/**
 * Excel创建查询窗口
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间�?015�?�?�?下午2:57:46
 * 
 */
@Controller
public class ExcelCreateFormAction{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private List<ExcelForm> configItems = Lists.newArrayList();
	private ObjectMapper mapper = new ObjectMapper();  
	protected Map<String, Object> context = new HashMap<String, Object>();
	@Autowired
	private ReportsGeneratorService genaratorService;
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/excel/createForm", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getForm(){
	        //将要被返回到客户端的对象  
		
			long start = System.currentTimeMillis();
			String locale = httpServletRequest.getParameter("locale");
			String pathName = httpServletRequest.getParameter("action");
//			String filePath = genaratorService.getFilePath(pathName);
//			String realPath = httpServletRequest.getRealPath(filePath);
//			int fileNameIndex = realPath.lastIndexOf(File.separator);
//			String fileName = realPath.substring(fileNameIndex+1);
//			String path = realPath.substring(0,fileNameIndex+1);
//			String absoltePath = ExcelToHtmlUtil.getFileNameByInternationnal(path,fileName, locale);
			String absoltePath = "D:/workspace_10/tools/src/main/webapp/WEB-INF/upload/test_CN.xlsx";
			Workbook workbook = ExcelToHtmlUtil.getExcelWorkBook(absoltePath);
			/**
			 * 解析excel系统设置模板配置信息
			 */
			Sheet sheet1 = workbook.getSheetAt(0);//系统设置模板
			int systtemSetrownum = sheet1.getLastRowNum();
			SystemSetting systemSetResult = ExcelToHtmlUtil.analyzeSystemSetting(sheet1, systtemSetrownum+1);
			Integer querySheet =systemSetResult.getQuerySheet();//查询参数模板页码
			String queryFromName = systemSetResult.getQueryFormName();
			String validateFile = systemSetResult.getValidateFile();
			/**
			 * 查询条件模板页条件解�?
			 * 
			 * 
			 */
			Sheet sheet = workbook.getSheetAt(querySheet-1);
			configItems = genaratorService.getExcelForm(sheet);
			try {
				context.put("user",mapper.writeValueAsString(configItems));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			context.put("queryFromName", queryFromName);
			context.put("filePath",absoltePath.replaceAll("\\\\", "/"));
			if(!Strings.isNullOrEmpty(validateFile)){
				context.put("validateFile", validateFile);
			}else{
				System.out.println("生成表格或�?验证js文件路径未设�?");
			}
			  System.out.println("耗时: " + (System.currentTimeMillis() - start)+"毫秒");
//			  ServletActionContext.getRequest().setAttribute("context", context);
			 return new ModelAndView("/excel/index",context);
	}

}
