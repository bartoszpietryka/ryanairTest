package com.bart.utils;

import java.text.SimpleDateFormat;

import org.apache.log4j.DailyRollingFileAppender;

/**
 * Custom log4j Appender. It extends DailyRollingFileAppender . Only difference
 * is that it adds date time to filename.
 * 
 * @version 1.0, 8/14/16
 * 
 * @author Bartosz Pietryka
 * 
 */

public class LogFileAppender extends DailyRollingFileAppender {
	@Override
	public void setFile(String fileName) {
		SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		// Formated current time
		String current_time_str = time_formatter.format(System.currentTimeMillis()); 
		// replace %date in filename. Nothing happens if there is no %date
		fileName = fileName.replaceAll("%date", current_time_str); 
		super.setFile(fileName); // call from DailyRollingFileAppender
	}

}
