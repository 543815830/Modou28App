package com.ruixin.administrator.ruixinapplication.home.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/4.
 * 邮箱：543815830@qq.com
 * banner属性
 */

public class BannerDb {


    /**
     * status : 1
     * data : {"banner":[{"imgname":"/uppic/banner/20180315095715.jpg","imgsrc":"/User/Reg"},{"imgname":"/uppic/banner/20180315095541.jpg","imgsrc":"/Hd/Qiangka"},{"imgname":"/uppic/banner/20180315095201.jpg","imgsrc":"/Hd/List"}]}
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
        private List<BannerBean> banner;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public static class BannerBean {
            /**
             * imgname : /uppic/banner/20180315095715.jpg
             * imgsrc : /User/Reg
             */

            private String imgname;
            private String imgsrc;

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
}
