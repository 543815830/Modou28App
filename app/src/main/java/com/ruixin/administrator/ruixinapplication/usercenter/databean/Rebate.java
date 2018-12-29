package com.ruixin.administrator.ruixinapplication.usercenter.databean;

/**
 * 作者：Created by ${李丽} on 2018/4/19.
 * 邮箱：543815830@qq.com
 */

public class Rebate {

    /**
     * status : 1
     * data : {"scflbl":"0.5000","flpoints":null,"points":null,"iscz":0,"isgeted":null}
     */

    private int status;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * scflbl : 0.5000
         * flpoints : null
         * points : null
         * iscz : 0
         * isgeted : null
         */

        private String scflbl;
        private String flpoints;
        private String points;
        private int iscz;
        private String isgeted;

        public String getScflbl() {
            return scflbl;
        }

        public void setScflbl(String scflbl) {
            this.scflbl = scflbl;
        }

        public String getFlpoints() {
            return flpoints;
        }

        public void setFlpoints(String flpoints) {
            this.flpoints = flpoints;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public int getIscz() {
            return iscz;
        }

        public void setIscz(int iscz) {
            this.iscz = iscz;
        }

        public String getIsgeted() {
            return isgeted;
        }

        public void setIsgeted(String isgeted) {
            this.isgeted = isgeted;
        }
    }
}
