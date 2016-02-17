package ebills.tools.excel.service;

import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.collect.Maps;

public class ParaseMilePostData extends ParaseDataAbstract{
	public Map<String,CopyOnWriteArrayList<Map<String,String>>> appendData(Map<String,CopyOnWriteArrayList<Map<String,String>>> projectData,Map<String,CopyOnWriteArrayList<Map<String,String>>> milePostData){
		Map<Integer,String> map = Maps.newHashMap();
		map.put(19, "计划开票日期");
		map.put(20, "计划开票金额");
		map.put(21, "实际开票日期");
		map.put(22, "实际开票金额");
		map.put(23, "实际开票净额");
		map.put(24, "实际税额");
		map.put(28, "财务备注");
		map.put(29, "BO备注");
		map.put(30, "PM备注");
		for (Map.Entry<String,CopyOnWriteArrayList<Map<String,String>>> entry : projectData.entrySet()) {
			if(milePostData.get(entry.getKey())!=null&&milePostData.get(entry.getKey()).size()>0){
				CopyOnWriteArrayList<Map<String,String>> milePostList = milePostData.get(entry.getKey());
					for(int i=0;i<milePostList.size();i++){
						Map<String,String> m = milePostList.get(i);
						if(i==0){
							for(Map.Entry<Integer, String> colsMap:map.entrySet()){
								entry.getValue().get(0).put(colsMap.getValue(), m.get(colsMap.getValue()));
								entry.getValue().get(0).put("是否有里程碑信息","有");
							}
						}else{
							Map<String,String> newMap = Maps.newHashMap();
							newMap.putAll(entry.getValue().get(0));
							for(Map.Entry<Integer, String> colsMap:map.entrySet()){
								newMap.put(colsMap.getValue(), m.get(colsMap.getValue()));
								newMap.put("是否有里程碑信息","有");
							}
							entry.getValue().add(newMap);
						}
					}
					
			}else{
				entry.getValue().get(0).put("是否有里程碑信息","无");
			}
		}
		return projectData;
	}


	@Override
	public void export(Map<String, CopyOnWriteArrayList<Map<String, String>>> data, String path) {
		// TODO Auto-generated method stub
		
	}

}
