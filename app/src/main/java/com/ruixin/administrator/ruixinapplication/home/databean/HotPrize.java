package com.ruixin.administrator.ruixinapplication.home.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/21.
 * 邮箱：543815830@qq.com
 * 热门奖品的属性
 */

public class HotPrize {

    /**
     * status : 1
     * data : {"hotprize":[{"name":"10000充值卡","id":"178","points":"100000","imgsrc":"/uppic/prize/20180327162931.png"},{"name":"100原本妈","id":"177","points":"1000000","imgsrc":"/uppic/prize/20180327162931.png"},{"name":"移动手机充值卡50000","id":"174","points":"50000","imgsrc":"/uppic/prize/20180327162944.png"},{"name":"移动手机充值卡100000","id":"175","points":"100000","imgsrc":"/uppic/prize/20180327162939.png"},{"name":"www1211","id":"179","points":"100000","imgsrc":"/uppic/prize/20180327162939.png"}]}
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
        private List<HotprizeBean> hotprize;
        private List<HotprizeBean> newprize;

        public List<HotprizeBean> getHotprize() {
            return hotprize;
        }
        public List<HotprizeBean> getNewprize() {
            return newprize;
        }

        public void setHotprize(List<HotprizeBean> hotprize) {
            this.hotprize = hotprize;
        }
        public void setNewprize(List<HotprizeBean> newprize) {
            this.newprize = newprize;
        }

        public static class HotprizeBean {
            /**
             * name : 10000充值卡
             * id : 178
             * points : 100000
             * imgsrc : /uppic/prize/20180327162931.png
             */

            private String name;
            private String id;
            private String points;
            private String imgsrc;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
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
