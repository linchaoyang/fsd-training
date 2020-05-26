package fsd.common.model;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

	/** success code */
	public static final int CODE_SUCCESS = 0;

	/** error code */
	public static final int CODE_ERROR = -1;

	/** Timestamp */
	private long timestamp;

	/** code number with 0 or -1 */
	private int code = CODE_SUCCESS;

	/** error summary */
	@JsonInclude(Include.NON_NULL)
	private String error;

	/** error detail message */
	private String message = HttpStatus.OK.getReasonPhrase();

	/** body data */
	@JsonInclude(Include.NON_NULL)
	private T data;

	public Result(int code, String message, T data) {
		this.timestamp = System.currentTimeMillis();
		this.code = code; // 0 or -1
		this.error = null;
		this.message = message; // error detail message
		this.data = data;
	}

	/**
	 * Success.
	 */
	public static <T> Result<T> ok() {
		return new Result<>(CODE_SUCCESS, "success", null);
	}

	/**
	 * Success.
	 * 
	 * @param msg success message
	 */
	public static <T> Result<T> ok(String msg) {
		return new Result<>(CODE_SUCCESS, msg, null);
	}

	/**
	 * Success.
	 * 
	 * @param data success data
	 */
	public static <T> Result<T> ok(T data) {
		return new Result<>(CODE_SUCCESS, "success", data);
	}

	/**
	 * Success.
	 * 
	 * @param msg  success message
	 * @param data success data
	 */
	public static <T> Result<T> ok(String msg, T data) {
		return new Result<>(CODE_SUCCESS, msg, data);
	}

	/**
	 * Failure.
	 */
	public static <T> Result<T> error() {
		return error("error", "");
	}

	/**
	 * Failure.
	 * 
	 * @param msg error reason
	 */
	public static <T> Result<T> error(String msg) {
		return error("error", msg);
	}

	/**
	 * Failure.
	 * 
	 * @param error error summary
	 * @param msg   error reason
	 */
	public static <T> Result<T> error(String error, String msg) {
		Result<T> result = new Result<>(CODE_ERROR, msg, null);
		result.setError(error);
		return result;
	}

	@Data
	public static class Builder<T> {

		private String error = null;

		private String message = HttpStatus.OK.getReasonPhrase();

		private int code = CODE_SUCCESS;

		private T data;

		public Builder<T> data(T data) {
			this.data = data;
			return this;
		}

		public Builder<T> error(String error, String message) {
			return error(error).message(message);
		}

		public Builder<T> error(int code) {
			this.code = code;
			return this;
		}

		public Builder<T> error(String error) {
			this.code = CODE_ERROR;
			this.error = error;
			return this;
		}

		public Builder<T> message(String message) {
			this.message = message;
			return this;
		}

		public Result<T> build() {
			Result<T> result = new Result<T>(this.code, this.message, this.data);
			result.setError(this.error);

			return result;
		}

	}

}
