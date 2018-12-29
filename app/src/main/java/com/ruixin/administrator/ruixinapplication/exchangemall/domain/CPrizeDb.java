package com.ruixin.administrator.ruixinapplication.exchangemall.domain;

/**
 * Created by 李丽 on 2018/7/3.
 */

public class CPrizeDb {

    /**
     * status : 1
     * data : {"mfdjcs":"0","cssxf":{"type":1,"sumtimes":1,"fee":"1000.00","feetype":1},"cssxfopen":"1","lssxfopen":"1","lsts":"5","lsbs":1.6,"sumls":20782,"sumtx":0,"lssxbl":2,"yxls":20782,"prizename":"www1212","prizeid":"179","imgsrc":"20180409115142.png","vipprice":2}
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
         * mfdjcs : 0
         * cssxf : {"type":1,"sumtimes":1,"fee":"1000.00","feetype":1}
         * cssxfopen : 1
         * lssxfopen : 1
         * lsts : 5
         * lsbs : 1.6
         * sumls : 20782
         * sumtx : 0
         * lssxbl : 2
         * yxls : 20782
         * prizename : www1212
         * prizeid : 179
         * imgsrc : 20180409115142.png
         * vipprice : 2
         */

        private String mfdjcs;
        private CssxfBean cssxf;
        private String cssxfopen;
        private String lssxfopen;
        private String lsts;
        private double lsbs;
        private int sumls;
        private int sumtx;
        private int lssxbl;
        private int yxls;
        private int yz;
        private String prizename;
        private String prizeid;
        private String imgsrc;
        private int vipprice;
        private String shoptype;
        private int amouts;
        public String getMfdjcs() {
            return mfdjcs;
        }

        public void setMfdjcs(String mfdjcs) {
            this.mfdjcs = mfdjcs;
        }

        public CssxfBean getCssxf() {
            return cssxf;
        }
        public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }
        public int getAmouts() {
            return amouts;
        }

        public void setAmouts(int amouts) {
            this.amouts = amouts;
        }
        public void setCssxf(CssxfBean cssxf) {
            this.cssxf = cssxf;
        }

        public String getCssxfopen() {
            return cssxfopen;
        }

        public void setCssxfopen(String cssxfopen) {
            this.cssxfopen = cssxfopen;
        }

        public String getLssxfopen() {
            return lssxfopen;
        }

        public void setLssxfopen(String lssxfopen) {
            this.lssxfopen = lssxfopen;
        }

        public String getLsts() {
            return lsts;
        }

        public void setLsts(String lsts) {
            this.lsts = lsts;
        }

        public double getLsbs() {
            return lsbs;
        }

        public void setLsbs(double lsbs) {
            this.lsbs = lsbs;
        }

        public int getSumls() {
            return sumls;
        }

        public void setSumls(int sumls) {
            this.sumls = sumls;
        }

        public int getSumtx() {
            return sumtx;
        }

        public void setSumtx(int sumtx) {
            this.sumtx = sumtx;
        }

        public int getLssxbl() {
            return lssxbl;
        }

        public void setLssxbl(int lssxbl) {
            this.lssxbl = lssxbl;
        }

        public int getYxls() {
            return yxls;
        }
        public int getYz() {
            return yz;
        }

        public void setYxls(int yxls) {
            this.yxls = yxls;
        }
        public void setYz(int yz) {
            this.yz = yz;
        }

        public String getPrizename() {
            return prizename;
        }

        public void setPrizename(String prizename) {
            this.prizename = prizename;
        }

        public String getPrizeid() {
            return prizeid;
        }

        public void setPrizeid(String prizeid) {
            this.prizeid = prizeid;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public int getVipprice() {
            return vipprice;
        }

        public void setVipprice(int vipprice) {
            this.vipprice = vipprice;
        }

        public static class CssxfBean {
            /**
             * type : 1
             * sumtimes : 1
             * fee : 1000.00
             * feetype : 1
             */

            private int type;
            private int sumtimes;
            private int fee;
            private int feetype;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getSumtimes() {
                return sumtimes;
            }

            public void setSumtimes(int sumtimes) {
                this.sumtimes = sumtimes;
            }

            public int getFee() {
                return fee;
            }

            public void setFee(int fee) {
                this.fee = fee;
            }

            public int getFeetype() {
                return feetype;
            }

            public void setFeetype(int feetype) {
                this.feetype = feetype;
            }
        }
    }
}
