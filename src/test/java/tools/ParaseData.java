package tools;


import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public interface ParaseData<T> {
	Map<String,CopyOnWriteArrayList<Map<String,String>>> getData(T map,Sheet sheet);
	Map<Integer,String> getHead(Integer totalColumn,Sheet sheet);
	String getCellVal(Row row,Integer k);
	Map<String,CopyOnWriteArrayList<Map<String,String>>> appendData(Map<String,CopyOnWriteArrayList<Map<String,String>>> map1,Map<String,CopyOnWriteArrayList<Map<String,String>>> map2);
	void export(Map<String,CopyOnWriteArrayList<Map<String,String>>> data,String path);
}
