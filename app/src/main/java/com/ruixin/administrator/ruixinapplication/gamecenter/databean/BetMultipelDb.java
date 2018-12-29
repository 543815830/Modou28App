package com.ruixin.administrator.ruixinapplication.gamecenter.databean;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class BetMultipelDb {
    private static final List<BetMultipel> list = new ArrayList<BetMultipel>();
    static{
        list.add(new BetMultipel("0.1"));
        list.add(new BetMultipel("0.5"));
        list.add(new BetMultipel("0.8"));
        list.add(new BetMultipel("1.2"));
        list.add(new BetMultipel("1.5"));
        list.add(new BetMultipel("2"));
        list.add(new BetMultipel("10"));
        list.add(new BetMultipel("5"));
        list.add(new BetMultipel("50"));
        list.add(new BetMultipel("1000"));
        list.add(new BetMultipel("10000"));
        list.add(new BetMultipel("反选"));
        list.add(new BetMultipel("清除"));
        list.add(new BetMultipel("梭哈"));
        list.add(new BetMultipel("上期投注"));
    }
    /***
     * 获得头部tab的所有项
     */
    public static List<BetMultipel> getList() {
        return list;
    }
}
