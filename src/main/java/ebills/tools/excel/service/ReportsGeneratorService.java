package ebills.tools.excel.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import ebills.tools.excel.dao.ExcelDAO;
import ebills.tools.excel.entity.ExcelFormEntity;
import ebills.tools.excel.entity.ExcelInputType;
import ebills.tools.excel.entity.HtmlRowStructure;
import ebills.tools.excel.entity.HtmlStructure;
import ebills.tools.excel.entity.HtmlTdStructure;
import ebills.tools.excel.entity.SystemSetting;
import ebills.tools.excel.entity.TableParams;
import ebills.tools.excel.entity.TableSetting;
import ebills.tools.excel.entity.inter.ExcelForm;
import ebills.tools.excel.extend.ExcelExtendPublic;
import ebills.tools.util.ExcelToHtmlUtil;
import ebills.tools.util.Page;

/**
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间�?015�?�?�?下午4:48:18
 * 
 */
@Component
public class ReportsGeneratorService implements ReportsGenerator<Map<String,Object>> {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ExcelDAO excelDAO;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private ExcelExtendPublic excelExtendPublic;
	@Autowired
	private ReportsUtil reportsUtil;
	private HttpServletResponse response;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String,Object> generateReport(Map<String,Object> context) throws Exception {

		// HttpServletResponse response =
		// (HttpServletResponse)context.getValue(EAPConstance.SERVLET_REQSPONSE);
		excelExtendPublic.execute(context);
		String fileName = request.getParameter("templateName");// 模板名称
		Workbook workbook = ExcelToHtmlUtil.getExcelWorkBook(fileName);
		/**
		 * 解析excel系统设置模板配置信息
		 */
		Sheet sheet = workbook.getSheetAt(0);// 系统设置模板
		int systtemSetrownum = sheet.getLastRowNum();
		SystemSetting systemSetResult = ExcelToHtmlUtil.analyzeSystemSetting(sheet, systtemSetrownum);
		Map<String,Integer> sheetPage =  reportsUtil.getWorkBookSet(systemSetResult, request);
		Sheet sheet1 = workbook.getSheetAt(sheetPage.get("setSheet") - 1);// 配置sheet
		Sheet sheet2 = workbook.getSheetAt(sheetPage.get("templateSheet") - 1);// 模板sheet
		Sheet sheet3 = workbook.getSheetAt(sheetPage.get("querySheet") - 1);// 查询参数sheet
		List<ExcelForm> forms = getExcelForm(sheet3);

		/**
		 * 模板配置页面条件设置sheet1
		 */
		
		TableSetting analyzeResult = ExcelToHtmlUtil.getExcelAnalyze(sheet1);
		/**
		 * 根据是否分页设置查询数据结果的多少，不分页最多显�?000条数�?
		 */
		Page page = reportsUtil.getPageNum(analyzeResult, request);
		/**
		 * 获取sql集
		 */
		Map<String,List<String>> sqlResult = reportsUtil.getSql(analyzeResult, request);
		/**
		 * 获取详细设置
		 */
		TableParams tableParams = reportsUtil.getTemplateInfo(analyzeResult, page);
		/**
		 * 获取参数与值
		 */
		Map<String, Object> paramValues = reportsUtil.getParamValue(forms, request);
		long start = System.currentTimeMillis();
		List<CellRangeAddress> addresses = ExcelToHtmlUtil.getMergeRegons(sheet2);// �?��合并单元�?
		// 遍历合并单元格，标记被合并的单元�?
		HtmlStructure htmlStructure = createByRegin(addresses,tableParams);
		// 向单元格填充结构数据并返回宽�?
		Double tableWidth = fillStructureData(htmlStructure, tableParams, sheet2);
		// table宽度
		htmlStructure.setTableWidth(tableWidth / tableParams.getTrueRowNum());
		// 删除被合并的单元�?
		tableStyle(htmlStructure, addresses,tableParams);
		// 根据要显示的数据行数扩展htmlStructure对象行数
		expendHtmlStructure(htmlStructure, tableParams);
		 System.out.println("耗时: " + (System.currentTimeMillis() - start)+"毫秒");
		// 执行sql返回数据
		// 执行用户自定义java代码
		doPreJavaCode(analyzeResult.getJavaClass(), context);
		// 创建视图
		doPreSQL(sqlResult.get("preSqlList"), paramValues);
		// 返回数据
		page = getResult(page, htmlStructure,sqlResult, tableParams,analyzeResult, paramValues);
		// 用户自定义java类创建的表或者数据清�?
		doClean(analyzeResult.getJavaClass(),context);
		page.setPageSize(tableParams.getPageViewNum());
		Map<String, String> resultMap = Maps.newHashMap();
		try {
			resultMap.put("data", JSON.toJSONString(page));
			resultMap.put("showType", systemSetResult.getShowType());
			resultMap.put("tableTitle", String.valueOf(tableParams.getHeadsTail()));
			resultMap.put("pagination", analyzeResult.getPagination());
			String resultOut = JSON.toJSONString(resultMap);
			context.put("output", resultOut);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return context;
	}

	/**
	 * 执行sql填充数据
	 * 
	 * @param htmlStructure
	 * @param querySqlList
	 * @param queryTagList
	 * @param params
	 * @param dataStartIndex
	 * @param pageViewNum
	 * @param map
	 * @param detailLineNum
	 * @param copyRowNum
	 * @param headsTail
	 * @return
	 */
	private Page<HtmlStructure> getResult(Page<HtmlStructure> page, HtmlStructure htmlStructure,Map<String,List<String>> sqlResult,TableParams tableParams,TableSetting tableSetting, Map<String,Object> paramValues) {
		int totalSize = 0;
		int resultSize = 0;
		String dataBaseType="";
		dataBaseType = "mysql";
		List<String> querySqlList = sqlResult.get("querySqlList");
		List<String> queryTagList = sqlResult.get("queryTagList");
		Integer dataStartIndex = tableParams.getDataStartIndex();
		Integer pageViewNum = tableParams.getPageViewNum();
		Integer copyRowNum = tableParams.getCopyRowNum();
		Integer tailRowNum = tableParams.getTailRowNum();
		Integer headsTail = tableParams.getHeadsTail();
		for (int y = 0; y < querySqlList.size(); y++) {
			if (!Strings.isNullOrEmpty(querySqlList.get(y))) {
				String resultTag = queryTagList.get(y);
				List<Map<String, Object>> results = Lists.newArrayList();
				List<Map<String, Object>> totalResults = Lists.newArrayList();
				String sql = "";
				String totalSql;
				// 如果是内容项，则数据库分页查�?
				if ("YES".equals(tableSetting.getPagination())) {
					if (resultTag.matches(".*DATA_.*")) {
						totalSql = "SELECT COUNT(*) FROM(" + querySqlList.get(y) + ") as total";
						totalResults = excelDAO.queryBySqlExcel(totalSql, paramValues, resultTag);
						if(totalResults.size()>0){
							for (Map.Entry<String, Object> entry : totalResults.get(0).entrySet()) {
								if (totalSize < Integer.parseInt(entry.getValue().toString())) {// 取最大的totalSize
									totalSize = Integer.parseInt(entry.getValue().toString());
								}
							}
						}
						if(dataBaseType!=null&&dataBaseType.equals("db2")){
							sql =  "select * from ("+querySqlList.get(y)+") a where a.row_number>"+dataStartIndex+" fetch first "+pageViewNum+" rows only";
						}else{
							sql = querySqlList.get(y) + " limit "+dataStartIndex+","+pageViewNum;
						}
						results = excelDAO.queryBySqlExcel(sql, paramValues, resultTag);
					} else if (resultTag.matches(".*DATASUM_.*")) {
						sql = querySqlList.get(y);
						results = excelDAO.queryBySqlExcel(sql, paramValues, resultTag);
					}
				} else if ("NO".equals(tableSetting.getPagination())) {
					sql = querySqlList.get(y);
					results = excelDAO.queryBySqlExcel(sql, paramValues, resultTag);
					totalSql = "SELECT COUNT(*) FROM(" + querySqlList.get(y) + ") as total";
					totalResults = excelDAO.queryBySqlExcel(totalSql, paramValues, resultTag);
					if(totalResults.size()>0){
						for (Map.Entry<String, Object> entry : totalResults.get(0).entrySet()) {
							if (totalSize < Integer.parseInt(entry.getValue().toString())) {// 取最大的totalSize
								totalSize = Integer.parseInt(entry.getValue().toString());
							}
						}
					}
				}
				if (resultSize < results.size()) {
					resultSize = results.size();
				}
				// 谈查询结果只有一行的时�?（大多数时�?是统计查询），则直接遍历�?��excel单元格向里面填充数据
				if (results.size() <= 1 && results.size() > 0) {
					inputStatisticData(results, htmlStructure, tableParams);
				} else if (results.size() > 1) {
					inputData(results, htmlStructure, tableParams);
				}
			}
		}
		Integer delRowNum = 0;
		if (resultSize < 1) {
			delRowNum = headsTail + copyRowNum;
		} else {
			delRowNum = resultSize * copyRowNum + headsTail;
		}
		// Integer delRowNum = resultSize * copyRowNum + headsTail+copyRowNum;
		int size = htmlStructure.getHtmlRowStructure().size();
		Iterator<HtmlRowStructure> rowIter = htmlStructure.getHtmlRowStructure().iterator();
		int rowCount = 0;
		while (rowIter.hasNext()) {
			rowCount++;
			rowIter.next();
			if (totalSize == 0) {
				if (rowCount > (delRowNum + 3) && rowCount < (size - tailRowNum + 1)) {
					rowIter.remove();// 这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException
				}
			} else {
				if (rowCount > delRowNum && rowCount < (size - tailRowNum + 1)) {
					rowIter.remove();// 这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException
				}
			}
		}
		page.setTotalCount(totalSize);
		page.setPageSize(pageViewNum);
		List<HtmlStructure> resultHtmlStructure = Lists.newArrayList();
		resultHtmlStructure.add(htmlStructure);
		page.setResult(resultHtmlStructure);
		return page;
	}

	/**
	 * 填充统计数据
	 * 
	 * @param results
	 * @param htmlStructure
	 * @param map
	 */
	private void inputStatisticData(List<Map<String, Object>> results, HtmlStructure htmlStructure, TableParams tableParams) {
		
		for (HtmlRowStructure addRowData : htmlStructure.getHtmlRowStructure()) {
			for (HtmlTdStructure tdStructure : addRowData.getHtmlTdStructure()) {
					Map<String, Object>  map = results.get(0);
					if (!Strings.isNullOrEmpty(tdStructure.getValue())) {
						try {
							String content = tdStructure.getValue();
							String regex = "\\{(.*?)\\}";
							Pattern p = Pattern.compile(regex);
							Matcher m = p.matcher(content);
							m.find();
							String n="";
							String regx = "{"+m.group(1)+"}";
							if(!Strings.isNullOrEmpty(String.valueOf(map.get(m.group(1))))&&map.get(m.group(1))!=null){
								String regexValue=map.get(m.group(1)).toString();
								n = content.replace(regx, regexValue);
								tdStructure.setValue(n);
							}
						} catch (IllegalStateException e) {
//							tdStructure.setValue(entry.getValue());
							// TODO: handle exception
						}
				}
			}
		}
		 
	}

	/**
	 * 填充主数�?
	 * 
	 * @param detailLineNum
	 * @param copyRowNum
	 * @param headsTail
	 * @param results
	 * @param pageViewNum
	 * @param htmlStructure
	 * @param map
	 */
	private void inputData(	List<Map<String, Object>> results,HtmlStructure htmlStructure,TableParams tableParams) {
		int n = 0;
		int l = 0;
		Integer copyRowNum = tableParams.getCopyRowNum();
		for (int q = 0; q < tableParams.getDetailLineNum() / copyRowNum; q++) {// 取N条data,如果表格的源数据是N行，那么当一行处理只取一条数�?
			if (results.size() - n >= (tableParams.getPageViewNum() - q)) {// 当结果集不能填满整个表格的时候判�?
				for (int a = 0; a < copyRowNum; a++) {
					HtmlRowStructure addRowData = htmlStructure.getHtmlRowStructure().get(
							tableParams.getHeadsTail() + l * copyRowNum + a);// 取出�?��的表格结构数�?
					List<HtmlTdStructure> htmlTdStructures = addRowData.getHtmlTdStructure();
					Map<String,Object> resultMap = results.get(n);
						for (int s = 0; s < htmlTdStructures.size(); s++) {
							if (!Strings.isNullOrEmpty(htmlTdStructures.get(s).getValue())) {
								try {
									String content = htmlTdStructures.get(s).getValue();
									String regex = "\\{(.*?)\\}";
									Pattern p = Pattern.compile(regex);
									Matcher m = p.matcher(content);
									m.find();
									String result="";
									String regx = "{"+m.group(1)+"}";
									if(!Strings.isNullOrEmpty(String.valueOf(resultMap.get(m.group(1))))&&resultMap.get(m.group(1))!=null){
										String regexValue=(resultMap.get(m.group(1))).toString();
										result = content.replace(regx, regexValue);
										htmlTdStructures.get(s).setValue(result);
									}
								} catch (IllegalStateException e) {
								}
							}
						}
					}
				n++;
				l++;
			}
		}
	}

	/**
	 * 填充结构数据
	 * 
	 * @param htmlStructure
	 * @param trueRowNum
	 * @param cellnum
	 * @param sheet
	 * @return
	 */
	private Double fillStructureData(HtmlStructure htmlStructure,TableParams tableParams, Sheet sheet) {
		Double tableWidth = 0d;
		List<HtmlRowStructure> htmlRowStructures = htmlStructure.getHtmlRowStructure();
		for (int j = 0; j < tableParams.getTrueRowNum(); j++) {
			if (sheet.getRow(j) != null) {
				for (int k = 0; k < tableParams.getCellnum(); k++) {
					String value = "";
					if (sheet.getRow(j).getCell(k) != null && !"null".equals(sheet.getRow(j).getCell(k))) {
						try {
							value = sheet.getRow(j).getCell(k).getRichStringCellValue().toString();
						} catch (IllegalStateException e) {
							value = String.valueOf(sheet.getRow(j).getCell(k).getNumericCellValue());
							// TODO: handle exception
						}
					} else if ("null".equals(sheet.getRow(j).getCell(k))) {
						value = "";
					} else {
						value = "";
					}
					ExcelToHtmlUtil.setColor(j, k, sheet, htmlRowStructures);// 设置颜色、边框�?背景�?
					Short height = sheet.getRow(j).getHeight();
					htmlRowStructures
							.get(j)
							.getHtmlTdStructure()
							.get(k)
							.setValue((Strings.isNullOrEmpty(value.trim()) || "null".equals(value.trim())) ? "" : value);
					htmlRowStructures.get(j).getHtmlTdStructure().get(k)
							.setHeight(String.valueOf((height / 20) * 1.2368));
					htmlRowStructures.get(j).getHtmlTdStructure().get(k)
							.setWidth(String.valueOf((sheet.getColumnWidth(k) / 256) * 7.425));
					tableWidth += (sheet.getColumnWidth(k) / 256) * 7.425;
				}
			}
		}
		return tableWidth;
	}

	/**
	 * 根据要显示的页数扩展html机构对象
	 * 
	 * @param htmlStructure
	 * @param copyRowNum
	 * @param pageViewNum
	 * @param detailHead
	 * @param detailTail
	 * @return
	 */
	private List<HtmlRowStructure> expendHtmlStructure(HtmlStructure htmlStructure,TableParams tableParams) {
		List<HtmlRowStructure> htmlRowStructures = htmlStructure.getHtmlRowStructure();
		List<HtmlRowStructure> copyRow = htmlRowStructures.subList(tableParams.getDetailHead() - 1, tableParams.getDetailTail());
		// 复制内容行的表格结构
		List<HtmlRowStructure> pasteRow = new ArrayList<HtmlRowStructure>(tableParams.getCopyRowNum() * tableParams.getPageViewNum());
		for (int e = 1; e < tableParams.getPageViewNum(); e++) {
			try {
				for (HtmlRowStructure rowStructure : copyRow) {
					HtmlRowStructure copyStructure = new HtmlRowStructure();
					List<HtmlTdStructure> tdS = Lists.newArrayList();
					for (HtmlTdStructure tdStructure : rowStructure.getHtmlTdStructure()) {
						HtmlTdStructure copyTdStructure = new HtmlTdStructure();
						copyTdStructure.setColspan(tdStructure.getColspan());
						copyTdStructure.setRowspan(tdStructure.getRowspan());
						copyTdStructure.setTag(tdStructure.getTag());
						copyTdStructure.setValue(tdStructure.getValue());
						copyTdStructure.setHeight(tdStructure.getHeight());
						copyTdStructure.setWidth(tdStructure.getWidth());
						copyTdStructure.setBg_color(tdStructure.getBg_color());
						copyTdStructure.setBorder_bottom(tdStructure.getBorder_bottom());
						copyTdStructure.setBorder_bottom_color(tdStructure.getBorder_bottom_color());
						copyTdStructure.setBorder_left(tdStructure.getBorder_left());
						copyTdStructure.setBorder_left_color(tdStructure.getBorder_left_color());
						copyTdStructure.setBorder_right(tdStructure.getBorder_right());
						copyTdStructure.setBorder_right_color(tdStructure.getBorder_right_color());
						copyTdStructure.setBorder_top(tdStructure.getBorder_top());
						copyTdStructure.setBorder_top_color(tdStructure.getBorder_top_color());
						copyTdStructure.setFont_color(tdStructure.getFont_color());
						copyTdStructure.setFont_size(tdStructure.getFont_size());
						copyTdStructure.setIsBold(tdStructure.getIsBold());
						copyTdStructure.setFontFamily(tdStructure.getFontFamily());
						copyTdStructure.setVerticalAlign(tdStructure.getVerticalAlign());
						copyTdStructure.setHorizontalAlign(tdStructure.getHorizontalAlign());
						tdS.add(copyTdStructure);
					}
					copyStructure.setHtmlTdStructure(tdS);
					pasteRow.add(copyStructure);
					// pasteRow.add(rowStructure.clone());
				}
			} catch (Exception e2) {
				// TODO: handle exception
			} finally {
			}
		}
		htmlRowStructures.addAll(tableParams.getDetailTail(), pasteRow);
		return htmlRowStructures;
	}

	/**
	 * 根据表格合并情况设置整个表格每行的行数和列数，表格的合成情况在htmlTDstructure表中的tag标志位来表示是否被合�?
	 * 
	 * @param addresses
	 *            �?��合并�?
	 * @param headers
	 *            表格头行�?
	 * @param details
	 *            表格详细数据行数
	 * @param tails
	 *            表格统计栏行�?
	 * @param row
	 *            表格总行�?
	 * @param cells
	 *            表格总列行数
	 */
	private HtmlStructure createByRegin(List<CellRangeAddress> addresses,TableParams tableParams) {
		HtmlStructure htmlStructure = new HtmlStructure();
		Integer row = tableParams.getTrueRowNum();
		Integer cell = tableParams.getCellnum();
		htmlStructure.setRowNum(row);
		htmlStructure.setColumn(cell);
		// 初始化html表的数据结构
		List<HtmlRowStructure> htmlRowStructures = Lists.newArrayList();
		for (int k = 0; k <= row; k++) {
			HtmlRowStructure htmlStructureTemp = new HtmlRowStructure();
			List<HtmlTdStructure> htmlTdStructures = Lists.newArrayList();
			for (int h = 0; h < cell; h++) {
				HtmlTdStructure htmlTdStructureTemp = new HtmlTdStructure();
				htmlTdStructures.add(htmlTdStructureTemp);
			}
			htmlStructureTemp.setHtmlTdStructure(htmlTdStructures);
			htmlStructureTemp.setCellsNum(cell);
			htmlRowStructures.add(htmlStructureTemp);
		}
		// 标记被合并的单元格，计算每一行有多少个单元格
		for (int i = 0; i <= row; i++) {

			HtmlRowStructure htmlRowStructure = htmlRowStructures.get(i);
			int cellNum = htmlRowStructure.getCellsNum();
			for (CellRangeAddress address : addresses) {
				int firstColumn = address.getFirstColumn();
				int lastColumn = address.getLastColumn();
				int firstRow = address.getFirstRow();
				int lastRow = address.getLastRow();
				int reginRow = lastRow - firstRow;
				int reginCell = lastColumn - firstColumn;
				if (lastColumn >= cell) {
					System.out.print("列数设置错误");
				}
				if (firstRow > i) {
					continue;
				} else if (firstRow == i) {
					if (reginCell > 0) {
						cellNum -= reginCell;
						if (reginCell > 0) {
							for (int y = 1; y <= reginCell; y++) {
								htmlRowStructures.get(i).getHtmlTdStructure().get(firstColumn + y).setTag(true);
							}
						}
					}
					// 但出现合并行的时候，下一行需要生成的表格�?��减一，再加上合并的列数并且把被合并的单元格标记为true
					if (reginRow > 0) {
						for (int j = 1; j <= reginRow; j++) {
							int otherRowCellnum = htmlRowStructures.get(i + j).getCellsNum() - 1 - reginCell;
							htmlRowStructures.get(i + j).setCellsNum(otherRowCellnum);
							htmlRowStructures.get(i + j).getHtmlTdStructure().get(firstColumn).setTag(true);
							if (reginCell > 0) {
								for (int x = 1; x <= reginCell; x++) {
									htmlRowStructures.get(i + j).getHtmlTdStructure().get(firstColumn + x).setTag(true);
								}
							}
						}
					}
				} else {

				}
			}
			htmlRowStructure.setRowNum(i);
			htmlRowStructure.setCellsNum(cellNum);
			htmlRowStructures.set(i, htmlRowStructure);
		}
		htmlStructure.setHtmlRowStructure(htmlRowStructures);
		return htmlStructure;
	}

	/**
	 * 删除被标记的单元�?
	 * 
	 * @param htmlStructure
	 * @param addresses
	 * @param headers
	 * @param details
	 * @param tails
	 * @param row
	 * @param cell
	 * @return
	 */
	private HtmlStructure tableStyle(HtmlStructure htmlStructure, List<CellRangeAddress> addresses,TableParams tableParams) {
		List<HtmlRowStructure> htmlRowStructures = htmlStructure.getHtmlRowStructure();
		for (CellRangeAddress address : addresses) {
			int firstColumn = address.getFirstColumn();
			int lastColumn = address.getLastColumn();
			int firstRow = address.getFirstRow();
			int lastRow = address.getLastRow();
			int reginRow = lastRow - firstRow;
			int reginCell = lastColumn - firstColumn;
			List<HtmlTdStructure> tdStructures = htmlRowStructures.get(firstRow).getHtmlTdStructure();
			if (reginRow > 0) {
				tdStructures.get(firstColumn).setRowspan(reginRow + 1);
			}
			if (reginCell > 0) {
				tdStructures.get(firstColumn).setColspan(reginCell + 1);
			}
		}
		// 根据表尾行数，删除剩余的row
		int tailsize = tableParams.getTrueRowNum();
		int rowSize = htmlRowStructures.size();
		for (int delRow = tailsize; delRow < rowSize; delRow++) {
			htmlRowStructures.remove(tailsize);
		}
		// 删除被标记为被合并的单元格，由于�?��遍历删除不掉某一些单元格，遍历两次�?待优�?
		for (HtmlRowStructure htmlRowStructure : htmlRowStructures) {
			List<HtmlTdStructure> htmlTdStructures = htmlRowStructure.getHtmlTdStructure();
			Iterator<HtmlTdStructure> tdIter = htmlTdStructures.iterator();
			while (tdIter.hasNext()) {
				HtmlTdStructure td = tdIter.next();
				if (td.getTag() != null) {
					tdIter.remove();// 这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException
				}
			}
		}
		return htmlStructure;
	}

	/**
	 * excel查询参数解析
	 * 
	 * @param sheet
	 * @return
	 */
	public List<ExcelForm> getExcelForm(Sheet sheet) {
		int sheet2LastRow = sheet.getLastRowNum();
		List<ExcelForm> inputs = Lists.newArrayList();
		for (int i = 1; i <= sheet2LastRow; i++) {
			Row row = sheet.getRow(i);
			ExcelForm configItem = new ExcelFormEntity();
			if ((row.getCell(0) != null) && (!Strings.isNullOrEmpty(row.getCell(0).toString()))
					&& (!Strings.isNullOrEmpty(row.getCell(2).toString()))) {
				configItem.setName(row.getCell(0).toString());
				configItem.setContent(row.getCell(1).toString());
				configItem.setVarName(row.getCell(2).toString());
				configItem
						.setClassType(!Strings.isNullOrEmpty(ExcelInputType.getValue(row.getCell(3).toString())) ? ExcelInputType
								.getValue(row.getCell(3).toString()) : "easyui-textbox");
				configItem.setComboAttr(row.getCell(4).toString());
				configItem.setDataOption(row.getCell(5).toString());
				configItem.setAcurrate(row.getCell(5).toString());
				inputs.add(configItem);
			}
		}
		return inputs;
	}
	/**
	 * 获取模板路径
	 * @param name
	 * @return
	 */
	public String getFilePath(String name) {
		String sql = "select cardpath from ptcards where cardno=?cardno or ?cardno is null";
		Map<String,Object> inputMap = Maps.newHashMap();
		inputMap.put("cardno", name);
		String path = "";
		try {
			List<Map<String, Object>> rtnList = excelDAO.queryBySql(sql,inputMap);
			path = (String) rtnList.get(0).get("cardpath");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
	
	@Override	
	public void export(Map<String,Object> context,HttpServletResponse response) throws Exception {
		String fileName = request.getParameter("templateName");// 模板名称
		Workbook workbook = ExcelToHtmlUtil.getExcelWorkBook(fileName);
		/**
		 * 解析excel系统设置模板配置信息
		 */
		Sheet sheet = workbook.getSheetAt(0);// 系统设置模板
		int systtemSetrownum = sheet.getLastRowNum();
		SystemSetting systemSetResult = ExcelToHtmlUtil.analyzeSystemSetting(sheet, systtemSetrownum);
		Map<String,Integer> sheetPage =  reportsUtil.getWorkBookSet(systemSetResult, request);
		Sheet sheet1 = workbook.getSheetAt(sheetPage.get("setSheet") - 1);// 配置sheet
		Sheet sheet2 = workbook.getSheetAt(sheetPage.get("templateSheet") - 1);// 模板sheet
		Sheet sheet3 = workbook.getSheetAt(sheetPage.get("querySheet") - 1);// 查询参数sheet
		List<ExcelForm> forms = getExcelForm(sheet3);

		/**
		 * 模板配置页面条件设置sheet1
		 */
		
		TableSetting analyzeResult = ExcelToHtmlUtil.getExcelAnalyze(sheet1);
		/**
		 * 根据是否分页设置查询数据结果的多少，不分页最多显�?000条数�?
		 */
		Page page = reportsUtil.getPageNum(analyzeResult, request);
		/**
		 * 获取sql集
		 */
		Map<String,List<String>> sqlResult = reportsUtil.getSql(analyzeResult, request);
		/**
		 * 获取详细设置
		 */
		TableParams tableParams = reportsUtil.getTemplateInfo(analyzeResult, page);
		/**
		 * 获取参数与值
		 */
		Map<String, Object> paramValues = reportsUtil.getParamValue(forms, request);
		
		
		
		


		/**
		 * 执行用户自定义java代码
		 */
		doPreJavaCode(analyzeResult.getJavaClass(), context);
		Integer totalMax = getTotalMax(sqlResult, paramValues);
		int size = 1000;
		int startNum = 0;
		int totalMaxpageNum = 0;
		if (totalMax >= size && totalMax / size == 0) {
			totalMaxpageNum = totalMax / size;
		} else {
			totalMaxpageNum = totalMax / size + 1;
		}
		/**
		 * 创建结果输出excel
		 */
		Workbook workbookExcport = null;
		if (workbook instanceof XSSFWorkbook) {
			workbookExcport = new SXSSFWorkbook(1000);
		} else if (workbook instanceof HSSFWorkbook) {
			// workbookExcport = new XSSFWorkbook();
		}
		Sheet sheetExport1 = workbookExcport.createSheet("name");
		/**
		 * 复制模板创建新的sxssf模板
		 */
		workbookExcport = ExcelToHtmlUtil.copyWbSheet(sheet2, sheetExport1, workbook, workbookExcport, tableParams.getCopyRowNum()
				+ tableParams.getHeadsTail());
		Sheet sheetExport = workbookExcport.getSheetAt(0);
	
		/**
		 * 创建视图
		 */
		doPreSQL(sqlResult.get("preSqlList"), paramValues);
		/**
		 * 返回�?��统计数据
		 */
		List<Map<String, Object>> sumResults = getExcelSUMResult(sqlResult, paramValues);
		/**
		 * 数据库分页查询数据，向excel中插入数�?
		 */
		for (int pageExportNum = 0; pageExportNum < totalMaxpageNum; pageExportNum++) {
			List<List<Map<String, Object>>> dataResults = getExcelDATAResult(sheetExport, sqlResult,
					 paramValues, size, pageExportNum);
			if (dataResults.size() == 0) {
				dataResults.add(0, null);
				dataResults.add(1, sumResults);
			} else {
				dataResults.add(sumResults);
			}
			startNum = createExcel(sheetExport, sheet2, totalMax, tableParams.getHeadsTail(), tableParams.getTailRowNum(), size, startNum, tableParams.getCopyRowNum(),
					pageExportNum, workbookExcport, dataResults);
		}
		/**
		 * 清除用户通过代码创建的临时表单和数据
		 */
		doClean(analyzeResult.getJavaClass(),context);
		// 设置响应
		String exportName = "";
		try {
			exportName = URLEncoder.encode(analyzeResult.getTemplateName(), "utf-8") + "["
					+ DateFormat.getDateTimeInstance(2, 2, Locale.CHINA).format(new Date());
			response.setHeader("Content-Disposition", "inline; filename=" + exportName + "].xlsx");
			response.setContentType("application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			/* 写数据到文件�?*/
			ServletOutputStream os = response.getOutputStream();
			workbookExcport.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}

	/**
	 * 获取结果的最大行�?
	 * 
	 * @param querySqlList
	 * @param queryTagList
	 * @param baseDAO
	 * @param paramValues
	 * @return
	 */
	private int getTotalMax(Map<String,List<String>> sqlResult,  Map<String,Object> paramValues) {
		int totalSize = 0;
		List<String> querySqlList = sqlResult.get("querySqlList");
		List<String> queryTagList = sqlResult.get("queryTagList");
		for (int y = 0; y < querySqlList.size(); y++) {
			if (!Strings.isNullOrEmpty(querySqlList.get(y))) {
				String resultTag = queryTagList.get(y);
				List<Map<String, Object>> totalResults = Lists.newArrayList();
				String totalSql;
				// 如果是内容项，则数据库分页查�?
				totalSql = "SELECT COUNT(*) FROM(" + querySqlList.get(y) + ") as total";
				totalResults = excelDAO.queryBySqlExcel(totalSql, paramValues, resultTag);
				for (Map.Entry<String, Object> entry : totalResults.get(0).entrySet()) {
					if (totalSize < Integer.parseInt(entry.getValue().toString())) {// 取最大的totalSize
						totalSize = Integer.parseInt(entry.getValue().toString());
					}
				}
			}
		}
		return totalSize;
	}

	/**
	 * 查询返回�?��详细数据
	 * 
	 * @param sheetExcel
	 * @param querySqlList
	 * @param queryTagList
	 * @param baseDAO
	 * @param paramValues
	 * @param size
	 * @param pageExportNum
	 * @return
	 */
	private List<List<Map<String, Object>>> getExcelDATAResult(Sheet sheetExcel, Map<String,List<String>> sqlResult,  Map<String,Object> paramValues, Integer size, Integer pageExportNum) {
		List<List<Map<String, Object>>> allResults = Lists.newArrayList();
		String dataBaseType="";
		dataBaseType ="mysql";
		List<String> querySqlList = sqlResult.get("querySqlList");
		List<String> queryTagList = sqlResult.get("queryTagList");
		for (int y = 0; y < querySqlList.size(); y++) {

			if (!Strings.isNullOrEmpty(querySqlList.get(y))) {
				String resultTag = queryTagList.get(y);
				String sql = "";
				if (resultTag.matches(".*DATA_.*")) {
					List<Map<String, Object>> results = Lists.newArrayList();
					if(dataBaseType!=null&&dataBaseType.equals("db2")){
						sql =  "select * from ("+querySqlList.get(y)+") a where a.row_number>"+pageExportNum * size+" fetch first "+size+" rows only";
					}else{
						sql = querySqlList.get(y) + " limit "+ pageExportNum * size+","+size;
					}
					results = excelDAO.queryBySqlExcel(sql, paramValues, resultTag);
					allResults.add(results);
				}
			}
		}
		return allResults;
	}

	/**
	 * 查询返回�?��统计数据
	 * 
	 * @param querySqlList
	 * @param queryTagList
	 * @param baseDAO
	 * @param paramValues
	 * @return
	 */
	private List<Map<String, Object>> getExcelSUMResult(Map<String,List<String>> sqlResult,
			 Map<String,Object> paramValues) {
		List<String> querySqlList = sqlResult.get("querySqlList");
		List<String> queryTagList = sqlResult.get("queryTagList");
		List<Map<String, Object>> resultsCollect = Lists.newArrayList();
		for (int y = 0; y < querySqlList.size(); y++) {

			if (!Strings.isNullOrEmpty(querySqlList.get(y))) {

				String resultTag = queryTagList.get(y);
				if (resultTag.matches(".*DATASUM_.*")) {
					List<Map<String, Object>> results = Lists.newArrayList();
					String sql = "";
					sql = querySqlList.get(y);
					results = excelDAO.queryBySqlExcel(sql, paramValues, resultTag);
					if (results.size() <= 1 && results.size() > 0) {
						resultsCollect.addAll(results);
					} else {
						System.out.println("sql类型配置出错！改为DATA类型");
					}
				}

			}
		}
		return resultsCollect;
	}

	/**
	 * 创建表结构并填充数据
	 * 
	 * @param sheetExcel
	 * @param sheet
	 * @param totalMax
	 * @param head
	 * @param tail
	 * @param size
	 * @param startNum
	 * @param copyNum
	 * @param pageExportNum
	 * @param workbook
	 * @param results
	 * @return
	 */
	private Integer createExcel(Sheet sheetExcel, Sheet sheet, Integer totalMax, Integer head, Integer tail,
			Integer size, Integer startNum, Integer copyNum, Integer pageExportNum, Workbook workbook,
			List<List<Map<String, Object>>> results) {
		int resultSize = results.size();
		List<Map<String, Object>> sumResults = results.get(resultSize - 1);
		/**
		 * 创建excel�?
		 */
		if (startNum < head) {

			for (int a = startNum; a < head; a++) {
				Row row = sheetExcel.createRow(a);
				row.setRowStyle(sheet.getRow(a).getRowStyle());
				int cellNum = sheet.getRow(a).getLastCellNum();
				XSSFCellStyle cellStyle = null;
				XSSFCellStyle newCellStyle = null;
				RichTextString richTextString = null;
				for (int j = 0; j < cellNum; j++) {
					Cell cell = row.createCell(j);
					if (sheet.getRow(a).getCell(j) != null) {
						cellStyle = (XSSFCellStyle) sheet.getRow(a).getCell(j).getCellStyle();
						richTextString = sheet.getRow(a).getCell(j).getRichStringCellValue();
						newCellStyle = ExcelToHtmlUtil.copyCellStyle(cellStyle, (SXSSFWorkbook) workbook);
						cell.setCellStyle(newCellStyle);
						cell.setCellValue(richTextString);
						String content = cell.getRichStringCellValue().toString();
						String regex = "\\{(.*?)\\}";
						Pattern p = Pattern.compile(regex);
						Matcher m = p.matcher(content);
						m.find();
						String n="";
						String regexValue="";
						try {
							String regx = "{"+m.group(1)+"}";
							for (Map<String, Object> map : sumResults) {
									if (cell.getRichStringCellValue() != null) {
										if(!Strings.isNullOrEmpty(String.valueOf(map.get(m.group(1))))&&map.get(m.group(1))!=null){
											regexValue = map.get(m.group(1)).toString();
											break;
										}
									}
							}
							n = content.replace(regx, regexValue);
							cell.setCellValue(n);
						} catch (IllegalStateException e) {
							cell.setCellValue(richTextString);
							// TODO: handle exception
						}
					} else {
						newCellStyle = ExcelToHtmlUtil.copyCellStyle(cellStyle, (SXSSFWorkbook) workbook);
						cell.setCellStyle(newCellStyle);
					}
				}
				startNum++;
			}
			ExcelToHtmlUtil.mergeSheetInDetail(sheet, sheetExcel, 0, head, startNum, head);
		}
		/**
		 * 创建excel主数据结�?
		 */
		if (startNum >= head && (startNum + size * copyNum) < (totalMax * copyNum + head)) {

			for (int a = 0; a < size; a++) {
				for (int i = 0; i < (results.size() - 1); i++) {
					List<Map<String, Object>> result = results.get(i);
					for (int k = 0; k < copyNum; k++) {
						Row row = sheetExcel.createRow(startNum);
						row.setRowStyle(sheet.getRow(head + k).getRowStyle());
						int cellNum = sheet.getRow(head + k).getLastCellNum();
//						XSSFCellStyle cellStyle = null;
						RichTextString richTextString = null;
						for (int j = 0; j < cellNum; j++) {
							Cell cell = row.createCell(j);
							if (sheet.getRow(head + k).getCell(j) != null) {
//								cellStyle = (XSSFCellStyle) sheet.getRow(head + k).getCell(j).getCellStyle();
								richTextString = sheet.getRow(head + k).getCell(j).getRichStringCellValue();
//								cell.setCellStyle(cellStyle);
								cell.setCellValue(richTextString);
								if (cell.getRichStringCellValue() != null) {
									try {
										String content = cell.getRichStringCellValue().toString();
										String regex = "\\{(.*?)\\}";
										Pattern p = Pattern.compile(regex);
										Matcher m = p.matcher(content);
										m.find();
										String n="";
										String regexValue="";
										String regx = "{"+m.group(1)+"}";
										if (result != null && result.size() > a) {
											Map<String, Object> map = result.get(a);
											if (!Strings.isNullOrEmpty(String.valueOf(map.get(m.group(1))))&&map.get(m.group(1))!=null) {
												regexValue = (!Strings.isNullOrEmpty(String.valueOf(map.get(m.group(1))))) ? map.get(m.group(1)).toString(): "";
											} else {
												for (Map<String, Object> map2 : sumResults) {
													if (!Strings.isNullOrEmpty(String.valueOf(map2.get(m.group(1))))&&map2.get(m.group(1))!=null) {
														regexValue = (!Strings.isNullOrEmpty(String.valueOf(map2.get(m.group(1))))) ? map2.get(m.group(1)).toString(): "";
														break;
													}
												}
											}
										} else {
											for (Map<String, Object> map2 : sumResults) {
												if (!Strings.isNullOrEmpty(String.valueOf(map2.get(m.group(1))))&&map2.get(m.group(1))!=null) {
													regexValue = (!Strings.isNullOrEmpty(String.valueOf(map2.get(m.group(1))))) ? map2.get(m.group(1)).toString(): "";
													break;
												}
											}
										}
										n = content.replace(regx, regexValue);
										cell.setCellValue(n);
									} catch (IllegalStateException e) {
										cell.setCellValue(richTextString);
										// TODO: handle exception
									}
								} else {
									cell.setCellValue("");
								}
							} else {
//								cell.setCellStyle(cellStyle);
							}
						}
						startNum++;
					}
				}
				ExcelToHtmlUtil.mergeSheetInDetail(sheet, sheetExcel, head, copyNum, startNum, copyNum + head);
			}
			// 档数据的条数小于size�?
		} else if (startNum >= head && ((startNum + size * copyNum) >= (totalMax * copyNum + head))
				&& (startNum < (totalMax * copyNum + head + tail))) {
			Integer start = startNum;
			for (int a = 0; a < (totalMax * copyNum + head - start) / copyNum; a++) {
				for (int i = 0; i < results.size() - 1; i++) {
					List<Map<String, Object>> result = results.get(i);
					for (int k = 0; k < copyNum; k++) {
						Row row = sheetExcel.createRow(startNum);
						row.setRowStyle(sheet.getRow(head + k).getRowStyle());
						int cellNum = sheet.getRow(head + k).getLastCellNum();
//						XSSFCellStyle cellStyle = null;
						RichTextString richTextString = null;
						for (int j = 0; j < cellNum; j++) {
							Cell cell = row.createCell(j);
							if (sheet.getRow(head + k).getCell(j) != null) {
//								cellStyle = (XSSFCellStyle) sheet.getRow(head + k).getCell(j).getCellStyle();
								richTextString = sheet.getRow(head + k).getCell(j).getRichStringCellValue();
//								cell.setCellStyle(cellStyle);
								cell.setCellValue(richTextString);
								if (!Strings.isNullOrEmpty(cell.getRichStringCellValue().toString())) {
									try {
										String content = cell.getRichStringCellValue().toString();
										String regex = "\\{(.*?)\\}";
										Pattern p = Pattern.compile(regex);
										Matcher m = p.matcher(content);
										m.find();
										String n="";
										String regexValue="";
										String regx = "{"+m.group(1)+"}";
										if (result != null && result.size() > a) {
											Map<String, Object> map = result.get(a);
											if (!Strings.isNullOrEmpty(String.valueOf(map.get(m.group(1))))&&map.get(m.group(1))!=null) {
												
												regexValue = (!Strings.isNullOrEmpty(String.valueOf(map.get(m.group(1))))) ? map.get(m.group(1)).toString(): "";
											} else {
												for (Map<String, Object> map2 : sumResults) {
													if (!Strings.isNullOrEmpty(String.valueOf(map2.get(m.group(1))))&&map2.get(m.group(1))!=null) {
														regexValue = (!Strings.isNullOrEmpty(String.valueOf(map2.get(m.group(1))))) ? map2.get(m.group(1)).toString(): "";
														break;
													}
												}
											}
										} else {
											for (Map<String, Object> map2 : sumResults) {
												if (!Strings.isNullOrEmpty(String.valueOf(map2.get(m.group(1))))&&map2.get(m.group(1))!=null) {
													regexValue = (!Strings.isNullOrEmpty(String.valueOf(map2.get(m.group(1))))) ? map2.get(m.group(1)).toString(): "";
													break;
												}
											}
										}
										n = content.replace(regx, regexValue);
										cell.setCellValue(n);
									} catch (IllegalStateException e) {
										cell.setCellValue(richTextString);
										// TODO: handle exception
									}
								} else {
									cell.setCellValue("");
								}
							} else {
//								cell.setCellStyle(cellStyle);
							}
						}
						startNum++;
					}
				}
				ExcelToHtmlUtil.mergeSheetInDetail(sheet, sheetExcel, head, copyNum, startNum, copyNum + head);

			}
		}
		/**
		 * 创建excel表尾结构
		 */
		if (startNum >= (totalMax * copyNum + head)) {
			int k = 0;

			for (int q = 0; q < tail; q++) {
				Row row = sheetExcel.createRow(startNum);
				int cellNum = sheet.getRow(head + copyNum + k).getLastCellNum();
				XSSFCellStyle cellStyle = null;
				XSSFCellStyle newCellStyle = null;
				RichTextString richTextString = null;
				for (int j = 0; j < cellNum; j++) {
					Cell cell = row.createCell(j);
					if (sheet.getRow(head + copyNum + k) != null && sheet.getRow(head + copyNum + k).getCell(j) != null) {
						cellStyle = (XSSFCellStyle) sheet.getRow(head + copyNum + k).getCell(j).getCellStyle();
						richTextString = sheet.getRow(head + copyNum + k).getCell(j).getRichStringCellValue();
						newCellStyle = ExcelToHtmlUtil.copyCellStyle(cellStyle, (SXSSFWorkbook) workbook);
						cell.setCellStyle(newCellStyle);
						cell.setCellValue(richTextString);
						try {
							if (cell.getRichStringCellValue() != null) {
								String content = cell.getRichStringCellValue().toString();
								String regex = "\\{(.*?)\\}";
								Pattern p = Pattern.compile(regex);
								Matcher m = p.matcher(content);
								m.find();
								String regx = "{"+m.group(1)+"}";
								String regexValue="";
								String n="";
								for (Map<String, Object> map : sumResults) {
									if (cell.getRichStringCellValue() != null) {
										if (!Strings.isNullOrEmpty(String.valueOf(map.get(m.group(1))))&&map.get(m.group(1))!=null) {
											regexValue = map.get(m.group(1)).toString();
											break;
										}
									}
								}
								n = content.replace(regx, regexValue);
								cell.setCellValue(n);
							} else {
								cell.setCellValue("");
							}
						} catch (IllegalStateException e) {
							cell.setCellValue(richTextString);
							// TODO: handle exception
						}
					} else {
						cell.setCellStyle(newCellStyle);
					}
				}
				startNum++;
				k++;
			}
			ExcelToHtmlUtil.mergeSheetInDetail(sheet, sheetExcel, head + copyNum, head + copyNum + tail, startNum, head
					+ copyNum + tail);
		}
		return startNum;
	}

	/**
	 * 创建视图
	 * 
	 * @param preSQL
	 * @param ebpDAO
	 * @param paramValues
	 */
	private void doPreSQL(List<String> preSqlList, Map<String,Object> paramValues) {
		try {
			for (String sql : preSqlList) {
				if (Strings.isNullOrEmpty(sql)) {
					excelDAO.doCreateView(sql, paramValues);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 执行自定义java代码
	 * @param javaClass
	 * @param context
	 */
	private void doPreJavaCode(String javaClass, Map<String,Object> context) {
		if(Strings.isNullOrEmpty(javaClass)){
			return;
		}
		try {
			Class<?> demo = Class.forName(javaClass);
			// Constructor<?> demoStructure = demo.getConstructor();
			Object o = demo.newInstance();
			Method method = o.getClass().getDeclaredMethod("execute", new Class[] { Map.class});
			method.invoke(o, new Object[] { context });
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 用户自定义表或�?数据清除
	private void doClean(String javaClass, Map<String,Object> context) {
		if(Strings.isNullOrEmpty(javaClass)){
			return;
		}
		try {
			Class<?> demo = Class.forName(javaClass);
			// Constructor<?> demoStructure = demo.getConstructor();
			Object o = demo.newInstance();
			Method method = o.getClass().getDeclaredMethod("clean",new Class[] { Map.class});
			method.invoke(o,new Object[] { context });
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
