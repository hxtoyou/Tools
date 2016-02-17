package ebills.tools.excel.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间�?015�?�?�?下午4:03:24
 * 
 */
public interface ReportsGenerator<T> {
	T generateReport(T context) throws Exception;
	
	void export(T context,HttpServletResponse response) throws Exception;
}
