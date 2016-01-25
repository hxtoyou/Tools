package ebills.tools.excel.service;


/***
 * 该接口用于规范当通用的面函或报表处理程序不能满足要求时，允许在实现此接口的实现类里面作一些扩�?
 * 比如报表：执行存储过�?组织报表展示数据�?
 * 比如面函：组织表单数�?
 * @author xiangxing
 */
public interface ReportExtendInterface<T> {
	
	/**
	 * 具体的扩展在此方法中实现
	 * @param context
	 */
	public void execute(T context);
	
	/**
	 * 清理
	 * @param context
	 */
	public void clean(T context);

}
