/*
 * Copyright (C) CCRISE.
 */
package ebills.tools.util;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件工具�?
 * <p>
 * 按照applicationContext.xml中定义的配置文件优先级加载应用配�?
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public abstract class PropertiesUtils {
	/**
	 * 日志.
	 */
	protected final static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

	// 常用参数
	/**
	 * jdbc地址.
	 */
	public static final String JDBC_URL_PROPERTY = "jdbc.url";
	/**
	 * jdbc用户�?
	 */
	public static final String JDBC_USERNAME_PROPERTY = "jdbc.username";
	/**
	 * jdbc密码.
	 */
	public static final String JDBC_PASSWORD_PROPERTY = "jdbc.password";
	/**
	 * jdbc驱动.
	 */
	public static final String JDBC_DRIVER_PROPERTY = "jdbc.driver";

	/**
	 * 是否�?��权限控制.
	 */
	public static final String ENABLE_PROPERTY = "security.enable";
	/**
	 * 是否�?��高级权限控制.可以控制到操作级�?
	 */
	public static final String ADVANCED_PROPERTY = "security.advanced";
	/**
	 * 忽略路径.
	 */
	public static final String IGNORE_PATHS_PROPERTY = "security.ignore_paths";
	/**
	 * HttpSession.
	 */
	public static final String SESSION_KEY_PROPERTY = "security.session_key";
	/**
	 * 登录�?
	 */
	public static final String LOGIN_PATH_PROPERTY = "security.login_path";

	// 配置文件名，优先级从小到�?
	/**
	 * 默认配置文件�?
	 */
	private static final String DEFAULT_PROPERTIES_FILE = "application.properties";
	/**
	 * 集群配置文件�?
	 */
	private static final String CLUSTER_PROPERTIES_FILE = "application.cluster.properties";
	/**
	 * 生产环境配置文件�?
	 */
	private static final String SERVER_PROPERTIES_FILE = "application.server.properties";
	/**
	 * 本地配置文件�?
	 */
	private static final String LOCAL_PROPERTIES_FILE = "application.local.properties";

	// 配置文件
	/**
	 * 默认配置.
	 */
	private static PropertiesConfiguration defaultConfiguration;
	/**
	 * 集群配置.
	 */
	private static PropertiesConfiguration clusterConfiguration;
	/**
	 * 生产环境配置.
	 */
	private static PropertiesConfiguration serverConfiguration;
	/**
	 * 本地配置.
	 */
	private static PropertiesConfiguration localConfiguration;

	static {
		try {
			loadConfigFile();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取布尔�?
	 * 
	 * @param key
	 *            键名
	 * @return 布尔�?
	 */
	public static Boolean getBoolean(final String key) {
		if (localConfiguration != null && localConfiguration.containsKey(key)) {
			return localConfiguration.getBoolean(key);
		}
		if (serverConfiguration != null && serverConfiguration.containsKey(key)) {
			return serverConfiguration.getBoolean(key);
		}
		if (clusterConfiguration != null && clusterConfiguration.containsKey(key)) {
			return clusterConfiguration.getBoolean(key);
		}
		if (defaultConfiguration != null && defaultConfiguration.containsKey(key)) {
			return defaultConfiguration.getBoolean(key);
		}

		return Boolean.FALSE;
	}

	/**
	 * 获取整型.
	 * 
	 * @param key
	 *            键名
	 * @return 整型
	 */
	public static int getInt(final String key) {
		if (localConfiguration != null && localConfiguration.containsKey(key)) {
			return localConfiguration.getInt(key);
		}
		if (serverConfiguration != null && serverConfiguration.containsKey(key)) {
			return serverConfiguration.getInt(key);
		}
		if (clusterConfiguration != null && clusterConfiguration.containsKey(key)) {
			return clusterConfiguration.getInt(key);
		}
		if (defaultConfiguration != null && defaultConfiguration.containsKey(key)) {
			return defaultConfiguration.getInt(key);
		}

		throw new NoSuchElementException("没找" + key + "对应的整数�?");
	}

	/**
	 * 获取列表.
	 * 
	 * @param key
	 *            键名
	 * @return 字符串列�?
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getList(final String key) {
		if (localConfiguration != null && localConfiguration.containsKey(key)) {
			return localConfiguration.getList(key);
		}
		if (serverConfiguration != null && serverConfiguration.containsKey(key)) {
			return serverConfiguration.getList(key);
		}
		if (clusterConfiguration != null && clusterConfiguration.containsKey(key)) {
			return clusterConfiguration.getList(key);
		}
		if (defaultConfiguration != null && defaultConfiguration.containsKey(key)) {
			return defaultConfiguration.getList(key);
		}

		return null;
	}

	/**
	 * 获取字符串�?.
	 * 
	 * @param key
	 *            键名
	 * @return 字符串�?
	 */
	public static String getString(final String key) {
		if (localConfiguration != null && localConfiguration.containsKey(key)) {
			return localConfiguration.getString(key);
		}
		if (serverConfiguration != null && serverConfiguration.containsKey(key)) {
			return serverConfiguration.getString(key);
		}
		if (clusterConfiguration != null && clusterConfiguration.containsKey(key)) {
			return clusterConfiguration.getString(key);
		}
		if (defaultConfiguration != null && defaultConfiguration.containsKey(key)) {
			return defaultConfiguration.getString(key);
		}

		return null;
	}

	/**
	 * 加载配置文件.
	 * @throws ConfigurationException 
	 */
	private static void loadConfigFile() throws ConfigurationException {
		// 读取默认配置文件
		try {
			defaultConfiguration = new PropertiesConfiguration(DEFAULT_PROPERTIES_FILE);
		} catch (Exception e) {
			logger.debug("无默认配置文�?");
		}

		// 加载集群配置文件
		try {
			clusterConfiguration = new PropertiesConfiguration(CLUSTER_PROPERTIES_FILE);
		} catch (Exception e) {
			logger.debug("无集群配置文�?");
		}

		// 加载服务器配置文�?
		try {
			serverConfiguration = new PropertiesConfiguration(SERVER_PROPERTIES_FILE);
		} catch (Exception e) {
			logger.debug("无服务器配置文件.");
		}

		// 加载本地配置文件
		try {
			localConfiguration = new PropertiesConfiguration(LOCAL_PROPERTIES_FILE);
		} catch (Exception e) {
			logger.debug("无本地配置文�?");
		}
	}
}
