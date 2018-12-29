package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/26.
 * 邮箱：543815830@qq.com
 * 域名的数据
 */

public class Domain {

    /**
     * status : 1
     * data : {"yml":["modou28.com","ys2889.com","16828game.com","fuyun28.com","quwan288.com"],"web_zsxl":"11000"}
     */

    private int status;
    private String msg;
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
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
         * yml : ["modou28.com","ys2889.com","16828game.com","fuyun28.com","quwan288.com"]
         * web_zsxl : 11000
         */

        private String web_zsxl;
        private List<String> yml;

        public String getWeb_zsxl() {
            return web_zsxl;
        }

        public void setWeb_zsxl(String web_zsxl) {
            this.web_zsxl = web_zsxl;
        }

        public List<String> getYml() {
            return yml;
        }

        public void setYml(List<String> yml) {
            this.yml = yml;
        }
    }
}
