package model;

public class Result {

	private String error;
	private String result;
	private String message;


	public Result() {
	}

	public Result(String error, String result, String message) {
		super();
		this.error = error;
		this.result = result;
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
