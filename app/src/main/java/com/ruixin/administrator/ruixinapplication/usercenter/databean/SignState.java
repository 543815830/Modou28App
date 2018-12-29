package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/20.
 * 邮箱：543815830@qq.com
 */

public class SignState {

    /**
     * status : 1
     * data : {"qdarr":[15,9],"lxqd":"0","ljqdhd":"0","qd":{"id":"1","v0js":"0","v1js":"20","v2js":"30","v3js":"60","v4js":"100","v5js":"150","v6js":"200","v0ts":"1","v1ts":"1","v2ts":"1","v3ts":"1","v4ts":"1","v5ts":"1","v6ts":"1","maxts":"7","date":"2018-04-16"},"todayqd":0}
     */

    private int status;
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
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
         * qdarr : [15,9]
         * lxqd : 0
         * ljqdhd : 0
         * qd : {"id":"1","v0js":"0","v1js":"20","v2js":"30","v3js":"60","v4js":"100","v5js":"150","v6js":"200","v0ts":"1","v1ts":"1","v2ts":"1","v3ts":"1","v4ts":"1","v5ts":"1","v6ts":"1","maxts":"7","date":"2018-04-16"}
         * todayqd : 0
         */

        private String lxqd;
        private String ljqdhd;
        private QdBean qd;
        private String todayqd;
        private List<String> qdarr;

        public String getLxqd() {
            return lxqd;
        }

        public void setLxqd(String lxqd) {
            this.lxqd = lxqd;
        }

        public String getLjqdhd() {
            return ljqdhd;
        }

        public void setLjqdhd(String ljqdhd) {
            this.ljqdhd = ljqdhd;
        }

        public QdBean getQd() {
            return qd;
        }

        public void setQd(QdBean qd) {
            this.qd = qd;
        }

        public String getTodayqd() {
            return todayqd;
        }

        public void setTodayqd(String todayqd) {
            this.todayqd = todayqd;
        }

        public List<String> getQdarr() {
            return qdarr;
        }

        public void setQdarr(List<String> qdarr) {
            this.qdarr = qdarr;
        }

        public static class QdBean {
            /**
             * id : 1
             * v0js : 0
             * v1js : 20
             * v2js : 30
             * v3js : 60
             * v4js : 100
             * v5js : 150
             * v6js : 200
             * v0ts : 1
             * v1ts : 1
             * v2ts : 1
             * v3ts : 1
             * v4ts : 1
             * v5ts : 1
             * v6ts : 1
             * maxts : 7
             * date : 2018-04-16
             */

            private String id;
            private String v0js;
            private String v1js;
            private String v2js;
            private String v3js;
            private String v4js;
            private String v5js;
            private String v6js;
            private String v0ts;
            private String v1ts;
            private String v2ts;
            private String v3ts;
            private String v4ts;
            private String v5ts;
            private String v6ts;
            private String maxts;
            private String date;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getV0js() {
                return v0js;
            }

            public void setV0js(String v0js) {
                this.v0js = v0js;
            }

            public String getV1js() {
                return v1js;
            }

            public void setV1js(String v1js) {
                this.v1js = v1js;
            }

            public String getV2js() {
                return v2js;
            }

            public void setV2js(String v2js) {
                this.v2js = v2js;
            }

            public String getV3js() {
                return v3js;
            }

            public void setV3js(String v3js) {
                this.v3js = v3js;
            }

            public String getV4js() {
                return v4js;
            }

            public void setV4js(String v4js) {
                this.v4js = v4js;
            }

            public String getV5js() {
                return v5js;
            }

            public void setV5js(String v5js) {
                this.v5js = v5js;
            }

            public String getV6js() {
                return v6js;
            }

            public void setV6js(String v6js) {
                this.v6js = v6js;
            }

            public String getV0ts() {
                return v0ts;
            }

            public void setV0ts(String v0ts) {
                this.v0ts = v0ts;
            }

            public String getV1ts() {
                return v1ts;
            }

            public void setV1ts(String v1ts) {
                this.v1ts = v1ts;
            }

            public String getV2ts() {
                return v2ts;
            }

            public void setV2ts(String v2ts) {
                this.v2ts = v2ts;
            }

            public String getV3ts() {
                return v3ts;
            }

            public void setV3ts(String v3ts) {
                this.v3ts = v3ts;
            }

            public String getV4ts() {
                return v4ts;
            }

            public void setV4ts(String v4ts) {
                this.v4ts = v4ts;
            }

            public String getV5ts() {
                return v5ts;
            }

            public void setV5ts(String v5ts) {
                this.v5ts = v5ts;
            }

            public String getV6ts() {
                return v6ts;
            }

            public void setV6ts(String v6ts) {
                this.v6ts = v6ts;
            }

            public String getMaxts() {
                return maxts;
            }

            public void setMaxts(String maxts) {
                this.maxts = maxts;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
