package ebills.tools.excel.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;








import com.google.common.collect.Maps;

import ebills.tools.document.action.FileUploadUtil;
import ebills.tools.excel.entity.File.Collection;
import ebills.tools.excel.entity.File.Invoice;
import ebills.tools.excel.entity.File.MilePost;
import ebills.tools.excel.entity.File.Project;
import ebills.tools.util.ExcelToHtmlUtil;
@Service
public class ExcelParaseService {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private MilePostService milePostService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private CollectionService collectionService;
	public void saveProjectData(String path){
		Map<String,List<Map<String,String>>> projectresult = getProjectData(path);
		for(Map.Entry<String, List<Map<String,String>>> map:projectresult.entrySet()){
			List<Map<String,String>> list = map.getValue();
			for(Map<String,String> m:list){
				Project project = new Project();
				String projectId = m.get("项目编号");
				if(projectService.count(Restrictions.eq("projectId", projectId))>0){
					String sql="delete from project where projectId='"+projectId+"'";
					SQLQuery sqlQuery= projectService.createSQLQuery(sql);
					sqlQuery.executeUpdate();
				}
				for(Map.Entry<String, String> mEnd:m.entrySet()){
					switch (mEnd.getKey()) {
					case "LegalEntity":
						project.setLegalEntity(mEnd.getValue());
						break;
					case "ABU":
						project.setAbu(mEnd.getValue());
						break;
					case "DBU":
						project.setDbu(mEnd.getValue());
						break;
					case "DTM":
						project.setDtm(mEnd.getValue());
						break;
					case "部门":
						project.setDepartment(mEnd.getValue());
						break;
					case "项目编号":
						project.setProjectId(mEnd.getValue());
						break;
					case "项目名称":
						project.setProjectName(mEnd.getValue());
						break;
					case "合同方":
						project.setContractName(mEnd.getValue());
						break;
					case "最终客户":
						project.setCustName(mEnd.getValue());
						break;
					case "Account Group":
						project.setAccountGroup(mEnd.getValue());
						break;
					case "PM":
						project.setPm(mEnd.getValue());
						break;
					case "PL":
						project.setPl(mEnd.getValue());
						break;
					case "Category":
						project.setCategory(mEnd.getValue());
						break;
					case "Frontlog收入确认时间":
						project.setIncomeTime(mEnd.getValue());
						break;
					case "EATP预计合同签订时间":
						project.setEatpTime(ReportsUtil.paraseDate(mEnd.getValue()));
						break;
					case "项目类型":
						project.setProjectType(mEnd.getValue());
						break;
					case "工作城市":
						project.setCity(mEnd.getValue());
						break;
					case "计划开始":
						project.setPlanStart(ReportsUtil.paraseDate(mEnd.getValue()));
						break;
					case "计划结束":
						project.setPlanEnd(ReportsUtil.paraseDate(mEnd.getValue()));
						break;
					case "实际开始":
						project.setRealStart(ReportsUtil.paraseDate(mEnd.getValue()));
						break;
					case "实际结束":
						project.setRealEnd(ReportsUtil.paraseDate(mEnd.getValue()));
						break;
					case "技术名称":
						project.setTechName(mEnd.getValue());
						break;
					case "状态":
						project.setStatus(mEnd.getValue());
						break;
					case "收入确认类型":
						project.setIncomeComfirmType(mEnd.getValue());
						break;
					case "MainSub":
						project.setMainsub(mEnd.getValue());
						break;
					case "Associated Primary PID":
						project.setPid(mEnd.getValue());
						break;
					case "ONOFFTRIP":
						project.setOnofftrip(mEnd.getValue());
						break;
					case "Cost Center":
						project.setCostCenter(mEnd.getValue());
						break;
					case "ProductLine":
						project.setProductline(mEnd.getValue());
						break;
					case "ABU-CCC":
						project.setAbuccc(mEnd.getValue());
						break;
					case "Combine To CCC":
						project.setCombineccc(mEnd.getValue());
						break;
					case "IsCrossCharge":
						project.setIsCrossCharge(mEnd.getValue());
						break;
					case "备注":
						project.setRemarks(mEnd.getValue());
						break;
					case "BO":
						project.setBo(mEnd.getValue());
						break;
					case "创建人":
						project.setCreater(mEnd.getValue());
						break;
					case "创建时间":
						project.setCreatTime(ReportsUtil.paraseDate(mEnd.getValue()));
						break;
					case "更新人":
						project.setUpdater(mEnd.getValue());
						break;
					case "更新日期":
						project.setUpdateTime(ReportsUtil.paraseDate(mEnd.getValue()));
						break;
					case "RevenueType":
						project.setRevenueType(mEnd.getValue());
						break;
					case "RevRec":
						project.setRevrec(mEnd.getValue());
						break;
					case "结算币种":
						project.setCur(mEnd.getValue());
						break;
					case "SmartData":
						project.setSmartdata(mEnd.getValue());
						break;
					case "订单类型":
						project.setOrderType(mEnd.getValue());
						break;
					case "机会编号":
						project.setChangeCode(mEnd.getValue());
						break;
					case "销售":
						project.setSaler(mEnd.getValue());
						break;
					case "Contract Amount_Delivery":
						project.setDelivery(ReportsUtil.paraseDouble(mEnd.getValue()));
						break;
					case "Contract Amount_EATP":
						project.setEatp(mEnd.getValue());
						break;
					case "Contract Amount_Total":
						project.setTotal(ReportsUtil.paraseDouble(mEnd.getValue()));
						break;
					case "活动/非活动":
						project.setIsActivity(mEnd.getValue());
						break;
					case "BG Solution Type_L1":
						project.setSolutionType1(mEnd.getValue());
						break;
					case "BG Solution Type_L2":
						project.setSolutionType2(mEnd.getValue());
						break;
					case "项目管理模式":
						project.setProjectManageType(mEnd.getValue());
						break;
					case "Working Location":
						project.setWorkLoal(mEnd.getValue());
						break;
					case "Partnership":
						project.setPartnership(mEnd.getValue());
						break;
					case "Project Region":
						project.setProjectRegion(mEnd.getValue());
						break;
					case "MS Division":
						project.setMsDivision(mEnd.getValue());
						break;
					case "Pipeline PID":
						project.setPipeline(mEnd.getValue());
						break;
					case "Pipeline转正式日期":
						project.setPipelineTime(ReportsUtil.paraseDate(mEnd.getValue()));
						break;
					case "是否有里程碑信息":
						project.setIsMileInfo(mEnd.getValue());
						break;
					default:
						break;
					}
				}
				projectService.save(project);
			}
		}
		
	}
	
