/**
 * additionframe
 * SplitUtil.java
 * 2015年12月4日
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 * 
 */
package org.addition.plat.utils;

/**
 * TODO Add class comment here<p/>
 * @version 1.0.0
 * @since 1.0.0
 * @author Addition
 * @history<br/>
 * ver    date       author desc
 * 1.0.0  2015年12月4日  LiangJiahao    created<br/>
 * <p/> 
 */
public class SplitUtil
{
	public static final String GPS_MESSAGE_SPLIT_REGEX = "\\+";
	public static final String SEND_GPS_MESSAGE_SPLIT_REGEX = "+";
	public static void main(String[] args)
	{
		
		String s = "42f6be35bc2a5ac0aa0dd82b+深圳市南山区松坪山路1号侧门+源兴科技大厦-东座";
		
		String values[] = s.split(GPS_MESSAGE_SPLIT_REGEX);
		String sum = "";
		for(String v : values)
		{
			System.out.println(v);
			sum = sum + v + SEND_GPS_MESSAGE_SPLIT_REGEX;
		}
		System.out.println(sum);
	}
}
