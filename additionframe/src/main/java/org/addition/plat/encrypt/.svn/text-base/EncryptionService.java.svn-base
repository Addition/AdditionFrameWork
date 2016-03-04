package org.addition.plat.encrypt;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.addition.plat.service.INuServicesConfig;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("encryptionService")
public class EncryptionService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionService.class);

    private static final Charset CHARSET = Charset.forName("utf-8");

    private final Base64 base64 = new Base64();

    private String apiKey;

    private byte[] apiKeyBytes;

    private String aceKey;

    private byte[] aesInnerKeyBytes;

    private INuServicesConfig nuServicesConfig;

    public INuServicesConfig getNuServicesConfig()
    {
        return nuServicesConfig;
    }

    @Autowired(required = true)
    public void setNuServicesConfig(INuServicesConfig config)
    {
        nuServicesConfig = config;
        final String aesKey = nuServicesConfig.getAesKey();
        if (43 != aesKey.length())
        {
            final IllegalStateException ex = new IllegalStateException("aceKey length must be 43");
            LOGGER.error("", ex);
            throw ex;
        }
        this.aceKey = aesKey;
        this.apiKey = nuServicesConfig.getApiKey();
        this.apiKeyBytes = apiKey.getBytes(CHARSET);
        aesInnerKeyBytes = Base64.decodeBase64(this.aceKey + "=");

    }

    // 生成4个字节的网络字节序
    private byte[] getNetworkBytesOrder(int sourceNumber)
    {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (sourceNumber & 0xFF);
        orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
        orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
        orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
        return orderBytes;
    }

    // 还原 4 个字节的网络字节序
    private int recoverNetworkBytesOrder(byte[] orderBytes)
    {
        int sourceNumber = 0;
        for (int i = 0; i < 4; i++)
        {
            sourceNumber <<= 8;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }

    // 随机生成 16 位字符串
    private String getRandom()
    {
        final String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++)
        {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 对明文进行加密.
     * @param random 随机字符串
     * @param text 需要加密的明文
     * @return 加密后 base64 编码的字符串
     */
    private String encrypt(String random, String text)
    {
        final ByteGroup byteGroup = new ByteGroup();
        final byte[] randomStrBytes = random.getBytes(CHARSET);
        final byte[] textBytes = text.getBytes(CHARSET);
        final byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);

        // random + networkBytesOrder + text + appid
        byteGroup.addBytes(randomStrBytes);
        byteGroup.addBytes(networkBytesOrder);
        byteGroup.addBytes(textBytes);
        byteGroup.addBytes(apiKeyBytes);

        // ... + pad，使用自定义的填充方式对明文进行补位填充
        final byte[] padBytes = PKCS7Encoder.encode(byteGroup.size());
        byteGroup.addBytes(padBytes);

        // 获得最终的字节流, 未加密
        final byte[] unencrypted = byteGroup.toBytes();
        try
        {
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesInnerKeyBytes, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesInnerKeyBytes, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            // 加密
            byte[] encrypted = cipher.doFinal(unencrypted);

            // 使用BASE64对加密后的字符串进行编码
            String base64Encrypted = base64.encodeToString(encrypted);

            return base64Encrypted;
        } catch (Exception ex)
        {
            final String msg = "encryption failed";
            LOGGER.error(msg, ex);
            throw new IllegalStateException(msg);
        }
    }

    /**
     * 对密文进行解密.
     *
     * @param text 需要解密的密文
     * @return 解密得到的明文
     */
    public String decrypt(String text)
    {
        byte[] raw;
        try
        {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec key_spec = new SecretKeySpec(aesInnerKeyBytes, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesInnerKeyBytes, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);

            // 使用 BASE 64 对密文进行解码
            final byte[] encrypted = Base64.decodeBase64(text);

            // 解密
            raw = cipher.doFinal(encrypted);
        } catch (Exception ex)
        {
            final String msg = "decryption failed";
            LOGGER.error(msg, ex);
            throw new IllegalStateException(msg);
        }

        String content, apiKeyFromContent;
        try
        {
            // 去除补位字符
            final byte[] bytes = PKCS7Encoder.decode(raw);

            // 分离 16 位随机字符串, 原文长度和 apiKey
            final byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

            int contentLength = recoverNetworkBytesOrder(networkOrder);
            content = new String(Arrays.copyOfRange(bytes, 20, 20 + contentLength), CHARSET);
            apiKeyFromContent = new String(Arrays.copyOfRange(bytes, 20 + contentLength, bytes.length),
                    CHARSET);
        } catch (Exception ex)
        {
            final String msg = "extract content failed";
            LOGGER.error(msg, ex);
            throw new IllegalStateException(msg);
        }

        // apiKey 不相同的情况
        if (!apiKeyFromContent.equals(apiKey))
        {
            final String msg = "apiKey check failed";
            LOGGER.error(msg);
            throw new IllegalStateException(msg);
        }
        return content;
    }

    public String encrypt(String text)
    {
        // 加密
        return EncryptionService.this.encrypt(getRandom(), text);
    }

    private static class ByteGroup
    {
        private final ArrayList<Byte> byteContainer;

        private ByteGroup()
        {
            this.byteContainer = new ArrayList<Byte>();
        }

        public byte[] toBytes()
        {
            byte[] bytes = new byte[byteContainer.size()];
            for (int i = 0; i < byteContainer.size(); i++)
            {
                bytes[i] = byteContainer.get(i);
            }
            return bytes;
        }

        public ByteGroup addBytes(byte[] bytes)
        {
            for (byte b : bytes)
            {
                byteContainer.add(b);
            }
            return this;
        }

        public int size()
        {
            return byteContainer.size();
        }
    }
}
