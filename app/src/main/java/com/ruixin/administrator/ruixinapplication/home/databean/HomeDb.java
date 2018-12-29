package com.ruixin.administrator.ruixinapplication.home.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/27.
 * 邮箱：543815830@qq.com
 * 首页的属性
 */

public class HomeDb {
    /**
     * status : 1
     * data : {"img":[{"imgname":"/uppic/banner/20180315095715.jpg","imgsrc":"/User/Reg"},{"imgname":"/uppic/banner/20180315095541.jpg","imgsrc":"/Hd/Qiangka"},{"imgname":"/uppic/banner/20180315095201.jpg","imgsrc":"/Hd/List"}],"gamelist":[{"tilte":"破晓奇兵","img":"/uppic/game/20180327161610.png"},{"tilte":"破晓奇兵","img":"/uppic/game/20180327161619.png"},{"tilte":"破晓奇兵","img":"/uppic/game/20180327161626.png"},{"tilte":"破晓奇兵","img":"/uppic/game/20180327161631.png"},{"tilte":"破晓奇兵","img":"/uppic/game/20180327161635.png"},{"tilte":"破晓奇兵","img":"/uppic/game/20180327161707.png"}],"hdlist":[{"id":"55","title":"bhvg","image":"/uppic/activitiespic/20170222083220.jpg","image2":"20170222083235.jpg"},{"id":"54","title":"每日亏损均返利","image":"/uppic/activitiespic/20170222083034.jpg","image2":"uppic/activitiespic/20170220200036.jpg"},{"id":"53","title":"福运28实力网站火爆上线","image":"/uppic/activitiespic/20170323200520.jpg","image2":"uppic/activitiespic/20170222083336.jpg"}],"news":[{"id":"66","title":"房的范德萨发顺丰"},{"id":"65","title":"测试公告2"},{"id":"64","title":"测试公告"}],"hotprize":[{"name":"10000充值卡","id":"178","points":"100000","imgsrc":"/uppic/prize/20180327162931.png"},{"name":"100原本妈","id":"177","points":"1000000","imgsrc":"/uppic/prize/20180327162931.png"},{"name":"移动手机充值卡50000","id":"174","points":"50000","imgsrc":"/uppic/prize/20180327162944.png"},{"name":"移动手机充值卡100000","id":"175","points":"100000","imgsrc":"/uppic/prize/20180327162939.png"},{"name":"www1211","id":"179","points":"100000","imgsrc":"/uppic/prize/20180327162939.png"}]}
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
        private List<ImgBean> img;
        private List<GamelistBean> gamelist;
        private List<HdlistBean> hdlist;
        private List<NewsBean> news;
        private List<HotprizeBean> hotprize;

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public List<GamelistBean> getGamelist() {
            return gamelist;
        }

        public void setGamelist(List<GamelistBean> gamelist) {
            this.gamelist = gamelist;
        }

        public List<HdlistBean> getHdlist() {
            return hdlist;
        }

        public void setHdlist(List<HdlistBean> hdlist) {
            this.hdlist = hdlist;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public List<HotprizeBean> getHotprize() {
            return hotprize;
        }

        public void setHotprize(List<HotprizeBean> hotprize) {
            this.hotprize = hotprize;
        }

        public static class ImgBean {
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

        public static class GamelistBean {
            /**
             * tilte : 破晓奇兵
             * img : /uppic/game/20180327161610.png
             */

            private String tilte;
            private String img;

            public String getTilte() {
                return tilte;
            }

            public void setTilte(String tilte) {
                this.tilte = tilte;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class HdlistBean {
            /**
             * id : 55
             * title : bhvg
             * image : /uppic/activitiespic/20170222083220.jpg
             * image2 : 20170222083235.jpg
             */

            private String id;
            private String title;
            private String image;
            private String image2;

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

        public static class NewsBean {
            /**
             * id : 66
             * title : 房的范德萨发顺丰
             */

            private String id;
            private String title;
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
            public String getTop() {
                return top;
            }

            public void setTop(String top) {
                this.top = top;
            }
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
            private String convertnum;

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
            public String getConvertnum() {
                return convertnum;
            }

            public void setConvertnum(String convertnum) {
                this.convertnum = convertnum;
            }
        }
    }

}
