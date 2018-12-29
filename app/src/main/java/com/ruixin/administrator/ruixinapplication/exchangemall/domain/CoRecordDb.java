package com.ruixin.administrator.ruixinapplication.exchangemall.domain;

import java.util.List;

/**
 * Created by 李丽 on 2018/7/4.
 */

public class CoRecordDb {

    /**
     * status : 1
     * data : [{"orderid":"8691","prizeid":"177","prizename":"100原本妈","num":"1","time":"2018-07-04 10:41:38","state":"0"},{"orderid":"8690","prizeid":"178","prizename":"10000充值卡","num":"1","time":"2018-07-04 10:35:22","state":"1"},{"orderid":"8644","prizeid":"178","prizename":"10000充值卡","num":"2","time":"2018-07-03 17:35:19","state":"1"}]
     */

    private int status;
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
         * orderid : 8691
         * prizeid : 177
         * prizename : 100原本妈
         * num : 1
         * time : 2018-07-04 10:41:38
         * state : 0
         */

        private String orderid;
        private String prizeid;
        private String prizename;
        private String num;
        private String time;
        private String state;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getPrizeid() {
            return prizeid;
        }

        public void setPrizeid(String prizeid) {
            this.prizeid = prizeid;
        }

        public String getPrizename() {
            return prizename;
        }

        public void setPrizename(String prizename) {
            this.prizename = prizename;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
