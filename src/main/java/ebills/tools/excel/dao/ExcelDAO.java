package ebills.tools.excel.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Xiao He E-mail:hxtoyou@163.com
 * @version 创建时间�?015�?�?9�?上午10:48:05
 * 
 */
@Component
public class ExcelDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 创建视图
	 * @param preSQL
	 * @param params
	 * @throws EbillsException
	 */
	public void doCreateView(String preSQL,Map<String,Object> params){
		String newSQL = analyzeSQL(preSQL,params);
		Map<String,Object> stand = Maps.newHashMap();
			NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
			namedParameterJdbcTemplate.update(newSQL, stand);
	}
	/**
	 * sql查询，带结果头tag
	 * @param sql
	 * @param params
	 * @param resultTag
	 * @return
	 */
	public List<Map<String,Object>> queryBySqlExcel(String sql, Map<String,Object> params,String resultTag) {
		String newSQL = analyzeSQL(sql,params);
		Map<String,Object> stand = Maps.newHashMap();
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(newSQL, stand);
		List<Map<String, Object>> result = Lists.newArrayList();
		for(Map<String, Object> map : list){
			Map<String, Object> newmap = Maps.newHashMap();
			  for (Map.Entry<String, Object> entry : map.entrySet()) {
				  String resultType="";
				  if(!Strings.isNullOrEmpty(resultTag)){
						resultType = resultTag+".";
					}
				  	newmap.put(resultType+entry.getKey(), entry.getValue());
				  }
			  result.add(newmap);
		}
		return result;
	}
	/**
	 * sql查询，结果名称不带头
	 * @param sql
	 * @param inputMap
	 * @return
	 * @throws EbillsException
	 */
	public List<Map<String,Object>> queryBySql (String sql,Map<String,Object> inputMap) {
		String newSQL = analyzeSQL(sql,inputMap);
		Map<String,Object> stand = Maps.newHashMap();
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
		List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(newSQL,stand);
		return list;
	}
	/**
	 * 根据参数创建sql
	 * @param sql
	 * @param params
	 * @return
	 */
	private String analyzeSQL(String sql,Map<String,Object> params){
		String newsql=sql;
		if(params==null){
			return newsql;
		}
		  for (Map.Entry<String, Object> entry : params.entrySet()) {
			   if(!Strings.isNullOrEmpty(entry.getKey())){
					newsql = newsql.replaceAll("\\?"+entry.getKey(), "'"+entry.getValue()+"'");
				}else{
					newsql = newsql.replaceAll("\\?"+entry.getKey(), "null");
				}
			  }
		return newsql;
	}
}
