package com.ruixin.administrator.ruixinapplication.home.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/7/5.
 */

public class PrizeTypeDb {


    /**
     * status : 1
     * data : {"prizetype":[{"name":"电脑外设","id":"41"},{"name":"手机充值卡","id":"37"},{"name":"手机数码","id":"40"}]}
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
        private List<PrizetypeBean> prizetype;

        public List<PrizetypeBean> getPrizetype() {
            return prizetype;
        }

        public void setPrizetype(List<PrizetypeBean> prizetype) {
            this.prizetype = prizetype;
        }

        public static class PrizetypeBean {
            /**
             * name : 电脑外设
             * id : 41
             */

            private String name;
            private String id;

            public PrizetypeBean(String name, String id, boolean isFlag) {
                this.name = name;
                this.id = id;
                this.isFlag = isFlag;
            }

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
            private boolean isFlag;

            public boolean isFlag() {
                return isFlag;
            }

            public void setFlag(boolean flag) {
                isFlag = flag;
            }
        }
    }
}
