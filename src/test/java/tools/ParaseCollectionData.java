package tools;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ParaseCollectionData extends ParaseDataAbstract{

	@Override
	public Map<String, CopyOnWriteArrayList<Map<String, String>>> appendData(
			Map<String, CopyOnWriteArrayList<Map<String, String>>> projectData,
			Map<String, CopyOnWriteArrayList<Map<String, String>>> collectionData) {
		// TODO Auto-generated method stub
		Map<Integer,String> map = Maps.newHashMap();
		map.put(9, "GL_Date");
		map.put(10, "记账本位币");
		map.put(11, "收款金额");
		for (Map.Entry<String,CopyOnWriteArrayList<Map<String,String>>> entry : projectData.entrySet()) {
			for(Map<String,String> projectMap:entry.getValue()){
				Double realAmt = paraseDouble(projectMap.get("计划开票金额"));
				if(collectionData.get(entry.getKey())!=null&&collectionData.get(entry.getKey()).size()>0){
					List<Map<String,String>> invoiceList = collectionData.get(entry.getKey());
//					List<Map<String,String>> invoiceTemp = Lists.newArrayList();
					CopyOnWriteArrayList<Map<String,String>> invoiceTemp = new CopyOnWriteArrayList<Map<String,String>>();

					/**
					 * 获取本项目所有发票数据
					 */
					for(int i=0;i<invoiceList.size();i++){
						Map<String,String> tempMap = Maps.newHashMap();
						Map<String,String> m = invoiceList.get(i);
						if("是".equals(m.get("数据是否异常"))){
							continue;
						}
						Double invoiceAmt = paraseDouble(m.get("收款金额"));
						Double diffAmt  = Math.abs(realAmt-invoiceAmt);
						String invoiceCode = m.get("发票号码");
						String invoiceCur = m.get("记账本位币");
						String GL_Date = m.get("GL_Date");
						tempMap.put("invoiceCode", invoiceCode);
						tempMap.put("invoiceCur", invoiceCur);
						tempMap.put("invoiceAmt", invoiceAmt.toString());
						tempMap.put("GL_Date", GL_Date);
						tempMap.put("diffAmt", diffAmt.toString());
						tempMap.put("projectKey", entry.getKey());
						invoiceTemp.add(tempMap);
					}
					List<String> negaAmtList = Lists.newArrayList();
					/**
					 * 获取所有为负的发票号
					 */
					for(Map<String,String> m2: invoiceTemp){
						if(paraseDouble(m2.get("invoiceAmt"))<0){
							negaAmtList.add(m2.get("invoiceAmt"));
							invoiceTemp.remove(m2);
						}
					}
					/**
					 * 删除所有负数的发票
					 */
					for(Map<String,String> m2: invoiceTemp){
						for(String amt : negaAmtList){
							if(m2.get("invoiceAmt").equals(String.valueOf(Math.abs(paraseDouble(amt))))){
								invoiceTemp.remove(m2);
							}
						}
					}
					Double difAmt = null;
					Map<String,String> deleteM = null;
					for(Map<String,String> m2: invoiceTemp){
						Double value = paraseDouble(m2.get("diffAmt"));
						if(difAmt!=null&&value!=null){
							if(value<difAmt){
								projectMap.put("收款金额", m2.get("invoiceAmt"));
								projectMap.put("收款时间", m2.get("GL_Date"));
								projectMap.put("记账本位币", m2.get("invoiceCur"));
								projectMap.put("收款差异额", m2.get("diffAmt"));
								deleteM = m2;
							}
						}else{
							projectMap.put("收款金额", m2.get("invoiceAmt"));
							projectMap.put("收款时间", m2.get("GL_Date"));
							projectMap.put("记账本位币", m2.get("invoiceCur"));
							projectMap.put("收款差异额", m2.get("diffAmt"));
							difAmt = value;
							deleteM = m2;
						}
					}
					invoiceTemp.remove(deleteM);
					for(Map<String,String> m3: invoiceTemp){
						Map<String,String> newMap = Maps.newHashMap();
						newMap.putAll(projectMap);
						newMap.put("数据是否异常", "是");
						newMap.put("收款金额", m3.get("invoiceAmt"));
						newMap.put("收款时间", m3.get("GL_Date"));
						newMap.put("记账本位币", m3.get("invoiceCur"));
						newMap.put("收款差异额", m3.get(""));
						newMap.put("发票差异额", m3.get(""));
						newMap.put("计划开票日期", m3.get(""));
						newMap.put("计划开票金额", m3.get(""));
						newMap.put("实际开票净额", m3.get(""));
						newMap.put("实际税额", m3.get(""));
						newMap.put("财务备注", m3.get(""));
						newMap.put("BO备注", m3.get(""));
						newMap.put("PM备注", m3.get(""));
						entry.getValue().add(newMap);
					}
				}
			}
		}
		return projectData;
	}

	@Override
	public void export(Map<String, CopyOnWriteArrayList<Map<String, String>>> data, String path) {
		// TODO Auto-generated method stub
		
	}


}
