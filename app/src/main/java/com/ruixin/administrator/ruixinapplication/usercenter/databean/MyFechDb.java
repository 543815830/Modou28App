package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 18-11-27.
 */

public class MyFechDb {

    /**
     * status : 1
     * data : [{"id":"91","uid":"19177","time":"2018-11-27 13:37:35","bank":"支付宝","card":"images/payment/19177/19177_44164.jpg","realname":"李丽","points":"50000","state":"0","rmb":"50"}]
     * count : 1
     */

    private int status;
    private int count;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 91
         * uid : 19177
         * time : 2018-11-27 13:37:35
         * bank : 支付宝
         * card : images/payment/19177/19177_44164.jpg
         * realname : 李丽
         * points : 50000
         * state : 0
         * rmb : 50
         */

        private String id;
        private String uid;
        private String time;
        private String bank;
        private String card;
        private String realname;
        private String points;
        private String state;
        private String rmb;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getRmb() {
            return rmb;
        }

        public void setRmb(String rmb) {
            this.rmb = rmb;
        }
    }
}
