package ebills.tools.excel.entity;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

/**
 * 统一定义id的属性名称、数据类型、列名映射及生成策略的父类.
 * <p>
 * 子类可重载getId()函数重定义id的列名映射和生成策略.生成策略包括：
 * <li>底层数据库生成标识符
 * 
 * <pre>
 * {@literal @}GeneratedValue(strategy = GenerationType.IDENTITY)
 * </pre>
 * 
 * <li>采用数据库提供的sequence机制
 * 
 * <pre>
 * {@literal @}GeneratedValue(strategy = GenerationType.SEQUENCE)
 * </pre>
 * 
 * <li>UUID主键策略
 * 
 * <pre>
 * {@literal @}GeneratedValue(generator = "uuid")
 * {@literal @}GenericGenerator(name = "uuid", strategy = "uuid")
 * </pre>
 * 
 * <li>GUID主键策略
 * 
 * <pre>
 * {@literal @}GeneratedValue(generator = "guid")
 * {@literal @}GenericGenerator(name = "guid", strategy = "guid")
 * </pre>
 * 
 * @author He Xiao
 */
@MappedSuperclass
public abstract class IDEntity {
	/**
	 * 主键.
	 */
	protected Long id;

	/**
	 * 根据数据库自动生成选择主键策略，MySQL使用identity，Oracle使用sequence.
	 * 
	 * @return 主键
	 */
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Id
	public Long getId() {
		return id;
	}

	/**
	 * 设置主键.
	 * 
	 * @param id
	 *            主键
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
