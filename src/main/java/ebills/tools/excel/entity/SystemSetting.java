package ebills.tools.excel.entity;
/**
 * 系统设置属性
 * @author hexiao--hxtoyou@163.com
 *
 */
public class SystemSetting {
	/**
	 * 明细页配置页码
	 */
	private Integer detailSetSheet;
	/**
	 * 明细页模板页码
	 */
	private Integer detailTemplateSheet;
	/**
	 * 统计页配置页码
	 */
	private Integer statisticSetSheet;
	/**
	 * 统计页模板页码
	 */
	private Integer statisticTemplateSheet;
	/**
	 * 查询条件页码
	 */
	private Integer querySheet;
	/**
	 * 显示页面
	 */
	private String showType;
	/**
	 * 扩展js文件路径
	 */
	private String validateFile;
	/**
	 * 查询窗口名称
	 */
	private String queryFormName;
	public String getValidateFile() {
		return validateFile;
	}
	public void setValidateFile(String validateFile) {
		this.validateFile = validateFile;
	}
	public String getQueryFormName() {
		return queryFormName;
	}
	public void setQueryFormName(String queryFormName) {
		this.queryFormName = queryFormName;
	}
	public Integer getDetailSetSheet() {
		return detailSetSheet;
	}
	public void setDetailSetSheet(Integer detailSetSheet) {
		this.detailSetSheet = detailSetSheet;
	}
	public Integer getDetailTemplateSheet() {
		return detailTemplateSheet;
	}
	public void setDetailTemplateSheet(Integer detailTemplateSheet) {
		this.detailTemplateSheet = detailTemplateSheet;
	}
	public Integer getStatisticSetSheet() {
		return statisticSetSheet;
	}
	public void setStatisticSetSheet(Integer statisticSetSheet) {
		this.statisticSetSheet = statisticSetSheet;
	}
	public Integer getStatisticTemplateSheet() {
		return statisticTemplateSheet;
	}
	public void setStatisticTemplateSheet(Integer statisticTemplateSheet) {
		this.statisticTemplateSheet = statisticTemplateSheet;
	}
	public Integer getQuerySheet() {
		return querySheet;
	}
	public void setQuerySheet(Integer querySheet) {
		this.querySheet = querySheet;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	
}
