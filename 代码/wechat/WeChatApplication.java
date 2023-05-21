package com.wechat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Properties;

public class WeChatApplication
{

    public static void main(String[] args) throws ParseException, IOException {
        System.out.println("Start Success !");
        Execute execute = new Execute();
        // ��ȡ�����ļ�
        Properties prop = new Properties();
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\wechatjobconf\\config.properties"));
        BufferedReader bf = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
        prop.load(bf);
        String friendname = prop.getProperty("wechatsend-friendname");
        String time = prop.getProperty("time");
        String type = prop.getProperty("type");
        String msg = prop.getProperty("msg");
        fileInputStream.close();

        // execute.everyday("�ļ���������","22:00:00","ZY:��������");
        execute.everydayimage(friendname, time, type, msg);
    }
}
