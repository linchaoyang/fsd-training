package fsd.common.model;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {

	private long timestamp;

	private String error;

	private String message = HttpStatus.OK.getReasonPhrase();

	private int status = HttpStatus.OK.value();

	private T data;

	public Result(int status, String message, T data) {
		this.timestamp = System.currentTimeMillis();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	@Data
	public static class Builder<T> {

		private String message = HttpStatus.OK.getReasonPhrase();

		private int status = HttpStatus.OK.value();

		private T data;

		public Builder<T> data(T data) {
			this.data = data;
			return this;
		}

		public Builder<T> error(int status, String message) {
			this.status = status;
			this.message = message;
			return this;
		}

		public Builder<T> error(int status) {
			this.status = status;
			return this;
		}

		public Result<T> build() {
			return new Result<T>(this.status, this.message, this.data);
		}

		public Builder<T> message(String message) {
			this.message = message;
			return this;
		}
	}

}
