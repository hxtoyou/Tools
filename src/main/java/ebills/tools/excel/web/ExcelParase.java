package ebills.tools.excel.web;

import org.apache.commons.fileupload.UploadContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ebills.tools.document.action.FileUploadUtil;

@Controller
public class ExcelParase {
	@RequestMapping(value="/excel/parase",method = RequestMethod.GET)
	public void parase(String path){
		String realPath = FileUploadUtil.uploadPath+"\\"+path;
	}
}
