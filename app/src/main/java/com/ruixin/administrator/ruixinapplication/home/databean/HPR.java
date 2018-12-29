package com.ruixin.administrator.ruixinapplication.home.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/23.
 * 邮箱：543815830@qq.com
 * 新闻公告的属性
 */

public class HPR {
    /**
     * status : 1
     * totalpage :
     * data : [{"id":"66","title":"房的范德萨发顺丰","content":"房的范德萨发顺丰房的范德萨发顺丰房的范德萨发顺丰房的范德萨发顺丰房的范德萨发顺丰","time":"2017-08-01","top":"0"},{"id":"65","title":"测试公告2","content":"测试公告2测试公告2测试公告2测试公告2","time":"2017-03-18","top":"0"},{"id":"64","title":"测试公告","content":"测试公告测试公告测试公告测试公告测试公告","time":"2017-03-18","top":"0"}]
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
         * id : 66
         * title : 房的范德萨发顺丰
         * content : 房的范德萨发顺丰房的范德萨发顺丰房的范德萨发顺丰房的范德萨发顺丰房的范德萨发顺丰
         * time : 2017-08-01
         * top : 0
         */

        private String id;
        private String title;
        private String content;
        private String time;
        private String top;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }
    }
}
