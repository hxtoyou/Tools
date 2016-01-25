package tools;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;



public class ParaseProjectData extends ParaseDataAbstract{

	@Override
	public Map<String, CopyOnWriteArrayList<Map<String, String>>> appendData(
			Map<String, CopyOnWriteArrayList<Map<String, String>>> map1,
			Map<String, CopyOnWriteArrayList<Map<String, String>>> map2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void export(Map<String, CopyOnWriteArrayList<Map<String, String>>> data, String path) {
		Workbook workbook = Utils.getExcelWorkBook(path);
		Sheet srcSheet= workbook.getSheetAt(0);
		Workbook workbookExcport = new SXSSFWorkbook(1000);
		Sheet sheetExport1 = workbookExcport.createSheet("name");
		/**
		 * 复制模板创建新的sxssf模板
		 */
		workbookExcport = Utils.copyWbSheet(srcSheet, sheetExport1, workbook, workbookExcport, 2);
		Sheet sheetExport = workbookExcport.getSheetAt(0);
		Map<Integer,String> headData = getHead(75, sheetExport);
		// TODO Auto-generated method stub
		Integer rowNum = 1;
		for(Map.Entry<String, CopyOnWriteArrayList<Map<String, String>>> map :data.entrySet()){
			for(Map<String,String> m:map.getValue()){
				Row row = sheetExport.createRow(rowNum);
				for(Map.Entry<Integer, String> head : headData.entrySet()){
					if(head!=null&&head.getKey()!=null){
						row.createCell(head.getKey());
						row.getCell(head.getKey()).setCellValue(m.get(head.getValue()));
					}
				}
				rowNum++;
			}
		}
		
		FileOutputStream outStream = null;
		Date date = new Date();
		String excelPath = "D://gjyw/output/result_"+date.getTime()+".xlsx";
        try {
			outStream = new FileOutputStream(excelPath);
			workbookExcport.write(outStream);
			outStream.flush();
			outStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
}
