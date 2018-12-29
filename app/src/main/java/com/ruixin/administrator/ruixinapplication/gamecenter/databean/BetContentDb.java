package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/10/17.
 */

public class BetContentDb {

    /**
     * status : 1
     * data : [{"tzhm":1,"bzpl":10,"kjpl":"10","tzsl":0,"hdsl":0},{"tzhm":2,"bzpl":10,"kjpl":"10","tzsl":0,"hdsl":0},{"tzhm":3,"bzpl":10,"kjpl":"10","tzsl":0,"hdsl":0},{"tzhm":4,"bzpl":10,"kjpl":"10","tzsl":0,"hdsl":0},{"tzhm":5,"bzpl":10,"kjpl":"10","tzsl":0,"hdsl":0},{"tzhm":6,"bzpl":10,"kjpl":"10","tzsl":0,"hdsl":0},{"tzhm":7,"bzpl":10,"kjpl":"10","tzsl":0,"hdsl":0},{"tzhm":8,"bzpl":10,"kjpl":"10","tzsl":0,"hdsl":0},{"tzhm":9,"bzpl":10,"kjpl":"10","tzsl":0,"hdsl":0},{"tzhm":10,"bzpl":10,"kjpl":"10","tzsl":0,"hdsl":0}]
     */

    private int status;
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
         * tzhm : 1
         * bzpl : 10
         * kjpl : 10
         * tzsl : 0
         * hdsl : 0
         */

        private String tzhm;
        private double bzpl;
        private String kjpl;
        private int tzsl;
        private int hdsl;

        public String getTzhm() {
            return tzhm;
        }

        public void setTzhm(String tzhm) {
            this.tzhm = tzhm;
        }

        public double getBzpl() {
            return bzpl;
        }

        public void setBzpl(double bzpl) {
            this.bzpl = bzpl;
        }

        public String getKjpl() {
            return kjpl;
        }

        public void setKjpl(String kjpl) {
            this.kjpl = kjpl;
        }

        public int getTzsl() {
            return tzsl;
        }

        public void setTzsl(int tzsl) {
            this.tzsl = tzsl;
        }

        public int getHdsl() {
            return hdsl;
        }

        public void setHdsl(int hdsl) {
            this.hdsl = hdsl;
        }
    }
}
