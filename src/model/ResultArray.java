package model;

public class ResultArray {

	private String error;
	private String result;
	private String message;
    private String agentId;

	public ResultArray() {
	}

	public ResultArray(String error, String result, String message,String agentId) {
		super();
		this.error = error;
		this.result = result;
		this.message = message;
		this.agentId=agentId;
	}
	
	
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.error = agentId;
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
