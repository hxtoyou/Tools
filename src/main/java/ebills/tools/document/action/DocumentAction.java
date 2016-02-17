package ebills.tools.document.action;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;





import ebills.tools.document.entity.Document;
import ebills.tools.document.service.DocumentService;
import ebills.tools.excel.service.ExcelParaseService;
import ebills.tools.util.GridPag;
import ebills.tools.util.Page;
import ebills.tools.util.Response;

@Controller
public class DocumentAction {
	@Autowired
	private DocumentService documentService;
	@Autowired
	private ExcelParaseService excelParaseService;
	
	@RequestMapping(value = "/document/list", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Response getExcelHtml(Page<Document> page,HttpSession httpSession) throws Exception {
		//取组件名称，
		 page = documentService.getPage(page);
		GridPag gridPage = new GridPag<Document>();
		 gridPage.setCurrent(page.getPageNumber());
		 gridPage.setRowCount(page.getPageSize());
		 gridPage.setTotal(page.getTotalCount());
		 gridPage.setRows(page.getResult());
		return new Response(gridPage);
	}
	@RequestMapping(value = "/document/parase", method = RequestMethod.GET)
	@ResponseBody
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Response paraseData(String type,String path){
		if("project".equals(type)){
			excelParaseService.saveProjectData(path);
			excelParaseService.saveMilePostData(path);
		}else if("invoice".equals(type)){
			excelParaseService.saveInvoiceData(path);
			excelParaseService.saveCollectionData(path);
		}
		return new Response(true);
	}
}
