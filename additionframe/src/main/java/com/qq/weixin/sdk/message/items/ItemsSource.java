package com.qq.weixin.sdk.message.items;

import java.util.ArrayList;
import java.util.List;

import com.qq.weixin.sdk.message.ItemArticle;

public class ItemsSource {

	/**
	 * @author antoniohu
	 */
	
	//关注回复
	public static List getGreetingItems(String userName) {
		ItemArticle GreetingItem0 = new ItemArticle();
		//GreetingItem0.setDescription("松下懒人，排产咯！");
		GreetingItem0.setPicUrl("http://t3.baidu.com/it/u=2,1204678601&fm=19&gp=0.jpg");
		GreetingItem0.setTitle("Hey！ " + userName + " ！排产咯！         抓紧立马直接回复以下数字");
		GreetingItem0.setUrl("http://1.antoniohu.duapp.com");
		
		ItemArticle GreetingItem1 = new ItemArticle();
		//GreetingItem1.setDescription("1");
		GreetingItem1.setPicUrl("");
		GreetingItem1.setTitle("①	设定昵称");
		//GreetingItem1.setUrl("http://1.antoniohu.duapp.com");
		
		ItemArticle GreetingItem2 = new ItemArticle();
		//GreetingItem2.setDescription("123");
		GreetingItem2.setPicUrl("");
		GreetingItem2.setTitle("②	计划安排");
		//GreetingItem2.setUrl("http://1.antoniohu.duapp.com");
		
		ItemArticle GreetingItem3 = new ItemArticle();
		//GreetingItem3.setDescription("");
		GreetingItem3.setPicUrl("");
		GreetingItem3.setTitle("③	实时汇报");
		//GreetingItem3.setUrl("http://1.antoniohu.duapp.com");
		
		ItemArticle GreetingItem4 = new ItemArticle();
		//GreetingItem4.setDescription("");
		GreetingItem4.setPicUrl("");
		GreetingItem4.setTitle("④	查询进度");
		//GreetingItem4.setUrl("http://1.antoniohu.duapp.com");
		
		ItemArticle GreetingItem5 = new ItemArticle();
		//GreetingItem5.setDescription("");
		GreetingItem5.setPicUrl("");
		GreetingItem5.setTitle("⑤	帮助");
		//GreetingItem5.setUrl("http://1.antoniohu.duapp.com");
		
		List newsList = new ArrayList();
		newsList.add(GreetingItem0);
		newsList.add(GreetingItem1);
		newsList.add(GreetingItem2);
		newsList.add(GreetingItem3);
		newsList.add(GreetingItem4);
		newsList.add(GreetingItem5);
		return newsList;
	}

	public static List getGreetingItems() {
		ItemArticle GreetingItem0 = new ItemArticle();
		//GreetingItem0.setDescription("松下懒人，排产咯！");
		GreetingItem0.setPicUrl("http://t3.baidu.com/it/u=2,1204678601&fm=19&gp=0.jpg");
		GreetingItem0.setTitle("东爹排产咯！直接回复以下数字");
		GreetingItem0.setUrl("http://1.antoniohu.duapp.com");
		
		ItemArticle GreetingItem1 = new ItemArticle();
		//GreetingItem1.setDescription("1");
		GreetingItem1.setPicUrl("");
		GreetingItem1.setTitle("①	设定昵称");
		//GreetingItem1.setUrl("http://1.antoniohu.duapp.com");
		
		ItemArticle GreetingItem2 = new ItemArticle();
		//GreetingItem2.setDescription("123");
		GreetingItem2.setPicUrl("");
		GreetingItem2.setTitle("②	计划安排");
		//GreetingItem2.setUrl("http://1.antoniohu.duapp.com");
		
		ItemArticle GreetingItem3 = new ItemArticle();
		//GreetingItem3.setDescription("");
		GreetingItem3.setPicUrl("");
		GreetingItem3.setTitle("③	实时汇报");
		//GreetingItem3.setUrl("http://1.antoniohu.duapp.com");
		
		ItemArticle GreetingItem4 = new ItemArticle();
		//GreetingItem4.setDescription("");
		GreetingItem4.setPicUrl("");
		GreetingItem4.setTitle("④	查询进度");
		//GreetingItem4.setUrl("http://1.antoniohu.duapp.com");
		
		ItemArticle GreetingItem5 = new ItemArticle();
		//GreetingItem5.setDescription("");
		GreetingItem5.setPicUrl("");
		GreetingItem5.setTitle("⑤	帮助");
		//GreetingItem5.setUrl("http://1.antoniohu.duapp.com");
		
		List newsList = new ArrayList();
		newsList.add(GreetingItem0);
		newsList.add(GreetingItem1);
		newsList.add(GreetingItem2);
		newsList.add(GreetingItem3);
		newsList.add(GreetingItem4);
		newsList.add(GreetingItem5);
		return newsList;
	}
	
	//测试回复
	public static List getDefaultResultItems(String name) {
		ItemArticle defaultResultItem = new ItemArticle();
		defaultResultItem.setDescription("");
		defaultResultItem.setPicUrl("");
		defaultResultItem.setTitle(name);
		defaultResultItem.setUrl("http://1.antoniohu.duapp.com");
		List newsList = new ArrayList();
		newsList.add(defaultResultItem);
		return newsList;
	}
}
