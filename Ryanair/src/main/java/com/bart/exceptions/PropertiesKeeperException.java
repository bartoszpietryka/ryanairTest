package com.bart.exceptions;


/**
 * If we create custom exception classes it's easier to "catch" them and find
 * them in logs
 * 
 * @author bart
 *
 */
public class PropertiesKeeperException extends RyanairTestCommonException{
	public PropertiesKeeperException (String message) { super(message); } 

}
