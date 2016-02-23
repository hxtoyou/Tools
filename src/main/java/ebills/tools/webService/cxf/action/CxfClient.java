package ebills.tools.webService.cxf.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ebills.tools.util.Response;
import ebills.tools.webService.cxf.Client;
@Controller
public class CxfClient {
	@RequestMapping(value="/cxf/client",method=RequestMethod.GET)
	@ResponseBody
	public Response getXml(){
		Client client = new Client();
		String result = "";
		try {
			result = client.doRegXml();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Response(result);
	}
	@RequestMapping(value="/cxf/index",method=RequestMethod.GET)
	@ResponseBody
	public ModelAndView index(){
		return new ModelAndView("/cfx/index");
	}
}
