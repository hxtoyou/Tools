package ebills.tools.document.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

import org.apache.commons.fileupload.*;

import javax.servlet.http.HttpServletRequest;

import java.util.regex.Pattern;
import java.io.IOException;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.poi.openxml4j.opc.internal.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ebills.tools.document.entity.Document;
import ebills.tools.document.service.DocumentService;
import ebills.tools.util.Response;

import java.util.regex.Matcher;
/**
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
@Controller
public class FileUploadUtil {
	public final static String uploadPath = "D:\\temp"; // 上传文件的目录
	public final static String tempPath = "d:\\temp\\buffer\\"; // 临时文件目录
	File tempPathFile;
	@Autowired
	private HttpServletRequest httpServletRequest;
	@Autowired
	private DocumentService documentService;
    /** 文件上载
     * @return true —— success; false —— fail.
     */
	@RequestMapping(value = "/document/fileUpload", method = RequestMethod.POST)
	@ResponseBody
    public ModelAndView Upload(String fileType, @RequestParam("fileName") MultipartFile file){
    	try {
    	  init();
    	  long time = (new Date()).getTime();
    	  String name = file.getOriginalFilename();
    	  if(file.getOriginalFilename().indexOf(".")!=-1){
    		  int splitIndex = name.indexOf(".xls");
    			String startString = name.substring(0, splitIndex);
    			String end = name.substring(splitIndex);
    			name=startString+time+end;
    	  }
          FileInputStream in=(FileInputStream) file.getInputStream();
          File savedFile = new File(uploadPath,name);
          FileOutputStream out=new FileOutputStream(savedFile);
          int c;
          byte buffer[]=new byte[1024];
          while((c=in.read(buffer))!=-1){
              for(int i=0;i<c;i++)
                  out.write(buffer[i]);        
          }
          in.close();
          out.close();
          Document document = new Document();
          document.setIsParase(false);
          document.setName(file.getOriginalFilename());
          document.setPath(name);
          document.setType(fileType);
          document.setTime(new java.sql.Date(time));
          documentService.save(document);
          return new ModelAndView("/index");
      } catch (Exception e) {
          // 可以跳转出错页面
          e.printStackTrace();
      }
    	return new ModelAndView("/index");
       
    }
    
    public void init()  {
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
        File tempPathFile = new File(tempPath);
         if (!tempPathFile.exists()) {
            tempPathFile.mkdirs();
        }
     }
}