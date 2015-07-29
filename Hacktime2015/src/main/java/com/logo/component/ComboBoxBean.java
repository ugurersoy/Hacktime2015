package com.logo.component;

public class ComboBoxBean
{
	private int tag;
	private String value;

	public ComboBoxBean(int tag, String value)
	{
		super();
		this.tag = tag;
		this.value = value;
	}

	public int getTag()
	{
		return tag;
	}

	public void setTag(int tag)
	{
		this.tag = tag;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