	public void saveMilePostData(String path){
		Map<String,List<Map<String,String>>> milePostresult = getMilepostData(path);
		for(Map.Entry<String, List<Map<String,String>>> map:milePostresult.entrySet()){
			List<Map<String,String>> list = map.getValue();
			for(Map<String,String> m:list){
				String invoiceNo = m.get("发票号码");
				String projectId = m.get("项目编号");
				if(milePostService.count(Restrictions.eq("projectNo", projectId),Restrictions.eq("invoiceNo", invoiceNo))>0){
					String sql="delete from milepost where projectNo='"+projectId+"' and invoiceNo='"+invoiceNo+"'";
					SQLQuery sqlQuery= milePostService.createSQLQuery(sql);
					sqlQuery.executeUpdate();
				}
				MilePost milePost = new MilePost();
				for(Map.Entry<String, String> mEnd:m.entrySet()){
					switch (mEnd.getKey()) {
					case "公司":
						milePost.setCorpName(mEnd.getValue());
						break;
					case "合同编号":
						milePost.setContractNo(mEnd.getValue());
						break;
					case "项目名称":
						milePost.setProjectName(mEnd.getValue());
						break;
					case "项目编号":
						milePost.setProjectNo(mEnd.getValue());
						break;
					case "PO ID":
						milePost.setPoid(mEnd.getValue());
						break;
					case "合同方":
						milePost.setContractName(mEnd.getValue());
						break;
					case "收入确认类型":
						milePost.setIncomeType(mEnd.getValue());
						break;
					case "BU":
						milePost.setBu(mEnd.getValue());
						break;
					case "DBU":
						milePost.setDbu(mEnd.getValue());
						break;
					case "部门":
						milePost.setDepartment(mEnd.getValue());
						break;
					case "是否开票":
						milePost.setIsNeedInv(mEnd.getValue());
						break;
					case "交付里程碑达成":
						milePost.setIsGetMile(mEnd.getValue());
						break;
					case "币种":
						milePost.setCur(mEnd.getValue());
						break;
					case "合同额":
						milePost.setAmt(ReportsUtil.paraseDouble(mEnd.getValue()));
						break;
					case "计划开票日期":
						milePost.setPlanTime(ReportsUtil.paraseDate(mEnd.getValue()));
						break;
					case "计划开票金额":
						milePost.setPlanAmt(ReportsUtil.paraseDouble(mEnd.getValue()));
						break;
					case "实际开票日期":
						milePost.setRealTime(ReportsUtil.paraseDate(mEnd.getValue()));
						break;
					case "实际开票金额":
						milePost.setRealAmt(ReportsUtil.paraseDouble(mEnd.getValue()));
						break;
					case "实际开票净额":
						milePost.setPureAmt(ReportsUtil.paraseDouble(mEnd.getValue()));
						break;
					case "实际税额":
						milePost.setTaxAmt(ReportsUtil.paraseDouble(mEnd.getValue()));
						break;
					case "折扣":
						milePost.setDiscount(ReportsUtil.paraseDouble(mEnd.getValue()));
						break;
					case "PM":
						milePost.setPm(mEnd.getValue());
						break;
					case "销售人":
						milePost.setSaler(mEnd.getValue());
						break;
					case "财务备注":
						milePost.setFinancalRemark(mEnd.getValue());
						break;
					case "BO备注":
						milePost.setBoRemark(mEnd.getValue());
						break;
					case "PM备注":
						milePost.setPmRemark(mEnd.getValue());
						break;
					case "发票号码":
						milePost.setInvoiceNo(mEnd.getValue());
						break;
					case "项目状态":
						milePost.setProjectStatus(mEnd.getValue());
						break;
					default:
						break;
					}
				}
				milePostService.save(milePost);
			}
		}
	}
	
