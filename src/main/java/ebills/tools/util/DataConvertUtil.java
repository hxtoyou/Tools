package ebills.tools.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;


/**
 * 
 * 数据格式转换
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间�?015�?�?8�?下午4:08:02
 */
public class DataConvertUtil {
	/**
	 * String类型的时间数据转为制定格式的String
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String dateStringFormat(String dateString, String format)  {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date;
		String datea="";
		try {
			date = sdf.parse(dateString);
			 datea = sdf.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return dateString;
		}
		return datea;
	}
	public static java.sql.Date String2date(String dateString, String format) {
		java.sql.Date utilDate = null;
		try {
			utilDate = new java.sql.Date((new SimpleDateFormat(format)).parse(dateString).getTime());
		} catch (Exception e) {

		}
		return utilDate;
	}
	/**
	 * 数字转换为制定格�?有几位小数位)的数�?
	 * 
	 * @param value
	 * @param format
	 * @return
	 */
	public static String NumberFormat(String value, String format) {
		Double num = Double.parseDouble(value);
		DecimalFormat df = new java.text.DecimalFormat(format);
		return df.format(num);
	}


	/**
	 * 匹配数据转换内容
	 * 
	 * @param value
	 * @return
	 */
	public static String ConvertContent(String value) {
		String contentC = value;
		String regexC = "\\((.*?)\\)";
		Pattern pC = Pattern.compile(regexC);
		Matcher mC = pC.matcher(contentC);
		mC.find();
		return mC.group(1);
	}

	/**
	 * 解析结果转换内容
	 * 
	 * @param value
	 * @return
	 */
	public static Map<String, Map<String, String>> ConvertAll(Map<String, Map<String, String>> map, String value) {
		String content = value;
		String regex = "\\[(.*?)\\]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		m.find();
		List<String> head = Arrays.asList(m.group(1).split(":"));
		String contentC = value;
		String regexC = "\\((.*?)\\)";
		Pattern pC = Pattern.compile(regexC);
		Matcher mC = pC.matcher(contentC);
		mC.find();
		Map<String, String> typeDetail = new HashMap<String, String>();
		typeDetail.put(head.get(0), mC.group(1));
		map.put(head.get(1), typeDetail);
		return map;
	}

	/**
	 * 数据为空时显示默认�?
	 * 
	 * @param value
	 * @return
	 */
	public static String defualtString(String value, String defualt) {
		if (value!=null&&!"".equals(value) && value != "null") {
			return value;
		} else {
			return defualt;
		}
	}

	/**
	 * 把�?转为对应的设定的�?
	 * 
	 * @param value
	 * @return
	 */
	public static Map<String, String> convertMap(String value) {
		List<String> v = Arrays.asList(value.split(","));
		Map<String, String> map = new HashMap<String, String>();
		for (String content : v) {
			List<String> c = Arrays.asList(content.split(":"));
			map.put(c.get(0), c.get(1));
		}
		return map;
	}
	public static String resultSetConvert(Object obj){
		String value="";
		try {
			value = (Strings.isNullOrEmpty(String.valueOf(obj).trim())||String.valueOf(obj).trim()=="null")?"":String.valueOf(obj);
		} catch (ClassCastException e) {
			// TODO: handle exception
			System.out.print("数据转换异常"+obj.getClass());
		}
		return value;
	}
	/**
	 * 去除字符串中的空格�?回车、换行符、制表符
	 * @param str
	 * @return
	 */
	 public static String replaceBlank(String str) {
	        String dest = "";
	        if (str!=null) {
	            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	            Matcher m = p.matcher(str);
	            dest = m.replaceAll("");
	        }
	        return dest;
	   }
}
