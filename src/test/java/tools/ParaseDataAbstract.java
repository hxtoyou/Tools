package tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public abstract class ParaseDataAbstract implements ParaseData<Map<String,Integer>>{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String,CopyOnWriteArrayList<Map<String,String>>> getData(Map<String,Integer> map,Sheet sheet) {
		// TODO Auto-generated method stub
		Integer totalColumn = map.get("totalColumn");
		Integer start = map.get("start");
		Integer end = map.get("end");
		Integer key = map.get("key");
		Map<String,CopyOnWriteArrayList<Map<String,String>>> result = Maps.newHashMap();
		Map<Integer,String>  headData = getHead(totalColumn,sheet);
		for(int i=start;i<=end;i++){
			Row row = sheet.getRow(i);
			if (row != null) {
				String projectKey = getCellVal(row, key);
				if(Strings.isNullOrEmpty(projectKey)){
					continue;
				}
				Map<String,String> mapTemp = Maps.newHashMap();
				for (int k = 0; k < totalColumn; k++) {
					String value = getCellVal(row,k);
					mapTemp.put(headData.get(k), value);
				}
				if(result.get(projectKey)!=null){
					result.get(projectKey).add(mapTemp);
				}else{
					CopyOnWriteArrayList<Map<String,String>> list = new CopyOnWriteArrayList();
					list.add(mapTemp);
					result.put(projectKey, list);
				}
			}
		}
		return result;
	}
	/**
	 * 获取head数据
	 * @param totalColumn
	 * @param workBook
	 * @return
	 */
	public Map<Integer,String> getHead(Integer totalColumn,Sheet sheet){
		Map<Integer,String> headData = Maps.newHashMap();
		Row row = sheet.getRow(0);
		for(int i=0;i<totalColumn;i++){
			String value = getCellVal(row,i);
			headData.put(i, value);
		}
		return headData;
	}
	
	/**
	 * 获取单元格数据
	 * @param row
	 * @param k
	 * @return
	 */
	public String getCellVal(Row row,Integer k){
		String value = "";
		if (row.getCell(k) != null && !"null".equals(row.getCell(k))) {
			try {
				int type = row.getCell(k).getCellType();
				switch (type) {
				case 0:
					if(HSSFDateUtil.isCellDateFormatted(row.getCell(k))){
						Date d = row.getCell(k).getDateCellValue();
						DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
						value = formater.format(d);
					}else{
						value = String.valueOf(row.getCell(k).getNumericCellValue());
					}
					break;
				case 1:
					value = String.valueOf(row.getCell(k).getRichStringCellValue());
					break;
				case 4:
					value = String.valueOf(row.getCell(k).getBooleanCellValue());
				default:
					value = String.valueOf(row.getCell(k).getRichStringCellValue());
					break;
				}
			} catch (IllegalStateException e) {
				value = String.valueOf(row.getCell(k).getBooleanCellValue());
				// TODO: handle exception
			}
		} else if ("null".equals(row.getCell(k))) {
			value = "";
		} else {
			value = "";
		}
		return value;
	}
	/**
	 * 金额转换
	 * @param value
	 * @return
	 */
	public Double paraseDouble(String value){
		Double result = 0d;
		if (!Strings.isNullOrEmpty(value)) {
			result = Double.parseDouble(value);
		}
		return result;
	}
}
