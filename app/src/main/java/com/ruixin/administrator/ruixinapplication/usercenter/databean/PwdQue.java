package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/23.
 * 邮箱：543815830@qq.com
 */

public class PwdQue {

    /**
     * status : 1
     * data : ["你父亲的姓名是什么?","你母亲的姓名是什么?","你的出生地?","你的宠物的名子?","你的职业是什么?","你配偶的职业是什么?"]
     */

    private int status;
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    private List<String> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
