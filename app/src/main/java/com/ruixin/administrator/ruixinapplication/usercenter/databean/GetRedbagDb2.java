package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/9/18.
 */

public class GetRedbagDb2 {

    /**
     * status : 3
     * msg : 已经领过该红包了！
     * data : {"packInfo":{"senderName":"魔图","points":"2888"},"luckmanID":null,"list":[{"name":"魔图","points":"1009","time":"2018-09-18 11:40:55","getuid":"19171"}]}
     */

    private int status;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * packInfo : {"senderName":"魔图","points":"2888"}
         * luckmanID : null
         * list : [{"name":"魔图","points":"1009","time":"2018-09-18 11:40:55","getuid":"19171"}]
         */

        private PackInfoBean packInfo;
        private Object luckmanID;
        private List<ListBean> list;

        public PackInfoBean getPackInfo() {
            return packInfo;
        }

        public void setPackInfo(PackInfoBean packInfo) {
            this.packInfo = packInfo;
        }

        public Object getLuckmanID() {
            return luckmanID;
        }

        public void setLuckmanID(Object luckmanID) {
            this.luckmanID = luckmanID;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PackInfoBean {
            /**
             * senderName : 魔图
             * points : 2888
             */

            private String senderName;
            private String points;
            private String head;

            public String getSenderName() {
                return senderName;
            }

            public void setSenderName(String senderName) {
                this.senderName = senderName;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            } public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }
        }

        public static class ListBean {
            /**
             * name : 魔图
             * points : 1009
             * time : 2018-09-18 11:40:55
             * getuid : 19171
             */

            private String name;
            private String points;
            private String time;
            private String getuid;
            private String head;
            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getGetuid() {
                return getuid;
            }

            public void setGetuid(String getuid) {
                this.getuid = getuid;
            }
        }
    }
}
