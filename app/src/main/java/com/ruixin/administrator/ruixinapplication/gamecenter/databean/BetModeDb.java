package com.ruixin.administrator.ruixinapplication.gamecenter.databean;
import java.util.ArrayList;
import java.util.List;
/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class BetModeDb {
    private static final List<BetMode> list = new ArrayList<BetMode>();
    static{
        list.add(new BetMode("全包"));
        list.add(new BetMode("单"));
        list.add(new BetMode("双"));
        list.add(new BetMode("大"));
        list.add(new BetMode("小"));
        list.add(new BetMode("中"));
        list.add(new BetMode("边"));
        list.add(new BetMode("大单"));
        list.add(new BetMode("小单"));
        list.add(new BetMode("大双"));
        list.add(new BetMode("小双"));
        list.add(new BetMode("大边"));
        list.add(new BetMode("小边"));
        list.add(new BetMode("单边"));
        list.add(new BetMode("双边"));
        list.add(new BetMode("0尾"));
        list.add(new BetMode("1尾"));
        list.add(new BetMode("2尾"));
        list.add(new BetMode("3尾"));
        list.add(new BetMode("4尾"));
        list.add(new BetMode("5尾"));
        list.add(new BetMode("6尾"));
        list.add(new BetMode("7尾"));
        list.add(new BetMode("8尾"));
        list.add(new BetMode("9尾"));
        list.add(new BetMode("小尾"));
        list.add(new BetMode("大尾"));
        list.add(new BetMode("3余0"));
        list.add(new BetMode("3余1"));
        list.add(new BetMode("3余2"));
        list.add(new BetMode("4余0"));
        list.add(new BetMode("4余1"));
        list.add(new BetMode("4余2"));
        list.add(new BetMode("4余3"));
        list.add(new BetMode("5余0"));
        list.add(new BetMode("5余1"));
        list.add(new BetMode("5余2"));
        list.add(new BetMode("5余3"));
        list.add(new BetMode("5余4"));
        list.add(new BetMode("清除"));
    }
    /***
     * 获得头部tab的所有项
     */
    public static List<BetMode> getList() {
        return list;
    }
}
