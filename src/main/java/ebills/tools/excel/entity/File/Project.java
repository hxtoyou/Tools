package ebills.tools.excel.entity.File;

import java.sql.Date;

import javax.persistence.Entity;

import ebills.tools.util.IDEntity;
@Entity
public class Project extends IDEntity{
	/**
	 * LegalEntity
	 */
	private String legalEntity;
	/**
	 * ABU
	 */
	private String abu;
	/**
	 * DBU
	 */
	private String dbu;
	/**
	 * DTM
	 */
	private String dtm;
	/**
	 * 部门
	 */
	private String department;
	/**
	 * 项目编号
	 */
	private String projectId;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 合同方
	 */
	private String contractName;
	/**
	 * 最终客户
	 */
	private String custName;
	/**
	 * Account Group
	 */
	private String accountGroup;
	/**
	 * PM
	 */
	private String pm;
	/**
	 * PL
	 */
	private String pl;
	/**
	 * Category
	 */
	private String category;
	/**
	 * Frontlog收入确认时间
	 */
	private String incomeTime;
	/**
	 * EATP预计合同签订时间
	 */
	private Date eatpTime;
	/**
	 * 项目类型
	 */
	private String projectType;
	/**
	 * 工作城市
	 */
	private String city;
	/**
	 * 计划开始
	 */
	private Date planStart;
	/**
	 * 计划结束
	 */
	private Date planEnd;
	/**
	 * 实际开始
	 */
	private Date realStart;
	/**
	 * 实际结束
	 */
	private Date realEnd;
	/**
	 * 技术名称
	 */
	private String techName;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 收入确认类型
	 */
	private String incomeComfirmType;
	/**
	 * MainSub
	 */
	private String mainsub;
	/**
	 * Associated Primary PID
	 */
	private String pid;
	/**
	 * ONOFFTRIP
	 */
	private String onofftrip;
	/**
	 * ProductLine
	 */
	private String productline;
	/**
	 * Cost Center
	 */
	private String costCenter;
	/**
	 * ABU-CCC
	 */
	private String abuccc;
	/**
	 * Combine To CCC
	 */
	private String combineccc;
	/**
	 * IsCrossCharge
	 */
	private String isCrossCharge;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * BO
	 */
	private String bo;
	/**
	 * 创建人
	 */
	private String creater;
	/**
	 * 创建时间
	 */
	private Date creatTime;
	/**
	 * 更新人
	 */
	private String updater;
	/**
	 * 更新日期
	 */
	private Date updateTime;
	/**
	 * RevenueType
	 */
	private String revenueType;
	/**
	 * RevRec
	 */
	private String revrec;
	/**
	 * 结算币种
	 */
	private String cur;
	/**
	 * SmartData
	 */
	private String smartdata;
	/**
	 * 订单类型
	 */
	private String orderType;
	/**
	 * 机会编号
	 */
	private String changeCode;
	/**
	 * 销售
	 */
	private String saler;
	/**
	 * Contract Amount_Delivery
	 */
	private Double delivery;
	/**
	 * Contract Amount_EATP
	 */
	private String eatp;
	/**
	 * Contract Amount_Total
	 */
	private Double total;
	/**
	 * 活动/非活动
	 */
	private String isActivity;
	/**
	 * BG Solution Type_L1
	 */
	private String solutionType1;
	/**
	 * BG Solution Type_L2
	 */
	private String solutionType2;
	/**
	 * 项目管理模式
	 */
	private String projectManageType;
	/**
	 * Working Location
	 */
	private String workLoal;
	/**
	 * Partnership
	 */
	private String partnership;
	/**
	 * Project Region
	 */
	private String projectRegion;
	/**
	 * MS Division
	 */
	private String msDivision;
	/**
	 * Pipeline PID
	 */
	private String pipeline;
	/**
	 * Pipeline转正式日期
	 */
	private Date pipelineTime;
	/**
	 * 是否有里程碑信息
	 */
	private String isMileInfo;
	public String getLegalEntity() {
		return legalEntity;
	}
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
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
	public String getDtm() {
		return dtm;
	}
	public void setDtm(String dtm) {
		this.dtm = dtm;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
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
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getAccountGroup() {
		return accountGroup;
	}
	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}
	public String getPm() {
		return pm;
	}
	public void setPm(String pm) {
		this.pm = pm;
	}
	public String getPl() {
		return pl;
	}
	public void setPl(String pl) {
		this.pl = pl;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getIncomeTime() {
		return incomeTime;
	}
	public void setIncomeTime(String incomeTime) {
		this.incomeTime = incomeTime;
	}
	public Date getEatpTime() {
		return eatpTime;
	}
	public void setEatpTime(Date eatpTime) {
		this.eatpTime = eatpTime;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getPlanStart() {
		return planStart;
	}
	public void setPlanStart(Date planStart) {
		this.planStart = planStart;
	}
	public Date getPlanEnd() {
		return planEnd;
	}
	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}
	public Date getRealStart() {
		return realStart;
	}
	public void setRealStart(Date realStart) {
		this.realStart = realStart;
	}
	public Date getRealEnd() {
		return realEnd;
	}
	public void setRealEnd(Date realEnd) {
		this.realEnd = realEnd;
	}
	public String getTechName() {
		return techName;
	}
	public void setTechName(String techName) {
		this.techName = techName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIncomeComfirmType() {
		return incomeComfirmType;
	}
	public void setIncomeComfirmType(String incomeComfirmType) {
		this.incomeComfirmType = incomeComfirmType;
	}
	public String getMainsub() {
		return mainsub;
	}
	public void setMainsub(String mainsub) {
		this.mainsub = mainsub;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getOnofftrip() {
		return onofftrip;
	}
	public void setOnofftrip(String onofftrip) {
		this.onofftrip = onofftrip;
	}
	public String getProductline() {
		return productline;
	}
	public void setProductline(String productline) {
		this.productline = productline;
	}
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getAbuccc() {
		return abuccc;
	}
	public void setAbuccc(String abuccc) {
		this.abuccc = abuccc;
	}
	public String getCombineccc() {
		return combineccc;
	}
	public void setCombineccc(String combineccc) {
		this.combineccc = combineccc;
	}
	public String getIsCrossCharge() {
		return isCrossCharge;
	}
	public void setIsCrossCharge(String isCrossCharge) {
		this.isCrossCharge = isCrossCharge;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getBo() {
		return bo;
	}
	public void setBo(String bo) {
		this.bo = bo;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getRevenueType() {
		return revenueType;
	}
	public void setRevenueType(String revenueType) {
		this.revenueType = revenueType;
	}
	public String getRevrec() {
		return revrec;
	}
	public void setRevrec(String revrec) {
		this.revrec = revrec;
	}
	public String getCur() {
		return cur;
	}
	public void setCur(String cur) {
		this.cur = cur;
	}
	public String getSmartdata() {
		return smartdata;
	}
	public void setSmartdata(String smartdata) {
		this.smartdata = smartdata;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getChangeCode() {
		return changeCode;
	}
	public void setChangeCode(String changeCode) {
		this.changeCode = changeCode;
	}
	public String getSaler() {
		return saler;
	}
	public void setSaler(String saler) {
		this.saler = saler;
	}
	public String getEatp() {
		return eatp;
	}
	public void setEatp(String eatp) {
		this.eatp = eatp;
	}
	public Double getDelivery() {
		return delivery;
	}
	public void setDelivery(Double delivery) {
		this.delivery = delivery;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getIsActivity() {
		return isActivity;
	}
	public void setIsActivity(String isActivity) {
		this.isActivity = isActivity;
	}
	public String getSolutionType1() {
		return solutionType1;
	}
	public void setSolutionType1(String solutionType1) {
		this.solutionType1 = solutionType1;
	}
	public String getSolutionType2() {
		return solutionType2;
	}
	public void setSolutionType2(String solutionType2) {
		this.solutionType2 = solutionType2;
	}
	public String getProjectManageType() {
		return projectManageType;
	}
	public void setProjectManageType(String projectManageType) {
		this.projectManageType = projectManageType;
	}
	public String getWorkLoal() {
		return workLoal;
	}
	public void setWorkLoal(String workLoal) {
		this.workLoal = workLoal;
	}
	public String getPartnership() {
		return partnership;
	}
	public void setPartnership(String partnership) {
		this.partnership = partnership;
	}
	public String getProjectRegion() {
		return projectRegion;
	}
	public void setProjectRegion(String projectRegion) {
		this.projectRegion = projectRegion;
	}
	public String getMsDivision() {
		return msDivision;
	}
	public void setMsDivision(String msDivision) {
		this.msDivision = msDivision;
	}
	public String getPipeline() {
		return pipeline;
	}
	public void setPipeline(String pipeline) {
		this.pipeline = pipeline;
	}
	public Date getPipelineTime() {
		return pipelineTime;
	}
	public void setPipelineTime(Date pipelineTime) {
		this.pipelineTime = pipelineTime;
	}
	public String getIsMileInfo() {
		return isMileInfo;
	}
	public void setIsMileInfo(String isMileInfo) {
		this.isMileInfo = isMileInfo;
	}
	
}