	public void saveInvoiceData(String path){
		String sql="delete from invoice";
		SQLQuery sqlQuery= invoiceService.createSQLQuery(sql);
		sqlQuery.executeUpdate();
		Map<String,List<Map<String,String>>> invoiceresult = getInvoiceData(path);
		for(Map.Entry<String, List<Map<String,String>>> map:invoiceresult.entrySet()){
			List<Map<String,String>> list = map.getValue();
			for(Map<String,String> m:list){
				Invoice invoice = new Invoice();
				for(Map.Entry<String, String> mEnd:m.entrySet()){
					switch (mEnd.getKey()) {
					case "customer name":
						invoice.setCustName(mEnd.getValue());
						break;
					case "项目编号":
						invoice.setProjectId(mEnd.getValue());
						break;
					case "project_name":
						invoice.setProjectName(mEnd.getValue());
						break;
					case "End customer":
						invoice.setEndCustomer(mEnd.getValue());
						break;
					case "Account Name":
						invoice.setAccountName(mEnd.getValue());
						break;
					case "ABU'2015":
						invoice.setAbu(mEnd.getValue());
						break;
					case "DBU'2015":
						invoice.setDbu(mEnd.getValue());
						break;
					case "Department":
						invoice.setDepartment(mEnd.getValue());
						break;
					case "Project CCC":
						invoice.setProjectCCC(mEnd.getValue());
						break;
					case "发票号码":
						invoice.setInvoiceNo(mEnd.getValue());
						break;
					case "记账本位币":
						invoice.setCur(mEnd.getValue());
						break;
					case "Amount":
						invoice.setAmount(ReportsUtil.paraseDouble(mEnd.getValue()));
						break;
					default:
						break;
					}
				}
				invoiceService.save(invoice);
			}
		}
	}
	
