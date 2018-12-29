package com.ruixin.administrator.ruixinapplication.utils;

import java.util.List;

/**
 * Created by 李丽 on 2018/12/27.
 */

public class AdDb {


    /**
     * status : 1
     * list : [{"id":"38","imgname":"uppic/banner/20181227151638.jpg","imgsrc":"奥术大师"}]
     */

    private int status;
    private List<ListBean> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 38
         * imgname : uppic/banner/20181227151638.jpg
         * imgsrc : 奥术大师
         */

        private String id;
        private String imgname;
        private String imgsrc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImgname() {
            return imgname;
        }

        public void setImgname(String imgname) {
            this.imgname = imgname;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }
    }
}
