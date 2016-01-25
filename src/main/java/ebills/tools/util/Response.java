/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package ebills.tools.util;

import java.util.Map;

/**
 * 返回数据模型.
 * <p>
 * 用于包装返回的所有数据，包括：响应数据，验证数据�?
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public class Response {
	/**
	 * 是否成功.
	 */
	private boolean success;
	/**
	 * 返回数据.
	 */
	private Object data;
	/**
	 * 错误数据.
	 */
	private Map<String, String> errors;

	/**
	 * 默认构�?方法.
	 */
	public Response() {
	}

	/**
	 * 构�?方法.
	 * 
	 * @param success
	 *            是否成功
	 */
	public Response(final boolean success) {
		this.success = success;
	}

	/**
	 * 构�?方法.
	 * 
	 * @param success
	 *            是否成功
	 * @param data
	 *            返回数据
	 */
	public Response(final boolean success, final Object data) {
		this.success = success;
		this.data = data;
	}

	/**
	 * 构�?方法.如果errors为空，则success为true.
	 * 
	 * @param errors
	 *            错误数据
	 */
	public Response(final Map<String, String> errors) {
		if (errors.size() == 0) {
			success = true;
		}
		this.errors = errors;
	}

	/**
	 * 构�?方法.如果data为null，则success为false.
	 * 
	 * @param data
	 *            返回数据
	 */
	public Response(final Object data) {
		if (data != null) {
			success = true;
		}
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setData(final Object data) {
		this.data = data;
	}

	public void setErrors(final Map<String, String> errors) {
		this.errors = errors;
	}

	public void setSuccess(final boolean success) {
		this.success = success;
	}
}
