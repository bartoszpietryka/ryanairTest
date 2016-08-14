package com.bart.exceptions;

/**
 * If we create custom exception classes it's easier to "catch" them and find
 * them in logs
 * 
 * @author bart
 *
 */
public class NoSuchDriverException extends RyanairTestCommonException{
	public NoSuchDriverException (String message) { super(message); } 

}
