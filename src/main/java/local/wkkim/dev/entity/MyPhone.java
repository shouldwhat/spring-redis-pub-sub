package local.wkkim.dev.entity;

import java.io.Serializable;

public class MyPhone implements Serializable
{
	private String number;

	public MyPhone()
	{
		
	}
	
	public MyPhone(String number)
	{
		this.setNumber(number);
	}
	
	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	@Override
	public String toString()
	{
		return "MyPhone [number=" + number + "]";
	}
}
