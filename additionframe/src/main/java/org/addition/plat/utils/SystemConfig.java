package org.addition.plat.utils;

public class SystemConfig {
public final static String EXTENSION_SEPARATOR = ",";
public final static String CONFIG_FILE_NAME = "config.property";
public final static String TEMPLATE_CONFIG_FILE_NAME = "generator.property";

private Integer uploadLimit;// 文件上传最大值,0表示无限制,单位KB
private Boolean isLoginFailureLock; // 是否开启登录失败锁定账号功能
private Integer loginFailureLockCount;// 同一账号允许连续登录失败的最大次数，超出次数后将锁定其账号
private Integer loginFailureLockTime;// 账号锁定时间(单位：分钟,0表示永久锁定)
public Integer getUploadLimit()
{
	return uploadLimit;
}
public void setUploadLimit(Integer uploadLimit)
{
	this.uploadLimit = uploadLimit;
}
public Boolean getIsLoginFailureLock()
{
	return isLoginFailureLock;
}
public void setIsLoginFailureLock(Boolean isLoginFailureLock)
{
	this.isLoginFailureLock = isLoginFailureLock;
}
public Integer getLoginFailureLockCount()
{
	return loginFailureLockCount;
}
public void setLoginFailureLockCount(Integer loginFailureLockCount)
{
	this.loginFailureLockCount = loginFailureLockCount;
}
public Integer getLoginFailureLockTime()
{
	return loginFailureLockTime;
}
public void setLoginFailureLockTime(Integer loginFailureLockTime)
{
	this.loginFailureLockTime = loginFailureLockTime;
}


}
