package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/18.
 * 邮箱：543815830@qq.com
 */

public class PromoteEarings {

    /**
     * status : 1
     * data : [{"id":"19136","type":"下线升级","regtime":"2017-04-29","time":"2017-08-26","points":"0"}]
     */

    private int status;
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    private String firstid;
    public String getFirstid() {
        return firstid;
    }

    public void setFirstid(String firstid) {
        this.firstid = firstid;
    }
    private String totalpage;
    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 19136
         * type : 下线升级
         * regtime : 2017-04-29
         * time : 2017-08-26
         * points : 0
         */

        private String id;
        private String tjlogid;
        private String type;
        private String regtime;
        private String time;
        private String points;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getTjlogid() {
            return tjlogid;
        }

        public void setTjlogid(String tjlogid) {
            this.tjlogid = tjlogid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRegtime() {
            return regtime;
        }

        public void setRegtime(String regtime) {
            this.regtime = regtime;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }
    }
}
