package com.ruixin.administrator.ruixinapplication.gamecenter.domain;

import com.ruixin.administrator.ruixinapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 李丽 on 2018/6/13.
 */

public class GetNumber {
    //根据游戏类型获得投注号码
    public static int[] getArray(String gameType,int[]array){
        switch (gameType){
            case"10":
                array=new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                break;
            case"11":
                array=new int[]{2,3,4,5,6,7,8,9,10,11,12};
                break;
            case"16":
                array=new int[]{3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
                break;
            case"22":
                array=new int[]{6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27};
                break;
            case"28":
              array=new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
              break;
            case"36":
                array=new int[]{1,2,3,4,5};
                break;
            case"xs":
                array=new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                break;
            case"gy":
                array=new int[]{3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
                break;
            case"ww":
                array=new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
                break;
            case"bjl":
                array=new int[]{0,1,2};
                break;
            case"lh":
                array=new int[]{0, 1};
                break;
            case"xn":
                array=new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
                break;
           default:
                array=new int[]{0};
                break;
        }
return array;
    }
    public static HashMap getArray2(String gameType, HashMap map){
        //根据游戏类型获得投注号码，标准赔率，投注初始金币
        int[]array = null;
        double[]rate = null;
        int[]money = null;
        switch (gameType){
            case"10":
                array=new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                rate=new double[]{10,10,10,10,10,10,10,10,10,10};
                money=new int[]{100,100,100,100,100,100,100,100,100,100};
                break;
            case"11":
                array=new int[]{2,3,4,5,6,7,8,9,10,11,12};
                rate=new double[]{36,18,12,9,7.2,6,7.2,9,12,18,36};
                money=new int[]{10,20,30,40,50,60,50,40,30,20,10};
                break;
            case"16":
                array=new int[]{3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18};
                rate=new double[]{216,72,36,21.6,14.4,10.29,8.64,8,8,8.64,10.29,14.4,21.6,36,72,216};
                money=new int[]{1,3,6,10,15,21,25,27,27,25,21,15,10,6,3,1};
                break;
            case"22":
                array=new int[]{6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27};
                rate=new double[]{120.,120.,60.,40.,30.,24.,17.1429,15.,13.3333,12.,12.,12.,12.,13.3333,15.,17.1429,24.,30.,40.,60.,120.,120.};
                money=new int[]{1,1,2,3,4,5,7,8,9,10,10,10,10,9,8,7,5,4,3,2,1,1};
                break;
            case"28":
              array=new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
                rate=new double[]{1000, 333.33, 166.67, 100, 66.66, 47.61, 35.71, 27.77, 22.22, 18.18, 15.87, 14.49, 13.69, 13.33, 13.33, 13.69, 14.49, 15.87, 18.18, 22.22, 27.77, 35.71, 47.61, 66.66, 100, 166.67, 333.33, 1000};
                money=new int[]{1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 63, 69, 73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15, 10, 6, 3, 1};
              break;
            case"36":
                array=new int[]{1,2,3,4,5};
                rate=new double[]{100,16.67,3.7,2.78,3.33};
                money=new int[]{10,60,270,360,300};
                break;
            case"xs":
                array=new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                rate=new double[]{10,10,10,10,10,10,10,10,10,10};
                money=new int[]{100,100,100,100,100,100,100,100,100,100};
                break;
            case"gy":
                array=new int[]{3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
                rate=new double[]{45, 45, 22, 22, 15, 15, 11, 11, 9, 11, 11, 15, 15, 22, 22, 45, 45};
                money=new int[]{2,2,4,4,6,6,8,8,10,8,8,6,6,4,4,2,2};
                break;
            case"ww":
                array=new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                rate=new double[]{2.1, 2.1, 2.1, 2.1, 17, 17, 4.6, 4.2, 4.2, 4.6};
                money=new int[]{100,100,100,100,100,100,100,100,100,100};
                break;
            case"bjl":
                array=new int[]{0,1,2};
                rate=new double[]{2.24,2.18,10.5};
                money=new int[]{100,100,100};
                break;
            case"lh":
                array=new int[]{0, 1};
                rate=new double[]{1.9, 1.9};
                money=new int[]{100,100};
                break;
            case"xn":
                array=new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27};
                rate=new double[]{1000, 333.33, 166.67, 100, 66.66, 47.61, 35.71, 27.77, 22.22, 18.18, 15.87, 14.49, 13.69, 13.33, 13.33, 13.69, 14.49, 15.87, 18.18, 22.22, 27.77, 35.71, 47.61, 66.66, 100, 166.67, 333.33, 1000};
                money=new int[]{1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 63, 69, 73, 75, 75, 73, 69, 63, 55, 45, 36, 28, 21, 15, 10, 6, 3, 1};
                break;
        }
        map.put("array",array);
        map.put("rate",rate);
        map.put("money",money);
        return map;
    }


    public  static String get36string(int num){
        //获得36的投注结果
        String s = null;
        if(num==1){
            s="豹";
        }else if(num==2){
           s="顺";
        }else if(num==3){
            s= "对";
        }else if(num==4){
           s="半";
        }else{
            s="杂";
        }
        return s;
    }

    public  static String getbjlstring(int num){
        //获得bjl的投注结果
        String s = null;
        if(num==0){
            s="庄";
        }else if(num==1){
            s="闲";
        }else if (num==2){
            s= "和";
        }
        return s;


    }
    public  static String getlh(int num){
        //获得lh的投注结果和投注号码
        String s = null;
        if(num==0){
            s="龙";
        }else if(num==1){
            s="虎";
        }
        return s;
    }
   // '小','大','单','双','极大','极小','小单','小双','大单','大双'
    public  static String getwws(int num){
        //获得lh的投注结果和投注号码
        String s = null;
        if(num==1){
            s="小";
        }else if(num==2){
            s="大";
        }else if(num==3){
            s="单";
        }else if(num==4){
            s="双";
        }else if(num==5){
            s="极大";
        }else if(num==6){
            s="极小";
        }else if(num==7){
            s="小单";
        }else if(num==8){
            s="小双";
        }else if(num==9){
            s="大单";
        }else if(num==10){
            s="大双";
        }
        return s;
    }
    public  static String getTws(int num){
        String s = null;
        if(num==34){
            s="大";
        }else if(num==35){
            s="小";
        }else if(num==36){
            s="单";
        }else if(num==37){
            s="双";
        }else if(num==38){
            s="4";
        }else if(num==39){
            s="5";
        }else if(num==40){
            s="6";
        }else if(num==41){
            s="7";
        }else if(num==42){
            s="8";
        }else if(num==43){
            s="9";
        }else if(num==44){
            s="10";
        }else if(num==45){
            s="11";
        }else if(num==46){
            s="12";
        }else if(num==47){
            s="13";
        }else if(num==48){
            s="14";
        }else if(num==49){
            s="15";
        }else if(num==50){
            s="16";
        }else if(num==51){
            s="17";
        }
        return s;
    }
    public  static  int getWei(int num){
        int i = -1;

     String s= String.valueOf(num);
     i= Integer.parseInt(s.substring(s.length()-1,s.length()));
        return i;
    }

/*根据投注模式获得投注金币*/
    public static String getMoney(String mode, int[]array,int[]money,int i,String gameType){
        int beg = 0;
        int end=0;

        switch (gameType) {
            case "10":
                beg = 4 - 1;
                end = 7 - 1;
                break;
            case "11":
                beg = 5 - 2;
                end = 9 - 2;
                break;
            case "16":
                beg = 8 - 3;
                end = 13 - 3;
                break;
            case "22":
                beg = 13 - 6;
                end = 20 - 6;
                break;
            case "28":
                beg = 10 - 0;
                end = 17 - 0;
                break;
            case "36":
                beg = 3 - 1;
                end = 3 - 1;
                break;
            case "xs":
                beg = 4 - 1;
                end = 7 - 1;
                break;
            case "gy":
                beg = 8 - 3;
                end = 14 - 3;
                break;
            case "xn":
                beg = 10 - 0;
                end = 17 - 0;
                break;

        }
        String Money ="";
        if (mode.equals("全包")) {
           Money= String.valueOf(money[i]);
        } else if (mode.equals("单")) {
            if (array[i] % 2 == 0) {
               Money="";
            } else {
                Money= String.valueOf(money[i]);
            }

        } else if (mode.equals("双")) {
            if (array[i] % 2 == 0) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("大")) {
            if (i >=array.length / 2) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("小")) {
            if (i < array.length / 2) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("中")) {
            if (i >= beg && i <= end) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
           /* if(array.length>5){
                if (i > array.length / 3 &&i < (array.length -1- array.length / 3)) {
                    Money= String.valueOf(money[i]);
                } else {
                    Money="";
                }
            }else{
                if (i==(array.length-1)/2) {
                    Money= String.valueOf(money[i]);
                } else {
                    Money="";
                }
            }*/

        } else if (mode.equals("边")) {
            if (i < beg ||i > end) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
          /*  if(array.length>5){
                if (i < array.length / 3 || i >= (array.length -1- array.length / 3)) {
                    Money= String.valueOf(money[i]);
                } else {
                    Money="";
                }
            }else{
                if (i!=(array.length-1)/2) {
                    Money= String.valueOf(money[i]);
                } else {
                    Money="";
                }
            }*/

        } else if (mode.equals("大单")) {
            if ((i > array.length / 2) && (array[i] % 2 != 0)) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("小单")) {
            if ((i < array.length / 2) && (array[i] % 2 != 0)) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("大双")) {
            if ((i > array.length / 2) && (array[i] % 2 == 0)) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("小双")) {
            if ((i < array.length / 2) && (array[i] % 2 == 0)) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("大边")) {
            if (i > end) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
          /*  if (i >= (array.length - array.length / 3)) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }*/
        } else if (mode.equals("小边")) {
            if (i <beg) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
          /*  if (i < array.length / 3) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }*/
        } else if (mode.equals("单边")) {
            if (((i < beg ||i > end)) && array[i] % 2 != 0) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("双边")) {
            if (((i < beg ||i > end)) && array[i] % 2 == 0) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("0尾")) {
            if (GetNumber.getWei(array[i]) == 0) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("1尾")) {
            if (GetNumber.getWei(array[i]) == 1) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("2尾")) {
            if (GetNumber.getWei(array[i]) == 2) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("3尾")) {
            if (GetNumber.getWei(array[i]) == 3) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("4尾")) {
            if (GetNumber.getWei(array[i]) == 4) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("5尾")) {
            if (GetNumber.getWei(array[i]) == 5) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("6尾")) {
            if (GetNumber.getWei(array[i]) == 6) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("7尾")) {
            if (GetNumber.getWei(array[i]) == 7) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("8尾")) {
            if (GetNumber.getWei(array[i]) == 8) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("9尾")) {
            if (GetNumber.getWei(array[i]) == 9) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("小尾")) {
            if (GetNumber.getWei(array[i]) == 0 || GetNumber.getWei(array[i]) == 1 || GetNumber.getWei(array[i]) == 2 || GetNumber.getWei(array[i]) == 3 || GetNumber.getWei(array[i]) == 4) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("大尾")) {
            if (GetNumber.getWei(array[i]) == 5 || GetNumber.getWei(array[i]) == 6 || GetNumber.getWei(array[i]) == 7 || GetNumber.getWei(array[i]) == 8 || GetNumber.getWei(array[i]) == 9) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("3余0")) {
            if (array[i] % 3 == 0) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("3余1")) {
            if (array[i] % 3 == 1) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("3余2")) {
            if (array[i] % 3 == 2) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("4余0")) {
            if (array[i] % 4 == 0) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("4余1")) {
            if (array[i] % 4 == 1) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("4余2")) {
            if (array[i] % 4 == 2) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("4余3")) {
            if (array[i] % 4 == 3) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("5余0")) {
            if (array[i] % 5 == 0) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("5余1")) {
            if (array[i] % 5 == 1) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("5余2")) {
            if (array[i] % 5 == 2) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("5余3")) {
            if (array[i] % 5 == 3) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else if (mode.equals("5余4")) {
            if (array[i] % 5 == 4) {
                Money= String.valueOf(money[i]);
            } else {
                Money="";
            }
        } else {
            Money="";
        }
        return Money;
    }

    public  static String getdws(int num){
        String s = null;
        if(num==0||num==15||num==29||num==43){
            s="单";
        }else if(num==1||num==13||num==27||num==41){
            s="大";
        }else if(num==2){
            s="小单";
        }else if(num==3){
            s="大单";
        }else if(num==4){
            s="极小";
        }else if(num==6||num==14||num==28||num==42){
            s="小";
        }else if(num==5||num==16||num==30||num==44){
            s="双";
        }else if(num==7){
            s="小双";
        }else if(num==8){
            s="大双";
        }else if(num==9){
            s="极大";
        }else if(num==10){
            s="龙";
        }else if(num==11){
            s="虎";
        }else if(num==12){
            s="豹";
        }else if(num==17||num==31||num==45){
            s="0";
        }else if(num==18||num==32||num==46){
            s="1";
        }else if(num==19||num==33||num==47){
            s="2";
        }else if(num==20||num==34||num==48){
            s="3";
        }else if(num==21||num==35||num==49){
            s="4";
        }else if(num==22||num==36||num==50){
            s="5";
        }else if(num==23||num==37||num==51){
            s="6";
        }else if(num==24||num==38||num==52){
            s="7";
        }else if(num==25||num==39||num==53){
            s="8";
        }else if(num==26||num==40||num==54){
            s="9";
        }
        return s;
    }
    public static List<Integer> getTbww(int num){
        List<Integer>list = new ArrayList<>();
        list.clear();
        if(num==0){
            list.add(R.drawable.tb1);
            list.add(R.drawable.tb1);
            list.add(R.drawable.tb1);
        } if(num==1){
            list.add(R.drawable.tb2);
            list.add(R.drawable.tb2);
            list.add(R.drawable.tb2);
        } if(num==2){
            list.add(R.drawable.tb3);
            list.add(R.drawable.tb3);
            list.add(R.drawable.tb3);
        } if(num==3){
            list.add(R.drawable.tb4);
            list.add(R.drawable.tb4);
            list.add(R.drawable.tb4);
        } if(num==4){
            list.add(R.drawable.tb5);
            list.add(R.drawable.tb5);
            list.add(R.drawable.tb5);
        } if(num==5){
            list.add(R.drawable.tb6);
            list.add(R.drawable.tb6);
            list.add(R.drawable.tb6);
        } if(num==6){
           list.clear();
        }
        if(num==22){
          list.add(R.drawable.tb1);
          list.add(R.drawable.tb1);
        } if(num==23){
            list.add(R.drawable.tb2);
            list.add(R.drawable.tb2);
        } if(num==24){
            list.add(R.drawable.tb3);
            list.add(R.drawable.tb3);
        } if(num==25){
            list.add(R.drawable.tb4);
            list.add(R.drawable.tb4);
        } if(num==26){
            list.add(R.drawable.tb5);
            list.add(R.drawable.tb5);
        } if(num==27){
            list.add(R.drawable.tb6);
            list.add(R.drawable.tb6);
        }  if(num==28){
          list.add(R.drawable.tb1);
        } if(num==29){
            list.add(R.drawable.tb2);
        } if(num==30){
            list.add(R.drawable.tb3);
        } if(num==31){
            list.add(R.drawable.tb4);

        } if(num==32){
            list.add(R.drawable.tb5);

        } if(num==33){
            list.add(R.drawable.tb6);

        }if(num==7){
            list.add(R.drawable.tb1);
            list.add(R.drawable.tb2);
        }if(num==8){
            list.add(R.drawable.tb1);
            list.add(R.drawable.tb3);
        }if(num==9){
            list.add(R.drawable.tb1);
            list.add(R.drawable.tb4);
        }if(num==10){
            list.add(R.drawable.tb1);
            list.add(R.drawable.tb5);
        }if(num==11){
            list.add(R.drawable.tb1);
            list.add(R.drawable.tb6);
        }if(num==12){
            list.add(R.drawable.tb2);
            list.add(R.drawable.tb3);
        }if(num==13){
            list.add(R.drawable.tb2);
            list.add(R.drawable.tb4);
        }if(num==14){
            list.add(R.drawable.tb2);
            list.add(R.drawable.tb5);
        }if(num==15){
            list.add(R.drawable.tb2);
            list.add(R.drawable.tb6);
        }if(num==16){
            list.add(R.drawable.tb3);
            list.add(R.drawable.tb4);
        }if(num==17){
            list.add(R.drawable.tb3);
            list.add(R.drawable.tb5);
        }if(num==18){
            list.add(R.drawable.tb3);
            list.add(R.drawable.tb6);
        }if(num==19){
            list.add(R.drawable.tb4);
            list.add(R.drawable.tb5);
        }if(num==20){
            list.add(R.drawable.tb4);
            list.add(R.drawable.tb6);
        }if(num==21){
            list.add(R.drawable.tb5);
            list.add(R.drawable.tb6);
        }
        return list;
    }
    public static List<String> getMoneyPoints(String gameType){
        List<String>list=new ArrayList<>();
        switch (gameType) {
            case "ssc":
                for(int i=0;i<92;i++){
                    list.add("0");
                }
                break;
            case "pkww":
                for(int i=0;i<171;i++){
                    list.add("0");
                }
                break;
            case "xync":
                for(int i=0;i<270;i++){
                    list.add("0");
                }
                break;
            case "dw":
                for(int i=0;i<55;i++){
                    list.add("0");
                }
                break;
            case "tbww":
                for(int i=0;i<52;i++){
                    list.add("0");
                }
                break;
        }
        return list;
    }
    public static HashMap getArray3(String gameType,int i, HashMap map){
        int[]array = null;
        double[]rate = null;
        int[]money = null;
        switch (gameType) {
            case "ssc":
                switch (i) {
                    case 0:
                        array = new int[]{0, 1, 2, 3, 4, 5, 6};
                        rate = new double[]{1.98, 1.98, 1.98, 1.98, 1.98, 1.98, 9.1};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 1:
                        array = new int[]{7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
                        rate = new double[]{1.98, 1.98, 1.98, 1.98, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 2:
                        array = new int[]{21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34};
                        rate = new double[]{1.98, 1.98, 1.98, 1.98, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 3:
                        array = new int[]{35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48};
                        rate = new double[]{1.98, 1.98, 1.98, 1.98, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 4:
                        array = new int[]{49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62};
                        rate = new double[]{1.98, 1.98, 1.98, 1.98, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 5:
                        array = new int[]{63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76};
                        rate = new double[]{1.98, 1.98, 1.98, 1.98, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9, 9.9};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 6:
                        array = new int[]{77, 78, 79, 80, 81};
                        rate = new double[]{69, 2.8, 13, 2, 2.3};
                        money = new int[]{100, 100, 100, 100, 100};
                        break;
                    case 7:
                        array = new int[]{82, 83, 84, 85, 86};
                        rate = new double[]{69, 2.8, 13, 2, 2.3};
                        money = new int[]{100, 100, 100, 100, 100};
                        break;
                    case 8:
                        array = new int[]{87, 88, 89, 90, 91};
                        rate = new double[]{69, 2.8, 13, 2, 2.3};
                        money = new int[]{100, 100, 100, 100, 100};
                        break;
                }
                break;
            case "pkww":
                switch (i) {
                    case 0:
                        array = new int[]{0, 1, 2, 3, 4, 5, 6,7, 8, 9, 10, 11, 12, 13};
                        rate = new double[]{1.986, 1.986, 1.986, 1.986,9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 1:
                        array = new int[]{14, 15, 16, 17, 18, 19, 20,21, 22, 23, 24, 25, 26, 27};
                        rate = new double[]{1.986, 1.986, 1.986, 1.986,9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 2:
                        array = new int[]{28, 29, 30, 31, 32, 33, 34,35, 36, 37, 38, 39, 40, 41};
                        rate = new double[]{1.986, 1.986, 1.986, 1.986,9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 3:
                        array = new int[]{42, 43, 44, 45, 46, 47, 48,49, 50, 51, 52, 53, 54, 55};
                        rate = new double[]{1.986, 1.986, 1.986, 1.986,9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 4:
                        array = new int[]{56, 57, 58, 59, 60, 61, 62,63, 64, 65, 66, 67, 68, 69};
                        rate = new double[]{1.986, 1.986, 1.986, 1.986,9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 5:
                        array = new int[]{70, 71, 72, 73, 74, 75, 76,77, 78, 79, 80, 81,82, 83};
                        rate = new double[]{1.986, 1.986, 1.986, 1.986,9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 6:
                        array = new int[]{84, 85, 86,87, 88, 89, 90,91,92,93,94,95,96,97};
                        rate = new double[]{1.986, 1.986, 1.986, 1.986,9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 7:
                        array = new int[]{98,99,100,101,102,103,104,105,106,107,108,109,110,111 };
                        rate = new double[]{1.986, 1.986, 1.986, 1.986,9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 8:
                        array = new int[]{112,113,114,115,116,117,118,119,120,121,122,123,124,125};
                        rate = new double[]{1.986, 1.986, 1.986, 1.986,9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 9:
                        array = new int[]{126,127,128,129,130,131,132,133,134,135,136,137,137,139};
                        rate = new double[]{1.986, 1.986, 1.986, 1.986,9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93, 9.93};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 10:
                        array = new int[]{150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170};
                        rate = new double[]{2.186,1.786,1.786,2.186,40,40,20,20,13,13,10,10,9,9,10,10,13,13,20,20,40,40};
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100,100, 100, 100, 100, 100, 100, 100};
                        break;
                    case 11:
                        array = new int[]{140,141};
                        rate = new double[]{1.986,1.986};
                        money = new int[]{100, 100};
                        break;
                    case 12:
                        array = new int[]{142,143};
                        rate = new double[]{1.986,1.986};
                        money = new int[]{100, 100};
                        break;
                    case 13:
                        array = new int[]{144,145};
                        rate = new double[]{1.986,1.986};
                        money = new int[]{100, 100};
                        break;
                    case 14:
                        array = new int[]{146,147};
                        rate = new double[]{1.986,1.986};
                        money = new int[]{100, 100};
                        break;
                    case 15:
                        array = new int[]{148,149};
                        rate = new double[]{1.986,1.986};
                        money = new int[]{100, 100};
                        break;
                }
                break;
            case "xync":
                switch (i) {
                    case 0:
                        array = new int[]{0, 1, 2, 3, 4, 5};
                        rate = new double[]{1.98, 1.98, 1.98, 1.98, 1.98, 1.98};
                        money = new int[]{100, 100, 100, 100, 100, 100};
                        break;
                    case 1:
                        array = new int[]{ 6,7, 8, 9, 10, 11, 12, 13,14, 15, 16, 17, 18, 19, 20,21, 22, 23, 24, 25, 26, 27,28, 29, 30, 31, 32, 33, 34,35, 36,37};
                        rate = new double[]{19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,3.95,3.95,3.95,3.95 };
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100,100,100,100};
                        break;
                    case 2:
                        array = new int[]{38, 39, 40, 41,42, 43, 44, 45, 46, 47, 48,49, 50, 51, 52, 53, 54, 55,56, 57, 58, 59, 60, 61, 62,63, 64, 65, 66, 67, 68, 69};
                        rate = new double[]{19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,3.95,3.95,3.95,3.95 };
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100,100,100,100};
                        break;
                    case 3:
                        array = new int[]{70, 71, 72, 73, 74, 75, 76,77, 78, 79, 80, 81,82, 83,84, 85, 86,87, 88, 89, 90,91,92,93,94,95,96,97,98,99,100,101};
                        rate = new double[]{19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,3.95,3.95,3.95,3.95 };
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100,100,100,100};
                        break;
                    case 4:
                        array = new int[]{102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133};
                        rate = new double[]{19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,3.95,3.95,3.95,3.95 };
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100,100,100,100};
                        break;
                    case 5:
                        array = new int[]{134,135,136,137,137,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165};
                        rate = new double[]{19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,3.95,3.95,3.95,3.95 };
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100,100,100,100};
                        break;
                    case 6:
                        array = new int[]{166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197};
                        rate = new double[]{19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,3.95,3.95,3.95,3.95 };
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100,100,100,100};
                        break;
                    case 7:
                        array = new int[]{198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229 };
                        rate = new double[]{19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,3.95,3.95,3.95,3.95 };
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100,100,100,100};
                        break;
                    case 8:
                        array = new int[]{230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261};
                        rate = new double[]{19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,19.8,1.98,1.98,1.98,1.98,1.98,1.98,1.98,1.98,3.95,3.95,3.95,3.95 };
                        money = new int[]{100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100, 100, 100, 100, 100, 100, 100,100,100,100,100};
                        break;
                    case 9:
                        array = new int[]{262,266};
                        rate = new double[]{1.98,1.98};
                        money = new int[]{100, 100};
                        break;
                    case 10:
                        array = new int[]{263,267};
                        rate = new double[]{1.98,1.98};
                        money = new int[]{100, 100};
                        break;
                    case 11:
                        array = new int[]{264,268};
                        rate = new double[]{1.98,1.98};
                        money = new int[]{100, 100};
                        break;
                    case 12:
                        array = new int[]{265,269};
                        rate = new double[]{1.98,1.98};
                        money = new int[]{100, 100};
                        break;
                }
                break;
            case "dw":
                switch (i) {
                    case 0:
                       array=new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12};
                       rate=new double[]{1.98,1.98,3.68,4.2,16.0,1.98,1.98,4.2,3.68,16.0,2.9,2.9,2.9};
                       money=new int[]{100,100,100,100,100,100,100,100,100,100,100,100,100};
                        break;
                    case 1:
                       array=new int[]{13,14,15,16,17,18,19,20,21,22,23,24,25,26};
                        rate=new double[]{1.98,1.98,1.98,1.98,9.9,9.9,9.9,9.9,9.9,9.9,9.9,9.9,9.9,9.9};
                       money=new int[]{100,100,100,100,100,100,100,100,100,100,100,100,100,100};
                        break;
                    case 2:
                        array=new int[]{27,28,29,30,31,32,33,34,35,36,37,38,39,40};
                        rate=new double[]{1.98,1.98,1.98,1.98,9.9,9.9,9.9,9.9,9.9,9.9,9.9,9.9,9.9,9.9};
                      money=new int[]{100,100,100,100,100,100,100,100,100,100,100,100,100,100};
                        break;
                    case 3:
                       array=new int[]{41,42,43,44,45,46,47,48,49,50,51,52,52,54};
                        rate=new double[]{1.98,1.98,1.98,1.98,9.9,9.9,9.9,9.9,9.9,9.9,9.9,9.9,9.9,9.9};
                       money=new int[]{100,100,100,100,100,100,100,100,100,100,100,100,100,100};
                        break;
                }
                break;
            case "tbww":
                switch (i) {
                    case 0:
                        array=new int[]{34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51};
                       rate=new double[]{1.993,1.993,1.993,1.993,60,30,19,13,9.3,7.2,7.2,7.2,7.2,9.3,13,19,30,60};
                       money=new int[]{100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100};
                        break;
                    case 1:
                        array=new int[]{22,23,24,25,26,27};
                        rate=new double[]{11,11,11,11,11,11};
                       money=new int[]{100,100,100,100,100,100};
                        break;
                    case 2:
                      array=new int[]{7,8,9,10,11,12,13,14,15,16,17,18,19,20,21};
                       rate=new double[]{6,6,6,6,6,6,6,6,6,6,6,6,6,6,6};
                       money=new int[]{100,100,100,100,100,100,100,100,100,100,100,100,100,100,100};
                        break;
                    case 3:
                       array=new int[]{28,29,30,31,32,33};
                       rate=new double[]{2,2,2,2,2,2};
                      money=new int[]{100,100,100,100,100,100};
                        break;
                    case 4:
                       array=new int[]{0,1,2,3,4,5,6};
                       rate=new double[]{168,168,168,168,168,168,26};
                       money=new int[]{100,100,100,100,100,100,100};
                        break;
                }
                break;
        }
        map.put("array",array);
        map.put("rate",rate);
        map.put("money",money);
        return map;
    }
    public  static String getsscs(int num){
        String s = null;
        if(num==0||num==7||num==21||num==35||num==49||num==63){
            s="大";
        }else if(num==1||num==8||num==22||num==36||num==50||num==64){
            s="小";
        }else if(num==2||num==9||num==23||num==37||num==51||num==65){
            s="单";
        }else if(num==3||num==10||num==24||num==38||num==52||num==66){
            s="双";
        }else if(num==4){
            s="龙";
        }else if(num==5){
            s="虎";
        }else if(num==6){
            s="和";
        }else if(num==11||num==25||num==39||num==53||num==67){
            s="0";
        }else if(num==12||num==26||num==40||num==54||num==68){
            s="1";
        }else if(num==13||num==27||num==41||num==55||num==69){
            s="2";
        }else if(num==14||num==28||num==42||num==56||num==70){
            s="3";
        }else if(num==15||num==29||num==43||num==57||num==71){
            s="4";
        }else if(num==16||num==30||num==44||num==58||num==72){
            s="5";
        }else if(num==17||num==31||num==45||num==59||num==73){
            s="6";
        }else if(num==18||num==32||num==46||num==60||num==74){
            s="7";
        }else if(num==19||num==33||num==47||num==61||num==75){
            s="8";
        }else if(num==20||num==34||num==48||num==62||num==76){
            s="9";
        }else if(num==77||num==82||num==87){
            s="豹";
        }else if(num==78||num==83||num==88){
            s="对";
        }else if(num==79||num==84||num==89){
            s="顺";
        }else if(num==80||num==85||num==90){
            s="半";
        }else if(num==81||num==86||num==91){
            s="杂";
        }
        return s;
    }
    public  static String getpkwws(int num){
        String s = null;
        if(num==0||num==14||num==28||num==42||num==56||num==70||num==84||num==98||num==112||num==126||num==150){
            s="大";
        }else if(num==1||num==15||num==29||num==43||num==57||num==71||num==85||num==99||num==113||num==127||num==151){
            s="小";
        }else if(num==2||num==16||num==30||num==44||num==58||num==72||num==86||num==100||num==114||num==128||num==152){
            s="单";
        }else if(num==3||num==17||num==31||num==45||num==59||num==73||num==87||num==101||num==115||num==129||num==153){
            s="双";
        }else if(num==140||num==142||num==144||num==146||num==148){
            s="龙";
        }else if(num==141||num==143||num==145||num==147||num==149){
            s="虎";
        }else if(num==4||num==18||num==32||num==46||num==60||num==74||num==88||num==102||num==116||num==130){
            s="1";
        }else if(num==5||num==19||num==33||num==47||num==61||num==75||num==89||num==103||num==117||num==131){
            s="2";
        }else if(num==6||num==20||num==34||num==48||num==62||num==76||num==90||num==104||num==118||num==132||num==154){
            s="3";
        }else if(num==7||num==21||num==35||num==49||num==63||num==77||num==91||num==105||num==119||num==133||num==155){
            s="4";
        }else if(num==8||num==22||num==36||num==50||num==64||num==78||num==92||num==106||num==120||num==134||num==156){
            s="5";
        }else if(num==9||num==23||num==37||num==51||num==65||num==79||num==93||num==107||num==121||num==135||num==157){
            s="6";
        }else if(num==10||num==24||num==38||num==52||num==66||num==80||num==94||num==108||num==122||num==136||num==158){
            s="7";
        }else if(num==11||num==25||num==39||num==53||num==67||num==81||num==95||num==109||num==123||num==137||num==159){
            s="8";
        }else if(num==12||num==26||num==40||num==54||num==68||num==82||num==96||num==110||num==124||num==138||num==160){
            s="9";
        }else if(num==13||num==27||num==41||num==55||num==69||num==83||num==97||num==111||num==125||num==139||num==161){
            s="10";
        }else if(num==162){
            s="11";
        }else if(num==163){
            s="12";
        }else if(num==164){
            s="13";
        }else if(num==165){
            s="14";
        }else if(num==166){
            s="15";
        }else if(num==167){
            s="16";
        }else if(num==168){
            s="17";
        }else if(num==169){
            s="18";
        }else if(num==170){
            s="19";
        }
        return s;
    }
    public  static String getxyncs(int num){
        String s = null;
        if(num==0||num==26||num==58||num==90||num==122||num==154||num==186||num==218||num==250){
            s="大";
        }else if(num==4||num==27||num==59||num==91||num==123||num==155||num==187||num==219||num==251){
            s="双";
        }else if(num==2||num==28||num==60||num==92||num==124||num==156||num==188||num==220||num==252){
            s="尾大";
        }else if(num==29||num==61||num==93||num==125||num==157||num==189||num==221||num==253){
            s="合双";
        }else if(num==3||num==30||num==62||num==94||num==126||num==158||num==190||num==222||num==254){
            s="小";
        }else if(num==1||num==31||num==63||num==95||num==127||num==159||num==191||num==223||num==255){
            s="单";
        }else if(num==5||num==32||num==64||num==96||num==128||num==160||num==192||num==224||num==256){
            s="尾小";
        }else if(num==33||num==65||num==97||num==129||num==161||num==193||num==225||num==257){
            s="合单";
        }else if(num==34||num==66||num==98||num==130||num==162||num==194||num==226||num==258){
            s="东";
        }else if(num==35||num==67||num==99||num==131||num==163||num==195||num==227||num==259){
            s="南";
        }else if(num==36||num==68||num==100||num==132||num==164||num==196||num==228||num==260){
            s="西";
        }else if(num==37||num==69||num==101||num==133||num==165||num==197||num==229||num==261){
            s="北";
        }else if(num==6||num==38||num==70||num==102||num==134||num==166||num==198||num==230){
            s="1";
        }else if(num==7||num==39||num==71||num==103||num==135||num==167||num==199||num==231){
            s="2";
        }else if(num==8||num==40||num==72||num==104||num==136||num==168||num==200||num==232){
            s="3";
        }else if(num==9||num==41||num==73||num==105||num==137||num==169||num==201||num==233){
            s="4";
        }else if(num==10||num==42||num==74||num==106||num==138||num==170||num==202||num==234){
            s="5";
        }else if(num==11||num==43||num==75||num==107||num==139||num==171||num==203||num==235){
            s="6";
        }else if(num==12||num==44||num==76||num==108||num==140||num==172||num==204||num==236){
            s="7";
        }else if(num==13||num==45||num==77||num==109||num==141||num==173||num==205||num==237){
            s="8";
        }else if(num==14||num==46||num==78||num==110||num==142||num==174||num==206||num==238){
            s="9";
        }else if(num==15||num==47||num==79||num==111||num==143||num==175||num==207||num==239){
            s="10";
        }else if(num==16||num==48||num==80||num==112||num==144||num==176||num==208||num==240){
            s="11";
        }else if(num==17||num==49||num==81||num==113||num==145||num==177||num==209||num==241){
            s="12";
        }else if(num==18||num==50||num==82||num==114||num==146||num==178||num==210||num==242){
            s="13";
        }else if(num==19||num==51||num==83||num==115||num==147||num==179||num==211||num==243){
            s="14";
        }else if(num==20||num==52||num==84||num==116||num==148||num==180||num==212||num==244){
            s="15";
        }else if(num==21||num==53||num==85||num==117||num==149||num==181||num==213||num==245){
            s="16";
        }else if(num==22||num==54||num==86||num==118||num==150||num==182||num==214||num==246){
            s="17";
        }else if(num==23||num==55||num==87||num==119||num==151||num==183||num==215||num==247){
            s="18";
        }else if(num==24||num==56||num==88||num==120||num==152||num==184||num==216||num==248){
            s="19";
        }else if(num==25||num==57||num==89||num==121||num==153||num==185||num==217||num==249){
            s="20";
        } else if(num==262||num==263||num==264||num==265){
            s="龙";
        }else if(num==266||num==267||num==268||num==269){
            s="虎";
        }
        return s;
    }
}
