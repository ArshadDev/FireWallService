package com.luxoft.assignment.fireWall.model;

/**
 * @author Arshad
 */
public enum HTTPStatus {
	
	OK(200), BAD_REQUEST(400), UNAUTHORIZED(401) ;

	private int code;

	HTTPStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}
