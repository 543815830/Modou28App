package com.ruixin.administrator.ruixinapplication.home.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/27.
 * 邮箱：543815830@qq.com
 * 活动专场的属性
 */

public class HactDb {

    /**
     * status : 1
     * totalpage :
     * data : [{"id":"56","title":"福运28实力网站火爆上线","strtime":"2017-05-01","endtime":"2017-05-15","image":"uppic/activitiespic/20170323200520.gif","image2":"uppic/activitiespic/20170222083336.jpg"},{"id":"55","title":"bhvg","strtime":"0000-00-00","endtime":"0000-00-00","image":"20170222083220.jpg","image2":"20170222083235.jpg"},{"id":"54","title":"每日亏损均返利","strtime":"2016-06-01","endtime":"2016-06-01","image":"20170222083034.jpg","image2":"uppic/activitiespic/20170220200036.jpg"}]
     */
    private String firstid;
    public String getFirstid() {
        return firstid;
    }

    public void setFirstid(String firstid) {
        this.firstid = firstid;
    }
private String totalpage;
    public String getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(String totalpage) {
        this.totalpage = totalpage;
    }
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 56
         * title : 福运28实力网站火爆上线
         * strtime : 2017-05-01
         * endtime : 2017-05-15
         * image : uppic/activitiespic/20170323200520.gif
         * image2 : uppic/activitiespic/20170222083336.jpg
         */

        private String id;
        private String top;
        private String title;
        private String strtime;
        private String endtime;
        private String image;
        private String image2;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStrtime() {
            return strtime;
        }

        public void setStrtime(String strtime) {
            this.strtime = strtime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }
    }
}
