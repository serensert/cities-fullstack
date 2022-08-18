package net.seren.cities.model;

public class UploadResponseMessage {

	private String responseMessage;

	public UploadResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	@Override
	public String toString() {
		return "UploadResponseMessage [responseMessage=" + responseMessage + "]";
	}
}
