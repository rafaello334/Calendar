package model;

import java.util.Date;

public class Event
{
	private String date;
	private String time;
	private String eventName;

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getEventName()
	{
		return eventName;
	}

	public void setEventName(String eventName)
	{
		this.eventName = eventName;
	}

	public Event(String time, String eventName, String date)
	{
		this.time = time;
		this.eventName = eventName;
		this.date = date;
	}

	@Override
	public String toString()
	{
		return time + "  -  " + eventName;
	}

}
