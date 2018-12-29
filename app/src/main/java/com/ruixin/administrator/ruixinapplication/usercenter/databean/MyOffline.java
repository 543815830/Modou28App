package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/18.
 * 邮箱：543815830@qq.com
 * 我的下线的属性
 */

public class MyOffline {

    /**
     * status : 1
     * msg :
     * data : [{"id":"19134","level":"1","regtime":"2017-04-12","logintime":"2017-07-20"},{"id":"19118","level":"0","regtime":"2017-03-19","logintime":"2017-04-09"}]
     */

    private int status;
    private String totalpage;
    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 19134
         * level : 1
         * regtime : 2017-04-12
         * logintime : 2017-07-20
         */

        private String id;
        private String level;
        private String regtime;
        private String logintime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getRegtime() {
            return regtime;
        }

        public void setRegtime(String regtime) {
            this.regtime = regtime;
        }

        public String getLogintime() {
            return logintime;
        }

        public void setLogintime(String logintime) {
            this.logintime = logintime;
        }
    }
}
