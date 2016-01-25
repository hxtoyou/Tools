package tools;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.common.collect.Maps;


public class AnanisyTable {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args){
		Map<String,List<Map<String,String>>> projectData = getProjectData();
		Map<String,List<Map<String,String>>> milePostData = getMilepostData();
		Map<String,List<Map<String,String>>> collectionData = getCollectionData();
		Map<String,List<Map<String,String>>> invoiceData = getInvoiceData();
		ParaseData paraseData = new ParaseMilePostData();
		ParaseData project = new ParaseProjectData();
		paraseData.appendData(projectData, milePostData);
		ParaseData invoice = new ParaseInvoiceData();
		invoice.appendData(projectData, invoiceData);
		ParaseData collect = new ParaseCollectionData();
		collect.appendData(projectData, collectionData);
		System.out.println(projectData);
		String projectFilePath = "D:/workspace_10/tools/src/main/webapp/WEB-INF/upload/export.xlsx";
		project.export(projectData,projectFilePath);
	} 
	
	/**
	 * 获取项目信息
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String,List<Map<String,String>>> getProjectData(){
		Map<String,Integer> projectParams = Maps.newHashMap();
		projectParams.put("totalColumn", 59);//总列数-从0开始计数
		projectParams.put("start", 1);//数据启始行--从0开始计数
		projectParams.put("end", 8);//数据结束行
		projectParams.put("title", 1);//标题行
		projectParams.put("key", 5);//projectId所在列数--从0开始计数
		String projectFilePath = "D:/workspace_10/tools/src/main/webapp/WEB-INF/upload/PaymentIte.xlsx";
		Workbook workbook = Utils.getExcelWorkBook(projectFilePath);
		Sheet sheet= workbook.getSheetAt(0);
		ParaseData paraseData = new ParaseProjectData();
		Map<String,List<Map<String,String>>> projectresult = paraseData.getData(projectParams, sheet);
		return projectresult;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String,List<Map<String,String>>> getMilepostData(){
		Map<String,Integer> milepostParams = Maps.newHashMap();
		milepostParams.put("totalColumn", 28);
		milepostParams.put("start", 1);
		milepostParams.put("end", 7);
		milepostParams.put("title", 1);
		milepostParams.put("key", 3);
		String projectFilePath = "D:/workspace_10/tools/src/main/webapp/WEB-INF/upload/PaymentIte.xlsx";
		Workbook workbook = Utils.getExcelWorkBook(projectFilePath);
		Sheet sheet= workbook.getSheetAt(1);
		ParaseData paraseData = new ParaseMilePostData();
		Map<String,List<Map<String,String>>> milepostResult = paraseData.getData(milepostParams, sheet);
		return milepostResult;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String,List<Map<String,String>>> getInvoiceData(){
		Map<String,Integer> invoiceParams = Maps.newHashMap();
		invoiceParams.put("totalColumn", 12);
		invoiceParams.put("start", 1);
		invoiceParams.put("end", 28);
		invoiceParams.put("title", 1);
		invoiceParams.put("key", 1);
		String projectFilePath = "D:/workspace_10/tools/src/main/webapp/WEB-INF/upload/invoice.xlsx";
		Workbook workbook = Utils.getExcelWorkBook(projectFilePath);
		Sheet sheet= workbook.getSheetAt(1);
		ParaseData paraseData = new ParaseInvoiceData();
		Map<String,List<Map<String,String>>> invoiceResult = paraseData.getData(invoiceParams, sheet);
		return invoiceResult;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String,List<Map<String,String>>> getCollectionData(){
		Map<String,Integer> collectionParams = Maps.newHashMap();
		collectionParams.put("totalColumn", 12);
		collectionParams.put("start", 1);
		collectionParams.put("end", 52);
		collectionParams.put("title", 1);
		collectionParams.put("key", 1);
		String projectFilePath = "D:/workspace_10/tools/src/main/webapp/WEB-INF/upload/invoice.xlsx";
		Workbook workbook = Utils.getExcelWorkBook(projectFilePath);
		Sheet sheet= workbook.getSheetAt(0);
		ParaseData paraseData = new ParaseCollectionData();
		Map<String,List<Map<String,String>>> collectionResult = paraseData.getData(collectionParams, sheet);
		return collectionResult;
	}
}
