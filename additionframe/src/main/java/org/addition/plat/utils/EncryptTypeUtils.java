package org.addition.plat.utils;

public class EncryptTypeUtils
{
    private EncryptTypeUtils()
    {
    }

    public static final String ENCRYPT_TYPE_RAW = "raw";

    public static final String ENCRYPT_TYPE_ENCRYPT = "encrypt";

    public static final boolean isEncryptType(String type)
    {
        return ENCRYPT_TYPE_ENCRYPT.equals(type);
    }
}
