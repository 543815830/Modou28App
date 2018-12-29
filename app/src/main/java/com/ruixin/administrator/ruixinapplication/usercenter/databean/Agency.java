package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/4/24.
 * 邮箱：543815830@qq.com
 * 代理商的属性
 */

public class Agency {

    /**
     * status : 1
     * data : [{"name":"6666","qq":"2822266","xcy":""},{"name":"博易点卡","qq":"2646223355","xcy":"反倒是发送到发的地方第三方的 地方水电费第三方稍等\u203b"},{"name":"enrico","qq":"3289200860","xcy":""},{"name":"博易点卡","qq":"208232437","xcy":""},{"name":"小白点卡","qq":"208232437","xcy":""},{"name":"12233","qq":"123123","xcy":""},{"name":"张月华","qq":"1950000","xcy":""},{"name":"小七点卡","qq":"208232437","xcy":""}]
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 6666
         * qq : 2822266
         * xcy :
         */

        private String name;
        private String qq;
        private String xcy;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getXcy() {
            return xcy;
        }

        public void setXcy(String xcy) {
            this.xcy = xcy;
        }
    }
}
