package ebills.tools.excel.extend;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ebills.tools.excel.service.ReportExtendInterface;


@Component
public class ExcelExtendPublic implements ReportExtendInterface<Map<String,Object>>{
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Override
	public void execute(Map<String,Object> context) {
		try {
			HttpServletRequest request = httpServletRequest;
			String start = request.getParameter("STARTDATE");
			String end = request.getParameter("ENDDATE");
			String orgNo = request.getParameter("BELONGORGNO");
			String year = request.getParameter("YEAR");
			String month = request.getParameter("MONTH");
			
			context.put("STARTDATE", start);
			context.put("ENDDATE", end);
//			context.put("ORGNAME", this.getOrgName(orgNo));
			context.put("YAERMONTH", year+"年"+month+"月");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	@Override
	public void clean(Map<String,Object> context) {
		
	}
}
