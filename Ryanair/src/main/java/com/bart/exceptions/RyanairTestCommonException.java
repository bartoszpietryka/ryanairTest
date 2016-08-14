package com.bart.exceptions;

/**
 * If we create custom exception classes it's easier to "catch" them and find
 * them in logs . All exceptions from project will inherit from this class. So
 * we can use "catch ( RyanairTestCommonException e)" to get all of our custom
 * exceptions
 * 
 * @author bart
 *
 */

public abstract class RyanairTestCommonException extends Exception {
	public RyanairTestCommonException(String message) {
		super(message);
	}
}