	public void saveCollectionData(String path){
		String sql="delete from collection";
		SQLQuery sqlQuery= collectionService.createSQLQuery(sql);
		sqlQuery.executeUpdate();
		Map<String,List<Map<String,String>>> collectionresult = getCollectionData(path);
		for(Map.Entry<String, List<Map<String,String>>> map:collectionresult.entrySet()){
			List<Map<String,String>> list = map.getValue();
			for(Map<String,String> m:list){
				Collection collection = new Collection();
				for(Map.Entry<String, String> mEnd:m.entrySet()){
					switch (mEnd.getKey()) {
					case "customer name":
						collection.setCustName(mEnd.getValue());
						break;
					case "项目编号":
						collection.setProjectId(mEnd.getValue());
						break;
					case "project_name":
						collection.setProjectName(mEnd.getValue());
						break;
					case "End customer":
						collection.setEndCustomer(mEnd.getValue());
						break;
					case "Account Name":
						collection.setAccountName(mEnd.getValue());
						break;
					case "ABU'2015":
						collection.setAbu(mEnd.getValue());
						break;
					case "DBU'2015":
						collection.setDbu(mEnd.getValue());
						break;
					case "Department":
						collection.setDepartment(mEnd.getValue());
						break;
					case "Project CCC":
						collection.setProjectCCC(mEnd.getValue());
						break;
					case "GL_Date":
						collection.setGlDate(ReportsUtil.paraseDate(mEnd.getValue()));
						break;
					case "记账本位币":
						collection.setCur(mEnd.getValue());
						break;
					case "收款金额":
						collection.setAmount(ReportsUtil.paraseDouble(mEnd.getValue()));
						break;
					default:
						break;
					}
				}
				collectionService.save(collection);
			}
		}
	}
	/**
	 * 获取项目信息
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private  Map<String,List<Map<String,String>>> getProjectData(String path){
		Map<String,Integer> projectParams = Maps.newHashMap();
		projectParams.put("totalColumn", 59);//总列数-从0开始计数
		projectParams.put("start", 1);//数据启始行--从0开始计数
		projectParams.put("end", 8);//数据结束行
		projectParams.put("title", 1);//标题行
		projectParams.put("key", 5);//projectId所在列数--从0开始计数
		String projectFilePath = FileUploadUtil.uploadPath+File.separator+path;
		Workbook workbook = ExcelToHtmlUtil.getExcelWorkBook(projectFilePath);
		Sheet sheet= workbook.getSheetAt(0);
		ParaseData paraseData =  new ParaseProjectData();
		Map<String,List<Map<String,String>>> projectresult = paraseData.getData(projectParams, sheet);
		return projectresult;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private  Map<String,List<Map<String,String>>> getMilepostData(String path){
		Map<String,Integer> milepostParams = Maps.newHashMap();
		milepostParams.put("totalColumn", 28);
		milepostParams.put("start", 1);
		milepostParams.put("end", 7);
		milepostParams.put("title", 1);
		milepostParams.put("key", 3);
		String projectFilePath = FileUploadUtil.uploadPath+File.separator+path;
		Workbook workbook = ExcelToHtmlUtil.getExcelWorkBook(projectFilePath);
		Sheet sheet= workbook.getSheetAt(1);
		ParaseData paraseData = new ParaseMilePostData();
		Map<String,List<Map<String,String>>> milepostResult = paraseData.getData(milepostParams, sheet);
		return milepostResult;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private  Map<String,List<Map<String,String>>> getInvoiceData(String path){
		Map<String,Integer> invoiceParams = Maps.newHashMap();
		invoiceParams.put("totalColumn", 12);
		invoiceParams.put("start", 1);
		invoiceParams.put("end", 28);
		invoiceParams.put("title", 1);
		invoiceParams.put("key", 1);
		String projectFilePath = FileUploadUtil.uploadPath+File.separator+path;
		Workbook workbook = ExcelToHtmlUtil.getExcelWorkBook(projectFilePath);
		Sheet sheet= workbook.getSheetAt(1);
		ParaseData paraseData = new ParaseInvoiceData();
		Map<String,List<Map<String,String>>> invoiceResult = paraseData.getData(invoiceParams, sheet);
		return invoiceResult;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private  Map<String,List<Map<String,String>>> getCollectionData(String path){
		Map<String,Integer> collectionParams = Maps.newHashMap();
		collectionParams.put("totalColumn", 12);
		collectionParams.put("start", 1);
		collectionParams.put("end", 52);
		collectionParams.put("title", 1);
		collectionParams.put("key", 1);
		String projectFilePath = FileUploadUtil.uploadPath+File.separator+path;
		Workbook workbook = ExcelToHtmlUtil.getExcelWorkBook(projectFilePath);
		Sheet sheet= workbook.getSheetAt(0);
		ParaseData paraseData = new ParaseCollectionData();
		Map<String,List<Map<String,String>>> collectionResult = paraseData.getData(collectionParams, sheet);
		return collectionResult;
	}
	
}
