package com.ruixin.administrator.ruixinapplication.utils;

import com.ruixin.administrator.ruixinapplication.R;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by 李丽 on 2018/6/7.
 */

public class GetGameResult {
//36的结果
    public  static  String get36rs(int num1,int num2,int num3){
        if(num1 == 9 || num2 == 9 || num3 == 9){
            if(num1 == 0){
                num1 = 10;
            }
            if(num2 == 0){
                num2 = 10;
            }
            if(num3 == 0){
                num3 = 10;
            }
        }
        if (num1 == num2 && num2 == num3) {
            return "豹";//豹子
        }
        if ((abs(num1 - num2) >= 1 && abs(num1 - num2) <= 2) && (abs(num1 - num3) >= 1 && abs(num1 - num3) <= 2) && (abs(num2 - num3) >= 1 && abs(num2 - num3) <= 2)) {
            return "顺";//顺子
        }
        if(num1 == 9 || num2 == 9 || num3 == 9){
            if(num1 == 10){
                num1 = 0;
            }
            if(num2 == 10){
                num2 = 0;
            }
            if(num3 == 10){
                num3 = 0;
            }
        }
        if ((num1 == 1 && num2 == 9 && num3 == 0) || (num1 == 1 && num2 == 0 && num3 == 9) || (num1 == 0 && num2 == 9 && num3 == 1) || (num1 == 0 && num2 == 1 && num3 == 9) || (num1 == 9 && num2 == 0 && num3 == 1) || (num1 == 9 && num2 == 1 && num3 == 0)) {
            return "顺";//顺子 特殊6种
        }
        if (num1 == num2 || num1 == num3 || num2 == num3) {
            return "对";//对子
        }
        if (abs(num1 - num2) == 1 || abs(num1 - num3) == 1 || abs(num2 - num3) == 1) {
            return "半";//半
        }
        if ((num1 == 0 && (num2 == 9 || num3 == 9)) || (num2 == 0 && (num1 == 9 || num3 == 9)) || (num3 == 0 && (num1 == 9 || num2 == 9))) {
            return "半";//特殊半
        }
        return "杂";
    }
    //外围的结果
    public  static String getwwrs(int num1, int num2, int num3, int num4){
        List<String>list=new ArrayList<>();
        StringBuilder res = new StringBuilder();
        if(num1==num2&&num2==num3&&num3==num1){
           list.add("豹");
        }if(num4<=5){
            list.add("极小");
        }if(num4<=13){
            list.add("小");
        }if(num4>=14){
            list.add("大");
        }if(num4>=22){
            list.add("极大");
        }
        if(num4%2==0){
            list.add("双");
        }else{
            list.add("单");
        }
        for(int j=0;j<list.size();j++){
            res.append(list.get(j));
            res.append(',');
        }
        if(res.length()>0){
            res.deleteCharAt(res.length()-1);
        }
        return res.toString();
    }
    public  static String getdwrs(int num4){
        List<String>list=new ArrayList<>();
        StringBuilder res = new StringBuilder();
        if(num4<=5){
            list.add("极小");
        }if(num4<=13){
            list.add("小");
        }if(num4>=14){
            list.add("大");
        }if(num4>=22){
            list.add("极大");
        }
        if(num4%2==0){
            list.add("双");
        }else{
            list.add("单");
        }if(num4%3==0){
            list.add("龙");
        }else if(num4%3==1){
            list.add("虎");
        }else if(num4%3==2){
            list.add("豹");
        }


        for(int j=0;j<list.size();j++){
            res.append(list.get(j));
            res.append(',');
        }
        if(res.length()>0){
            res.deleteCharAt(res.length()-1);
        }
        return res.toString();
    }
    public  static String getbjlrs(int num4){
       String res = null;
       if(num4==0){
           res="庄";
       }else if(num4==1){
           res="闲";
       }else if(num4==2){
           res="和";
       }
        return res;
    }
    public  static String getlhrs(int num4){
       String res = null;
       if(num4%2==0){
           res="龙";
       }else if(num4%2==1){
           res="虎";
       }
        return res;
    }
    public static  int getTbww(int num){
    int result = 0;
        if(num==1){
            result=R.drawable.tb1;
        } if(num==2){
            result=R.drawable.tb2;
        } if(num==3){
            result=R.drawable.tb3;
        } if(num==4){
            result=R.drawable.tb4;
        } if(num==5){
            result=R.drawable.tb5;
        } if(num==6){
            result=R.drawable.tb6;
        }
    return result;
}

public  static  String getGno(String s){
    String b;
        if(s.length()>=7){
           b=s.substring(s.length()-7,s.length());
        }else{
            b=s;
        }

    return  b;
}
}
