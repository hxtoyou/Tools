package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

public class Utils {
	/**
	 * 根据文件的路径创建Workbook对象
	 * 
	 * @param filePath
	 */
	public static Workbook getExcelWorkBook(String filePath) {
		InputStream ins = null;
		Workbook book = null;
		try {
			ins = new FileInputStream(new File(filePath));
			book = WorkbookFactory.create(ins);
			ins.close();
			return book;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 复制表格
	 * @param srcSheet
	 * @param destSheet
	 * @param srcwb
	 * @param destwb
	 * @return
	 */
	public static Workbook copyWbSheet(Sheet srcSheet,Sheet destSheet,Workbook srcwb,Workbook destwb,Integer contentRow){
		int maxCellNum = 0;
		try {
			maxCellNum = copySheet(srcSheet, destSheet, srcwb, destwb,contentRow);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 设置列宽
	    setSheetWidth(srcSheet, destSheet, maxCellNum);
	    // 合并单元�?
	    mergeSheetAllRegion(srcSheet, destSheet,contentRow);
	    return destwb;
	}
	public static void mergeSheetAllRegion(Sheet srcSheet, Sheet destSheet,Integer contentRow) {
	    int num = srcSheet.getNumMergedRegions();
	    CellRangeAddress cellR = null;
	    for (int i = 0; i < num; i++) {
	      cellR = srcSheet.getMergedRegion(i);
	      int lastRow=cellR.getLastRow();
	      if(lastRow<contentRow){
	    	  destSheet.addMergedRegion(cellR);
	      }
	    }
	  }

	public static void setSheetWidth(Sheet srcSheet, Sheet destSheet,
	      int maxCellNum) {
	    for (int i = 0; i <= maxCellNum; i++) {
	      destSheet.setColumnWidth(i, srcSheet.getColumnWidth(i));
	    }
	  }
	public static int copySheet(Sheet srcSheet, Sheet destSheet,
		      Workbook srcwb, Workbook destwb,Integer contentRow) throws Exception {
//		    int rowCount = srcSheet.getLastRowNum();// 总行�?
		    int maxCellNum = 0;
		    Row srcRow = null, destRow = null;
		    for (int i = 0; i < contentRow; i++) {
		      srcRow = srcSheet.getRow(i);
		      destRow = destSheet.getRow(i);
		      if (srcRow == null) {
		        continue;
		      }
		      // �?��列数
		      maxCellNum = maxCellNum < srcRow.getLastCellNum() ? srcRow
		          .getLastCellNum() : maxCellNum;
		      if (destRow == null) {
		        destRow = destSheet.createRow(i);
		      }
		      // 设置行高
		      destRow.setHeight(srcRow.getHeight());
		      copySheetRow(srcRow, destRow, srcwb, destwb);
		      srcRow = null;
		      destRow = null;
		    }
		    srcRow = null;
		    destRow = null;
		    return maxCellNum;
		  }
	private static void copySheetRow(Row srcRow, Row destRow,
		      Workbook srcwb, Workbook destwb) {
		    int cellCount = srcRow.getLastCellNum();// 每行的�?列数
		    Cell srcCell = null, destCell = null;
		    CellStyle srcCellStyle = null, destCellStyle = null;
		    for (int j = 0; j < cellCount; j++) {// 遍历行单元格
		      srcCell = srcRow.getCell(j);
		      destCell = destRow.getCell(j);
		      if (destCell == null) {
		        destCell = destRow.createCell(j);
		      }
		      if (srcCell != null) {
		        srcCellStyle = srcCell.getCellStyle();// 原sheet页样�?
		        destCellStyle = null;
		        destCellStyle = destCell.getCellStyle();
		        // 复制样式
		        destCellStyle.cloneStyleFrom(srcCellStyle);
		        // 处理单元格内�?
		        switch (srcCell.getCellType()) {
		        case Cell.CELL_TYPE_STRING:
		          destCell.setCellValue(srcCell.getRichStringCellValue());
		          destCell.setCellStyle(srcCellStyle);
		          break;
		        // 这里判断是否是日�?
		        case Cell.CELL_TYPE_NUMERIC:
		          // 判断是否是日期格�?
		          // 测试发现如果这里不新建样�?日期显示的是数字
		          if (DateUtil.isCellDateFormatted(srcCell)) {
		            // 新建样式
		            destCellStyle = destwb.createCellStyle();
		            // 复制样式
		            destCellStyle.cloneStyleFrom(srcCellStyle);
		            destCell.setCellStyle(srcCellStyle);
		            destCell.setCellValue(srcCell.getDateCellValue());
		          } else {
		            destCell.setCellValue(srcCell.getNumericCellValue());
		          }
		          break;
		        case Cell.CELL_TYPE_FORMULA:
		          destCell.setCellFormula(srcCell.getCellFormula());
		          destCell.setCellStyle(srcCellStyle);
		          break;
		        case Cell.CELL_TYPE_BOOLEAN:
		          destCell.setCellValue(srcCell.getBooleanCellValue());
		          destCell.setCellStyle(srcCellStyle);
		          break;
		        case Cell.CELL_TYPE_BLANK:
		          destCell.setCellType(Cell.CELL_TYPE_BLANK);
		          destCell.setCellStyle(srcCellStyle);
		          break;
		        case Cell.CELL_TYPE_ERROR:
		          break;
		        default:
		          break;
		        }
		      }
		    }
		    srcCellStyle = null;
		    destCellStyle = null;
		    srcCell = null;
		    destCell = null;
		  }
}
