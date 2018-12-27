package org.indyoracle.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DataManager {
	private static DataManager instance = null;
	
	private long startTime;
	private long uptime;
	private ArrayList<String> messages;
	
	protected DataManager() { }
	
	public static DataManager getInstance() {
		if (instance == null) {
			instance = new DataManager();
			instance.startTime = System.currentTimeMillis(); 
			instance.uptime    = 0;
			instance.messages  = new ArrayList<String>();
		}
		return instance;
	}
	
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getUptime() {
		return uptime;
	}

	public void setUptime(long uptime) {
		this.uptime = uptime;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}
	
	public void addMessage(String txt) {
		this.messages.add(txt);
	}
	
	public String getFormattedUptime() {
		long s = TimeUnit.MILLISECONDS.toSeconds(this.uptime) % 60;
		long m = TimeUnit.MILLISECONDS.toMinutes(this.uptime) % 60;
		long h = TimeUnit.MILLISECONDS.toHours(this.uptime);
	    return String.format("%03d:%02d:%02d", h,m,s);
	}
}
