package com.ruixin.administrator.ruixinapplication.home.databean;

import com.ruixin.administrator.ruixinapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/21.
 * 邮箱：543815830@qq.com
 * 合作伙伴的数据
 */

public class PartnerDb {
    private static final List<Partner> list = new ArrayList<Partner>();
    static{
        list.add(new Partner(R.drawable.partner1));
        list.add(new Partner(R.drawable.partner2));
        list.add(new Partner(R.drawable.partner3));
        list.add(new Partner(R.drawable.partner4));
        list.add(new Partner(R.drawable.partner5));
        list.add(new Partner(R.drawable.partner6));
        list.add(new Partner(R.drawable.partner7));
        list.add(new Partner(R.drawable.partner8));
    }
    /***
     * 获得头部tab的所有项
     */
    public static List<Partner> getList() {
        return list;
    }
}
