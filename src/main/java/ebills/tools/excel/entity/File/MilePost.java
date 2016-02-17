package ebills.tools.excel.entity.File;

import java.sql.Date;

import javax.persistence.Entity;

import ebills.tools.util.IDEntity;
@Entity
public class MilePost extends IDEntity{
	/**
	 * 公司
	 */
	private String corpName;
	/**
	 * 合同编号
	 */
	private String contractNo;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 项目编号
	 */
	private String projectNo;
	/**
	 * PO ID
	 */
	private String poid;
	/**
	 * 合同方
	 */
	private String contractName;
	/**
	 * 收入确认类型
	 */
	private String incomeType;
	/**
	 * BU
	 */
	private String bu;
	/**
	 * DBU
	 */
	private String dbu;
	/**
	 * 部门
	 */
	private String department;
	/**
	 * 是否开票
	 */
	private String isNeedInv;
	/**
	 * 交付里程碑达成
	 */
	private String isGetMile;
	/**
	 * 币种
	 */
	private String cur;
	/**
	 * 合同额
	 */
	private Double amt;
	/**
	 * 计划开票日期
	 */
	private Date planTime;
	/**
	 * 计划开票金额
	 */
	private Double planAmt;
	/**
	 * 实际开票日期
	 */
	private Date realTime;
	/**
	 * 实际开票金额
	 */
	private Double realAmt;
	/**
	 * 实际开票净额
	 */
	private Double pureAmt;
	/**
	 * 实际税额
	 */
	private Double taxAmt;
	/**
	 * 折扣
	 */
	private Double discount;
	/**
	 * PM
	 */
	private String pm;
	/**
	 * 销售人
	 */
	private String saler;
	/**
	 * 财务备注
	 */
	private String financalRemark;
	/**
	 * 财务备注
	 */
	private String boRemark;
	/**
	 * PM备注
	 */
	private String pmRemark;
	/**
	 * 发票号码
	 */
	private String invoiceNo;
	/**
	 * 项目状态
	 */
	private String projectStatus;
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getPoid() {
		return poid;
	}
	public void setPoid(String poid) {
		this.poid = poid;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getIncomeType() {
		return incomeType;
	}
	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}
	public String getBu() {
		return bu;
	}
	public void setBu(String bu) {
		this.bu = bu;
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
	public String getIsNeedInv() {
		return isNeedInv;
	}
	public void setIsNeedInv(String isNeedInv) {
		this.isNeedInv = isNeedInv;
	}
	public String getIsGetMile() {
		return isGetMile;
	}
	public void setIsGetMile(String isGetMile) {
		this.isGetMile = isGetMile;
	}
	public String getCur() {
		return cur;
	}
	public void setCur(String cur) {
		this.cur = cur;
	}
	public Date getPlanTime() {
		return planTime;
	}
	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}
	public Date getRealTime() {
		return realTime;
	}
	public void setRealTime(Date realTime) {
		this.realTime = realTime;
	}
	public Double getAmt() {
		return amt;
	}
	public void setAmt(Double amt) {
		this.amt = amt;
	}
	public Double getPlanAmt() {
		return planAmt;
	}
	public void setPlanAmt(Double planAmt) {
		this.planAmt = planAmt;
	}
	public Double getRealAmt() {
		return realAmt;
	}
	public void setRealAmt(Double realAmt) {
		this.realAmt = realAmt;
	}
	public Double getPureAmt() {
		return pureAmt;
	}
	public void setPureAmt(Double pureAmt) {
		this.pureAmt = pureAmt;
	}
	public Double getTaxAmt() {
		return taxAmt;
	}
	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getPm() {
		return pm;
	}
	public void setPm(String pm) {
		this.pm = pm;
	}
	public String getSaler() {
		return saler;
	}
	public void setSaler(String saler) {
		this.saler = saler;
	}
	public String getFinancalRemark() {
		return financalRemark;
	}
	public void setFinancalRemark(String financalRemark) {
		this.financalRemark = financalRemark;
	}
	public String getBoRemark() {
		return boRemark;
	}
	public void setBoRemark(String boRemark) {
		this.boRemark = boRemark;
	}
	public String getPmRemark() {
		return pmRemark;
	}
	public void setPmRemark(String pmRemark) {
		this.pmRemark = pmRemark;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	
}
