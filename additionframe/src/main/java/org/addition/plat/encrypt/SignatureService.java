package org.addition.plat.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.addition.plat.service.INuServicesConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("signatureService")
public class SignatureService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SignatureService.class);

    private INuServicesConfig nuServicesConfig;

    private String apiKey;

    @Autowired(required = true)
    public void setNuServicesConfig(INuServicesConfig config)
    {
        nuServicesConfig = config;
        apiKey = nuServicesConfig.getApiKey();
    }

    /**
     * 用 SHA1 算法生成安全签名
     *
     * @param timestamp 时间戳
     * @param nonce     随机字符串
     * @param encrypt   密文
     * @return 安全签名
     */
    public String getSha1(String timestamp, String nonce, String encrypt)
    {
        try {
            final String[] array;
            if (!StringUtils.isEmpty(encrypt)) {
                array = new String[]{
                    apiKey, timestamp, nonce, encrypt
                };
            }
            else {
                array = new String[]{
                    apiKey, timestamp, nonce
                };
            }
            // 按字母序排序
            Arrays.sort(array);
            // 拼接成串
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
            }
            // SHA1 签名生成
            final MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(sb.toString().getBytes());
            final byte[] digest = md.digest();

            final StringBuilder hexStr = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                final String sha1Hex = Integer.toHexString(digest[i] & 0xFF);
                if (sha1Hex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(sha1Hex);
            }
            return hexStr.toString();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("", ex);
        }
        throw new IllegalStateException("no such algorithm exception, SHA-1");
    }
    
    /**
     * 数字签名校对
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encrypt
     * @return
     */
    public boolean checkSignature(String signature, String timestamp,
			String nonce,String encrypt)
	{
		// sha1
		String flag = getSha1(timestamp, nonce, encrypt);
		// check
		if (flag.equalsIgnoreCase(signature))
		{
			LOGGER.info("checkSignature sucessfully!");
			return true;
		} else
		{
			LOGGER.info("checkSignature fail!");
			return false;
		}
	}
}
