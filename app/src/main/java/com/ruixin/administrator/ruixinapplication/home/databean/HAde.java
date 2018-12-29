package com.ruixin.administrator.ruixinapplication.home.databean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/19.
 *广告体验的属性
 */

public class HAde {

    /**
     * status : 1
     * msg :
     * data : [{"tilte":"破晓奇兵","img":"/uppic/game/20180327161610.png"},{"tilte":"破晓奇兵","img":"/uppic/game/20180327161619.png"},{"tilte":"破晓奇兵","img":"/uppic/game/20180327161626.png"},{"tilte":"破晓奇兵","img":"/uppic/game/20180327161631.png"},{"tilte":"破晓奇兵","img":"/uppic/game/20180327161635.png"},{"tilte":"破晓奇兵","img":"/uppic/game/20180327161707.png"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
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
}
