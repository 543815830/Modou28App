package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/13.
 * 邮箱：543815830@qq.com
 */

public class StateDb {

    /**
     * status : 1
     * totalpage:
     * data : [{"id":"9287690","time":"2018-04-12 08:57:57","logtype":"8","log":"登录奖励","points":"100","experience":"0","usersid":"19146","yucoins":"1200"},{"id":"9287526","time":"2018-04-11 09:18:51","logtype":"8","log":"登录奖励","points":"100","experience":"0","usersid":"19146","yucoins":"1100"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;
    private String totalpage;
    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }
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
         * id : 9287690
         * time : 2018-04-12 08:57:57
         * logtype : 8
         * log : 登录奖励
         * points : 100
         * experience : 0
         * usersid : 19146
         * yucoins : 1200
         */

        private String id;
        private String time;
        private String logtype;
        private String log;
        private String points;
        private String experience;
        private String usersid;
        private String yucoins;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getLogtype() {
            return logtype;
        }

        public void setLogtype(String logtype) {
            this.logtype = logtype;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getUsersid() {
            return usersid;
        }

        public void setUsersid(String usersid) {
            this.usersid = usersid;
        }

        public String getYucoins() {
            return yucoins;
        }

        public void setYucoins(String yucoins) {
            this.yucoins = yucoins;
        }
    }
}
