package ebills.tools.excel.entity.File;

import java.sql.Date;

import javax.persistence.Entity;

import ebills.tools.util.IDEntity;
@Entity
public class Collection extends IDEntity{
	/**
	 * customer name
	 */
	private String custName;
	/**
	 * 项目编号
	 */
	private String projectId;
	/**
	 * project_name
	 */
	private String projectName;
	/**
	 * End customer
	 */
	private String endCustomer;
	/**
	 * Account Name
	 */
	private String accountName;
	/**
	 * ABU'2015
	 */
	private String abu;
	/**
	 * DBU'2015
	 */
	private String dbu;
	/**
	 * Department
	 */
	private String department;
	/**
	 * Project CCC
	 */
	private String projectCCC;
	/**
	 * GL_Date
	 */
	private Date glDate;
	/**
	 * 记账本位币
	 */
	private String cur;
	/**
	 * Amount
	 */
	private Double amount;
	public Date getGlDate() {
		return glDate;
	}
	public void setGlDate(Date glDate) {
		this.glDate = glDate;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getEndCustomer() {
		return endCustomer;
	}
	public void setEndCustomer(String endCustomer) {
		this.endCustomer = endCustomer;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAbu() {
		return abu;
	}
	public void setAbu(String abu) {
		this.abu = abu;
	}
	public String getDbu() {
		return dbu;
	}
	public void setDbu(String dbu) {
		this.dbu = dbu;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getProjectCCC() {
		return projectCCC;
	}
	public void setProjectCCC(String projectCCC) {
		this.projectCCC = projectCCC;
	}
	public String getCur() {
		return cur;
	}
	public void setCur(String cur) {
		this.cur = cur;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
