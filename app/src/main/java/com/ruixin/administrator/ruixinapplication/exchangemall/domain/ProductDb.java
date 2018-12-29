package com.ruixin.administrator.ruixinapplication.exchangemall.domain;

/**
 * Created by 李丽 on 2018/7/3.
 */

public class ProductDb {

    /**
     * status : 1
     * data : {"prizename":"www1212","prizeid":"179","prizehits":"3859","imgsrc":"20180409115142.png","vipprice":2,"convertnum":"13958848"}
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
         * prizename : www1212
         * prizeid : 179
         * prizehits : 3859
         * imgsrc : 20180409115142.png
         * vipprice : 2
         * convertnum : 13958848
         */

        private String prizename;
        private String prizeid;
        private String shoptype;
        private int amouts;
        private String prizehits;
        private String imgsrc;
        private int vipprice;
        private String convertnum;

        public String getPrizename() {
            return prizename;
        }

        public void setPrizename(String prizename) {
            this.prizename = prizename;
        }
  public String getShoptype() {
            return shoptype;
        }

        public void setShoptype(String shoptype) {
            this.shoptype = shoptype;
        }


        public String getPrizeid() {
            return prizeid;
        }

        public void setPrizeid(String prizeid) {
            this.prizeid = prizeid;
        }
    public int getAmouts() {
            return amouts;
        }

        public void setAmouts(int amouts) {
            this.amouts = amouts;
        }

        public String getPrizehits() {
            return prizehits;
        }

        public void setPrizehits(String prizehits) {
            this.prizehits = prizehits;
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

        public String getConvertnum() {
            return convertnum;
        }

        public void setConvertnum(String convertnum) {
            this.convertnum = convertnum;
        }
    }
}
