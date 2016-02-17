package ebills.tools.util;

import java.util.List;

import com.google.common.collect.Lists;

public class GridPag<T> {
	private Integer current=1;
	private Integer rowCount;
	private Long total=0l;
	protected List<T> rows = Lists.newArrayList();
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
