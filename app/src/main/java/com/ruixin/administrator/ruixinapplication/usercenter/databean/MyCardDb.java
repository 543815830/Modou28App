package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 18-11-27.
 */

public class MyCardDb {

    /**
     * status : 1
     * data : [{"id":"61","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_43784.jpg","realname":"李丽"},{"id":"60","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_48877.jpg","realname":"李丽"},{"id":"59","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_89999.jpg","realname":"李丽"},{"id":"58","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_83534.jpg","realname":"李丽"},{"id":"57","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_11537.jpg","realname":"李丽"},{"id":"56","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_61754.jpg","realname":"李丽"}]
     * count : 2
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
         * id : 61
         * uid : 19177
         * typename : 支付宝
         * cardname : images/payment/19177/19177_43784.jpg
         * realname : 李丽
         */

        private String id;
        private String uid;
        private String typename;
        private String cardname;
        private String realname;

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

        public String getTypename() {
            return typename;
        }

        public void setTypename(String typename) {
            this.typename = typename;
        }

        public String getCardname() {
            return cardname;
        }

        public void setCardname(String cardname) {
            this.cardname = cardname;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }
    }
}
