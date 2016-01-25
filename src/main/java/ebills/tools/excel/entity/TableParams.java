package ebills.tools.excel.entity;
/**
 * 模板表详细设置
 * @author hexiao--hxtoyou@163.com
 *
 */
public class TableParams {
	/**
	 * 真是行数
	 */
	private Integer trueRowNum;
	/**
	 * 内容行数
	 */
	private Integer contentEnd;
	/**
	 * 内容行数
	 */
	private Integer detailLineNum;
	/**
	 * 表尾行数
	 */
	private Integer tailRowNum;
	/**
	 * 标题尾数
	 */
	private Integer headsTail;
	/**
	 * 数据起始行
	 */
	private Integer dataStartIndex;
	/**
	 * 一页显示数据条数
	 */
	private Integer pageViewNum;
	/**
	 * 数据所占行数
	 */
	private Integer copyRowNum;
	/**
	 * 数据起始行数
	 */
	private Integer detailTail;
	/**
	 * 数据结束行数
	 */
	private Integer detailHead;
	/**
	 * 总列数
	 */
	private Integer cellnum;
	public Integer getTrueRowNum() {
		return trueRowNum;
	}
	public void setTrueRowNum(Integer trueRowNum) {
		this.trueRowNum = trueRowNum;
	}
	public Integer getContentEnd() {
		return contentEnd;
	}
	public void setContentEnd(Integer contentEnd) {
		this.contentEnd = contentEnd;
	}
	public Integer getDetailLineNum() {
		return detailLineNum;
	}
	public void setDetailLineNum(Integer detailLineNum) {
		this.detailLineNum = detailLineNum;
	}
	public Integer getTailRowNum() {
		return tailRowNum;
	}
	public void setTailRowNum(Integer tailRowNum) {
		this.tailRowNum = tailRowNum;
	}
	public Integer getHeadsTail() {
		return headsTail;
	}
	public void setHeadsTail(Integer headsTail) {
		this.headsTail = headsTail;
	}
	public Integer getDataStartIndex() {
		return dataStartIndex;
	}
	public void setDataStartIndex(Integer dataStartIndex) {
		this.dataStartIndex = dataStartIndex;
	}
	public Integer getPageViewNum() {
		return pageViewNum;
	}
	public void setPageViewNum(Integer pageViewNum) {
		this.pageViewNum = pageViewNum;
	}
	public Integer getCopyRowNum() {
		return copyRowNum;
	}
	public void setCopyRowNum(Integer copyRowNum) {
		this.copyRowNum = copyRowNum;
	}
	public Integer getDetailTail() {
		return detailTail;
	}
	public void setDetailTail(Integer detailTail) {
		this.detailTail = detailTail;
	}
	public Integer getDetailHead() {
		return detailHead;
	}
	public void setDetailHead(Integer detailHead) {
		this.detailHead = detailHead;
	}
	public Integer getCellnum() {
		return cellnum;
	}
	public void setCellnum(Integer cellnum) {
		this.cellnum = cellnum;
	}
	
}
