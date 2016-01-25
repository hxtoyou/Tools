package ebills.tools.excel.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import ebills.tools.excel.entity.SystemSetting;
import ebills.tools.excel.entity.TableParams;
import ebills.tools.excel.entity.TableSetting;
import ebills.tools.excel.entity.inter.ExcelForm;
import ebills.tools.util.ExcelToHtmlUtil;
import ebills.tools.util.Page;

@Component
public class ReportsUtil {
	/**
	 * 获取模板
	 * @return
	 * @throws Exception 
	 */
	public Map<String,Integer> getWorkBookSet(SystemSetting systemSetting,HttpServletRequest request) throws Exception{
		String showType = systemSetting.getShowType();// 模板类型
		List<String> types = Arrays.asList(showType.split("&"));
		Integer setSheet = null;// 设置模板页码
		Integer templateSheet = null;// 样式模板页码
		Map<String,Integer> result = Maps.newHashMap();
		if (types.size() < 1) {
			throw new Exception("模板显示样式未设置");
		}
		String templateType = request.getParameter("templateType");// 获取模板类型参数
		Page page = new Page();
		if (Strings.isNullOrEmpty(templateType)) {
			templateType = types.get(0);
		}
		switch (templateType) {
			case "detail":
				setSheet = systemSetting.getDetailSetSheet();
				templateSheet = systemSetting.getDetailTemplateSheet();
				break;
			case "statistic":
				setSheet = systemSetting.getStatisticSetSheet();
				templateSheet = systemSetting.getStatisticTemplateSheet();
				break;
	
			default:
				throw new Exception("模板样式设置错误");
		}
		result.put("setSheet", setSheet);
		result.put("templateSheet", templateSheet);
		result.put("querySheet", systemSetting.getQuerySheet());
		return result;
		// 根据模板类型参数选择模板
	}
	/**
	 * 获取分页信息
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public Page getPageNum(TableSetting tableSetting,HttpServletRequest request) throws Exception{
		Page page = new Page();
		if ("YES".equals(tableSetting.getPagination())) {
			if (!Strings.isNullOrEmpty(request.getParameter("pageSize"))) { 
				Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
				page.setPageSize(pageSize);
			} else {
				page.setPageSize(10);
			}
			if (!Strings.isNullOrEmpty(request.getParameter("pageNumber"))) {
				Integer pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
				page.setPageNumber(pageNumber);
			} else {
				page.setPageNumber(1);
			}
		} else if ("NO".equals(tableSetting.getPagination())) {
			page.setPageSize(500);
			page.setPageNumber(1);
		} else {
			throw new Exception("模板类型设置错误");
		}
		return page;
	}
	/**
	 * 获取sql
	 * @param tableSetting
	 * @param request
	 * @return
	 */
	public Map<String,List<String>> getSql(TableSetting tableSetting,HttpServletRequest request){
		Map<String,List<String>> result = Maps.newHashMap();
		// 获取查询sql
		List<String> querySqlList = Lists.newArrayList();
		if (!Strings.isNullOrEmpty(tableSetting.getQuerySQL())) {
			querySqlList = Arrays.asList(tableSetting.getQuerySQL().split(";"));
		}
		// 获取执行前sql
		List<String> preSqlList = Lists.newArrayList();
		if (!Strings.isNullOrEmpty(tableSetting.getPreSQL())) {
			preSqlList = Arrays.asList(tableSetting.getPreSQL().split(";"));
		}
		List<String> queryTagList = Lists.newArrayList();
		if (!Strings.isNullOrEmpty(tableSetting.getQueryTag())) {
			queryTagList = Arrays.asList(tableSetting.getQueryTag().split(";"));
		}
		result.put("querySqlList", querySqlList);
		result.put("preSqlList", preSqlList);
		result.put("queryTagList", queryTagList);
		return result;
	}
	/**
	 * 获取模板详细信息
	 * @return
	 */
	public TableParams getTemplateInfo(TableSetting analyzeResult,Page page){
		TableParams tableParams = new TableParams();
		int cellnum = 0;
		cellnum = (int) Math.floor(Double.parseDouble(analyzeResult.getTotalColumnNum()));// 获取中列�?
		// 获取详细内容行数
		int detailHead = ExcelToHtmlUtil.paraseHead(analyzeResult.getDetails());// 起始位置
		int detailTail = ExcelToHtmlUtil.paraseTail(analyzeResult.getDetails());// 结束位置
		int copyRowNum = detailTail - detailHead + 1;// 源数据（详细内容）行�?
		// 每页显示的内容条�?

		int pageViewNum = (page.getPageSize() / copyRowNum) < 1 ? 1 : (page.getPageSize() / copyRowNum);
		// 显示数据的起始位�?
		int dataStartIndex = pageViewNum * (page.getPageNumber() - 1);
		// 获取表头行数
		int headsTail = ExcelToHtmlUtil.paraseTail(analyzeResult.getHeads());
		// 获取表尾行数
		int tailRowNum = 0;
		if (!Strings.isNullOrEmpty(analyzeResult.getTails())) {
			int tailHead = ExcelToHtmlUtil.paraseHead(analyzeResult.getTails());
			int tailTail = ExcelToHtmlUtil.paraseTail(analyzeResult.getTails());
			tailRowNum = tailTail - tailHead + 1;//
		}

		// table模板的行�?
		int trueRowNum = headsTail + copyRowNum + tailRowNum;// 排除空行后的table结构的真实行�?
		int contentEnd = trueRowNum - tailRowNum + copyRowNum * pageViewNum - copyRowNum;// 加上N条源数据后详细内容加上表头的行数
		int detailLineNum = contentEnd - headsTail;// 详细内容行数
		tableParams.setCellnum(cellnum);
		tableParams.setContentEnd(contentEnd);
		tableParams.setCopyRowNum(copyRowNum);
		tableParams.setDataStartIndex(dataStartIndex);
		tableParams.setDetailHead(detailHead);
		tableParams.setDetailLineNum(detailLineNum);
		tableParams.setDetailTail(detailTail);
		tableParams.setPageViewNum(pageViewNum);
		tableParams.setTailRowNum(tailRowNum);
		tableParams.setTrueRowNum(trueRowNum);
		tableParams.setHeadsTail(headsTail);
		return tableParams;
	}
	
	/**
	 * 获取查询参数对应的值
	 * @param forms
	 * @param request
	 * @return
	 */
	public Map<String,Object> getParamValue(List<ExcelForm> forms,HttpServletRequest request){
		Map<String,Object> paramValues = Maps.newHashMap();
		for (ExcelForm form : forms) {
			try {
				paramValues.put(form.getVarName(), request.getParameter(form.getVarName()));

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return paramValues;
	}
}
