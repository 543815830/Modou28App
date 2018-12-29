package com.ruixin.administrator.ruixinapplication.gamecenter.domain;

/**
 * Created by 李丽 on 2018/6/14.
 */

public class GetTab {
    public static String[] getArray(String gameType,String[]array){
        switch (gameType){
            case"ssc":
                array=new String[]{"总和","球一","球二","球三","球四","球五","前三","中三","后三"};
                break;
            case"xync":
                array=new String[]{"总和","球一","球二","球三","球四","球五","球六","球七","球八","1V8龙虎","2V7龙虎","3V6龙虎","4V5龙虎"};
                break;
            case"pkww":
                array=new String[]{"第一名","第二名","第三名","第四名","第五名","第六名","第七名","第八名","第九名","第十名","冠亚军和","1V10龙虎","2V9龙虎","3V8龙虎","4V7龙虎","5V6龙虎"};
                break;
        }
        return array;
    }
}
