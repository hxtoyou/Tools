package ebills.tools.charts.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EchartsAction {
	@RequestMapping(value="/echart/index", method = RequestMethod.GET)
	public ModelAndView index(){
		return new ModelAndView("/charts/index");
		
	}
}
