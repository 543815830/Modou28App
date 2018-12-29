package com.ruixin.administrator.ruixinapplication;

/**
 * Created by 李丽 on 2018/12/13.
 */

public class CheckUpDb {

    /**
     * status : 1
     * msg :
     * data : {"siteid":"101","sitename":"测试网","domain":"etest.ruixinyunke.com","version":"201812130001","download":"http://etest.ruixinyunke.com/app/android/201812130001.apk","feature":"1、修复若干问题\n2、改版会员中心的福利中心","available":"true","poptips":"这是一个模拟的通知"}
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
         * siteid : 101
         * sitename : 测试网
         * domain : etest.ruixinyunke.com
         * version : 201812130001
         * download : http://etest.ruixinyunke.com/app/android/201812130001.apk
         * feature : 1、修复若干问题
         2、改版会员中心的福利中心
         * available : true
         * poptips : 这是一个模拟的通知
         */

        private String siteid;
        private String sitename;
        private String domain;
        private String version;
        private String download;
        private String feature;
        private String available;
        private String poptips;

        public String getSiteid() {
            return siteid;
        }

        public void setSiteid(String siteid) {
            this.siteid = siteid;
        }

        public String getSitename() {
            return sitename;
        }

        public void setSitename(String sitename) {
            this.sitename = sitename;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public String getFeature() {
            return feature;
        }

        public void setFeature(String feature) {
            this.feature = feature;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public String getPoptips() {
            return poptips;
        }

        public void setPoptips(String poptips) {
            this.poptips = poptips;
        }
    }
}
