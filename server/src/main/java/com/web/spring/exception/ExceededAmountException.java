package com.web.spring.exception;

public class ExceededAmountException extends RuntimeException{

	public ExceededAmountException(String message) {
		super(message);
	}
}
