package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/7.
 */

public class MybetDetailDb {

    /**
     * status : 1
     * data : {"no":"624502","kjtime":"2018-06-06 16:49:59","points":"500","hdpoints":"0","result":["5","7","7","19"],"zjpl":"1000","ykbl":"0.00","tznr":"0:1,1:3,2:6,3:10,4:15,10:63,11:69,12:73,13:75,14:75,20:36,21:28,22:21,23:15,24:10"}
     */

    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * no : 624502
         * kjtime : 2018-06-06 16:49:59
         * points : 500
         * hdpoints : 0
         * result : ["5","7","7","19"]
         * zjpl : 1000
         * ykbl : 0.00
         * tznr : 0:1,1:3,2:6,3:10,4:15,10:63,11:69,12:73,13:75,14:75,20:36,21:28,22:21,23:15,24:10
         */

        private String no;
        private String kjtime;
        private String points;
        private String hdpoints;
        private String zjpl;
        private String ykbl;
        private String tznr;
        private List<String> result;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getKjtime() {
            return kjtime;
        }

        public void setKjtime(String kjtime) {
            this.kjtime = kjtime;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getHdpoints() {
            return hdpoints;
        }

        public void setHdpoints(String hdpoints) {
            this.hdpoints = hdpoints;
        }

        public String getZjpl() {
            return zjpl;
        }

        public void setZjpl(String zjpl) {
            this.zjpl = zjpl;
        }

        public String getYkbl() {
            return ykbl;
        }

        public void setYkbl(String ykbl) {
            this.ykbl = ykbl;
        }

        public String getTznr() {
            return tznr;
        }

        public void setTznr(String tznr) {
            this.tznr = tznr;
        }

        public List<String> getResult() {
            return result;
        }

        public void setResult(List<String> result) {
            this.result = result;
        }
    }
}
