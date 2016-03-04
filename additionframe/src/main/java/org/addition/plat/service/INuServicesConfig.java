/*
 * com.genew.nuas
 * INuServicesConfig.java
 * 2015-03-19
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 */
package org.addition.plat.service;

/**
 * 牛信服务配置接口<p/>
 * @version 1.0.0
 * @since 1.0.0
 * @author XXZ
 * @history<br/>
 * <pre>
 * ver    date        author  desc
 * 1.0.0  2015-03-19  XXZ     created
 * </pre>
 * <p/>
 */
public interface INuServicesConfig
{
    String getApiKey();

    String getAesKey();

    String getCacheFilesRootDir();

    String getFtpServerIp();

    int getFtpServerPort();

    boolean isAsynchronousable();

    String getDispatchingServerIp();

    int getDispatchingServerPort();

    String getStorageInfoServerIp();

    int getStorageInfoServerPort();

    String getStorageInfoServerUserName();

    String getStorageInfoServerUserPassword();

    String getImServerIp();

    int getImServerPort();

    int getImServerUploadPort();

    int getImServerDownloadPort();

    boolean isDevelopmentMode();

    String getDeployModel();

    int getImServerPacketSize();
}
