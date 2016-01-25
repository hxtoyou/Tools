package ebills.tools.excel.entity;



public  class TableSetting {
	/**
	 * 扩展javaClass
	 */
	private String javaClass;
	/**
	 * 执行前sql
	 */
	private String preSQL;
	/**
	 * 查询sql
	 */
	private String querySQL;
	/**
	 * 结果集Tag
	 */
	private String queryTag;
	/**
	 * 是否分页
	 */
	private String pagination;
	/**
	 * 总列数
	 */
	private String totalColumnNum;
	/**
	 * 头行数
	 */
	private String heads;
	/**
	 * 尾行数
	 */
	private String tails;
	/**
	 * 结果集行数
	 */
	private String details;
	/**
	 * 模板名称
	 */
	private String templateName;
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getJavaClass() {
		return javaClass;
	}
	public void setJavaClass(String javaClass) {
		this.javaClass = javaClass;
	}
	public String getPreSQL() {
		return preSQL;
	}
	public void setPreSQL(String preSQL) {
		this.preSQL = preSQL;
	}
	public String getQuerySQL() {
		return querySQL;
	}
	public void setQuerySQL(String querySQL) {
		this.querySQL = querySQL;
	}
	public String getQueryTag() {
		return queryTag;
	}
	public void setQueryTag(String queryTag) {
		this.queryTag = queryTag;
	}
	public String getPagination() {
		return pagination;
	}
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	public String getTotalColumnNum() {
		return totalColumnNum;
	}
	public void setTotalColumnNum(String totalColumnNum) {
		this.totalColumnNum = totalColumnNum;
	}
	public String getHeads() {
		return heads;
	}
	public void setHeads(String heads) {
		this.heads = heads;
	}
	public String getTails() {
		return tails;
	}
	public void setTails(String tails) {
		this.tails = tails;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}
