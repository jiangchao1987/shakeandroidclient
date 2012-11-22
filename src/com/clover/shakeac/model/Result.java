package com.clover.shakeac.model;

public class Result<E> {

	private int status;
	private String message;
	private E model;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public E getModel() {
		return model;
	}

	public void setModel(E model) {
		this.model = model;
	}

}
