package org.addition.plat.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyTool {
	public static String readValue(String filePath, String key)
    {
        Properties props = new Properties();
        String value = null;
        try
        {
            InputStream ips = new BufferedInputStream(new FileInputStream(filePath));
            props.load(ips);
            value = props.getProperty(key);
            return value;
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        } catch (IOException e)
        {
            e.printStackTrace();
            return value;
        }
    }

    //读取全部信息
    public static Map<String, String> readProperties(String filePath)
    {
        Properties props = new Properties();
        Map<String, String> data = new HashMap<String, String>();
        try
        {
            InputStream ips = new BufferedInputStream(new FileInputStream(filePath));
            props.load(ips);
            Enumeration enums = props.propertyNames();
            while (enums.hasMoreElements())
            {
                String key = (String) enums.nextElement();
                String value = props.getProperty(key);

                data.put(key, value);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return data;
    }

    public static boolean writeProperties(String filePath, Map<String, String> data)
    {
        Properties props = new Properties();
        try
        {
            OutputStream ops = new FileOutputStream(filePath);
            for (String key : data.keySet())
            {
                props.setProperty(key, data.get(key));
            }
            props.store(ops, "set");
            return true;
        } catch (IOException e)
        {
            return false;
        }
    }
}
