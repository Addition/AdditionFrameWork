package org.addition.plat.utils;

public class HttpHeaderNames
{
    private HttpHeaderNames()
    {
    }
    
    /**
     * 安全令牌
     */
    public static final String AUTH_TOKEN = "X-Token";

    /**
     * 总页数
     */
    public static final String TOTAL_PAGE = "X-Total-Pages";

    /**
     * 总记录数
     */
    public static final String TOTAL = "X-Total";

    /**
     * 当前页
     */
    public static final String PAGE = "X-Page";

    /**
     * 每页记录数
     */
    public static final String Per_PAGE = "X-Per-Page";

    /**
     * 牛信加密签名
     */
    public static final String SIGNATURE = "X-Signature";

    /**
     * HTTP 头的时间戳参数
     */
    public static final String TIMESTAMP = "X-Timestamp";

    /**
     * HTTP 头的随机数
     */
    public static final String NONCE = "X-Nonce";

    /**
     * HTTP 头的加密类型
     */
    public static final String ENCRYPT_TYPE = "X-Encrypt-Type";

}
