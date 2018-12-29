package com.ruixin.administrator.ruixinapplication.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * 作者：Created by ${李丽} on 2018/3/14.
 * 邮箱：543815830@qq.com
 * 让字符串数据每隔三位加一个逗号的工具类/md5/超过7位已万为结尾
 */

public class FormatUtils {
    public static String formatString(String data) {
        data=new StringBuilder(data).reverse().toString();//先将字符串颠倒顺序  
        String str2="";
        for(int i=0;i<data.length();i++){
            if(i*3+3>data.length()){
                str2+=data.substring(i*3,data.length());
                break;
            }
            str2+=data.substring(i*3,i*3+3)+",";
        }
        if(str2.endsWith(",")){
            str2=str2.substring(0,str2.length()-1);
        }
//最后再将顺序反转过来  
        return (new StringBuilder(str2).reverse().toString());
    }


    /*MD5加密*/
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


        public static String forMatWan(String s) {

            Scanner scan = new Scanner(s);

            long num = scan.nextLong();

            if(num<1000000){
                return formatString(String.valueOf(num)) ;

            }else{

                int n = (int) num/10000;
              String re=n+"万";
              return  re;

            }



    }

    public static String format2(double value) {

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(value);
    }
}
