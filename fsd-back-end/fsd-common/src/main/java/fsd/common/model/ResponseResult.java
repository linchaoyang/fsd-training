package fsd.common.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult implements Serializable {
	/**
	 * Serialize.
	 */
	private static final long serialVersionUID = -8739271187671909578L;

	public static final Integer CODE_SUCCESS = 0;

	public static final Integer CODE_ERROR = -1;

	private static final String STATUS_SUCCESS = "ok";

	private static final String STATUS_ERROR = "error";

	/** code. */
	private Integer code;

	/** status. */
	private String status;

	/** message. */
	private String msg;

	/** data. */
	private Object data;

	/**
	 * Success.
	 */
	public static ResponseResult ok() {
		return new ResponseResult(CODE_SUCCESS, STATUS_SUCCESS, "success", null);
	}

	/**
	 * Success.
	 * 
	 * @param msg success message
	 */
	public static ResponseResult ok(String msg) {
		return new ResponseResult(CODE_SUCCESS, STATUS_SUCCESS, msg, null);
	}

	/**
	 * Success.
	 * 
	 * @param data success data
	 */
	public static ResponseResult ok(Object data) {
		return new ResponseResult(CODE_SUCCESS, STATUS_SUCCESS, "success", data);
	}

	/**
	 * Success.
	 * 
	 * @param msg  success message
	 * @param data success data
	 */
	public static ResponseResult ok(String msg, Object data) {
		return new ResponseResult(CODE_SUCCESS, STATUS_SUCCESS, msg, data);
	}

	/**
	 * Failure.
	 */
	public static ResponseResult error() {
		return new ResponseResult(CODE_ERROR, STATUS_ERROR, "error", null);
	}

	/**
	 * Failure.
	 * 
	 * @param msg error reason
	 */
	public static ResponseResult error(String msg) {
		return new ResponseResult(CODE_ERROR, STATUS_ERROR, msg, null);
	}
}