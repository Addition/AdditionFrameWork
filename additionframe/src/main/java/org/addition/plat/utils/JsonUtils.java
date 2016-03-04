/*
 * com.genew.nuas
 * JsonUtils.java
 * 2015-03-26
 * Copyright (c) Genew Technologies 2010-2015. All rights reserved.
 */
package org.addition.plat.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * JSON 工具<p/>
 * @version 1.0.0
 * @since 1.0.0
 * @author XXZ
 * @history<br/>
 * ver    date       author desc
 * 1.0.0  2015-03-26 XXZ    created<br/>
 * <p/>
 */
public class JsonUtils
{
    private JsonUtils()
    {
    }

    public static String buildSimpleJsonEntiry(String code, String desc)
    {
        return buildSimpleJsonEntiry(code, desc, null);
    }

    public static String buildSimpleJsonEntiry(String code, String desc, String data)
    {
        StringBuilder b = new StringBuilder("{");
        b.append("\"status\": {");
        b.append("\"code\": ").append(code);
        if (!StringUtils.isEmpty(desc))
        {
            b.append(",");
            b.append("\"desc\": \"").append(desc).append("\"");
        }
        b.append("}");
        if (!StringUtils.isEmpty(data))
        {
            b.append(",");
            b.append("\"data\":");
            b.append(data);
        }
        b.append("}");
        return b.toString();
    }
}
