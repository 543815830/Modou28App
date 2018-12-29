package com.ruixin.administrator.ruixinapplication.usercenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/11/26.
 */

public class GoldDepositDb {


    /**
     * status : 1
     * data : {"lssxf":{"state":"1","detail":{"lsbs":0,"sumls":1600,"sumtx":0,"lssxbl":2,"yxls":1600,"lsts":5}},"cssxf":{"state":"1","detail":{"type":1,"sumtimes":1,"fee":"1000.00","feetype":1}},"time":{"start":"1","end":"24"},"jbbl":"1000","cardlist":[{"id":"61","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_43784.jpg","realname":"李丽"},{"id":"60","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_48877.jpg","realname":"李丽"},{"id":"59","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_89999.jpg","realname":"李丽"},{"id":"58","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_83534.jpg","realname":"李丽"},{"id":"57","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_11537.jpg","realname":"李丽"},{"id":"56","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_61754.jpg","realname":"李丽"},{"id":"55","uid":"19177","typename":"中国工商银行","cardname":"18328501242","realname":"李丽"},{"id":"54","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_97068.jpg","realname":"李丽"},{"id":"53","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_97495.png","realname":"沃德发"},{"id":"52","uid":"19177","typename":"中国建设银行","cardname":"6217003810001604100","realname":"李丽"},{"id":"48","uid":"19177","typename":"微信支付","cardname":"images/payment/19177/88484.png","realname":"沃德发"},{"id":"45","uid":"19177","typename":"中国建设银行","cardname":"622222222222","realname":"沃德发"}],"banklist":[{"id":"1","name":"中国建设银行"},{"id":"2","name":"中国工商银行"},{"id":"3","name":"支付宝"},{"id":"4","name":"微信支付"},{"id":"5","name":"财付通"},{"id":"6","name":"中国农业银行"},{"id":"7","name":"汇丰银行"},{"id":"8","name":"中国银行"}],"points":"31329.84"}
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
        /**
         * lssxf : {"state":"1","detail":{"lsbs":0,"sumls":1600,"sumtx":0,"lssxbl":2,"yxls":1600,"lsts":5}}
         * cssxf : {"state":"1","detail":{"type":1,"sumtimes":1,"fee":"1000.00","feetype":1}}
         * time : {"start":"1","end":"24"}
         * jbbl : 1000
         * cardlist : [{"id":"61","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_43784.jpg","realname":"李丽"},{"id":"60","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_48877.jpg","realname":"李丽"},{"id":"59","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_89999.jpg","realname":"李丽"},{"id":"58","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_83534.jpg","realname":"李丽"},{"id":"57","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_11537.jpg","realname":"李丽"},{"id":"56","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_61754.jpg","realname":"李丽"},{"id":"55","uid":"19177","typename":"中国工商银行","cardname":"18328501242","realname":"李丽"},{"id":"54","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_97068.jpg","realname":"李丽"},{"id":"53","uid":"19177","typename":"支付宝","cardname":"images/payment/19177/19177_97495.png","realname":"沃德发"},{"id":"52","uid":"19177","typename":"中国建设银行","cardname":"6217003810001604100","realname":"李丽"},{"id":"48","uid":"19177","typename":"微信支付","cardname":"images/payment/19177/88484.png","realname":"沃德发"},{"id":"45","uid":"19177","typename":"中国建设银行","cardname":"622222222222","realname":"沃德发"}]
         * banklist : [{"id":"1","name":"中国建设银行"},{"id":"2","name":"中国工商银行"},{"id":"3","name":"支付宝"},{"id":"4","name":"微信支付"},{"id":"5","name":"财付通"},{"id":"6","name":"中国农业银行"},{"id":"7","name":"汇丰银行"},{"id":"8","name":"中国银行"}]
         * points : 31329.84
         */

        private LssxfBean lssxf;
        private CssxfBean cssxf;
        private TimeBean time;
        private String jbbl;
        private String points;
        private List<CardlistBean> cardlist;
        private List<BanklistBean> banklist;

        public LssxfBean getLssxf() {
            return lssxf;
        }

        public void setLssxf(LssxfBean lssxf) {
            this.lssxf = lssxf;
        }

        public CssxfBean getCssxf() {
            return cssxf;
        }

        public void setCssxf(CssxfBean cssxf) {
            this.cssxf = cssxf;
        }

        public TimeBean getTime() {
            return time;
        }

        public void setTime(TimeBean time) {
            this.time = time;
        }

        public String getJbbl() {
            return jbbl;
        }

        public void setJbbl(String jbbl) {
            this.jbbl = jbbl;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public List<CardlistBean> getCardlist() {
            return cardlist;
        }

        public void setCardlist(List<CardlistBean> cardlist) {
            this.cardlist = cardlist;
        }

        public List<BanklistBean> getBanklist() {
            return banklist;
        }

        public void setBanklist(List<BanklistBean> banklist) {
            this.banklist = banklist;
        }

        public static class LssxfBean {
            /**
             * state : 1
             * detail : {"lsbs":0,"sumls":1600,"sumtx":0,"lssxbl":2,"yxls":1600,"lsts":5}
             */

            private String state;
            private DetailBean detail;

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public DetailBean getDetail() {
                return detail;
            }

            public void setDetail(DetailBean detail) {
                this.detail = detail;
            }

            public static class DetailBean {
                /**
                 * lsbs : 0
                 * sumls : 1600
                 * sumtx : 0
                 * lssxbl : 2
                 * yxls : 1600
                 * lsts : 5
                 */

                private int lsbs;
                private int sumls;
                private int sumtx;
                private int lssxbl;
                private int yxls;
                private int lsts;

                public int getLsbs() {
                    return lsbs;
                }

                public void setLsbs(int lsbs) {
                    this.lsbs = lsbs;
                }

                public int getSumls() {
                    return sumls;
                }

                public void setSumls(int sumls) {
                    this.sumls = sumls;
                }

                public int getSumtx() {
                    return sumtx;
                }

                public void setSumtx(int sumtx) {
                    this.sumtx = sumtx;
                }

                public int getLssxbl() {
                    return lssxbl;
                }

                public void setLssxbl(int lssxbl) {
                    this.lssxbl = lssxbl;
                }

                public int getYxls() {
                    return yxls;
                }

                public void setYxls(int yxls) {
                    this.yxls = yxls;
                }

                public int getLsts() {
                    return lsts;
                }

                public void setLsts(int lsts) {
                    this.lsts = lsts;
                }
            }
        }

        public static class CssxfBean {
            /**
             * state : 1
             * detail : {"type":1,"sumtimes":1,"fee":"1000.00","feetype":1}
             */

            private String state;
            private DetailBeanX detail;

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public DetailBeanX getDetail() {
                return detail;
            }

            public void setDetail(DetailBeanX detail) {
                this.detail = detail;
            }

            public static class DetailBeanX {
                /**
                 * type : 1
                 * sumtimes : 1
                 * fee : 1000.00
                 * feetype : 1
                 */

                private int type;
                private int sumtimes;
                private String fee;
                private int feetype;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getSumtimes() {
                    return sumtimes;
                }

                public void setSumtimes(int sumtimes) {
                    this.sumtimes = sumtimes;
                }

                public String getFee() {
                    return fee;
                }

                public void setFee(String fee) {
                    this.fee = fee;
                }

                public int getFeetype() {
                    return feetype;
                }

                public void setFeetype(int feetype) {
                    this.feetype = feetype;
                }
            }
        }

        public static class TimeBean {
            /**
             * start : 1
             * end : 24
             */

            private String start;
            private String end;

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }
        }

        public static class CardlistBean {
            /**
             * id : 61
             * uid : 19177
             * typename : 支付宝
             * cardname : images/payment/19177/19177_43784.jpg
             * realname : 李丽
             */

            private String id;
            private String uid;
            private String typename;
            private String cardname;
            private String realname;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }

            public String getCardname() {
                return cardname;
            }

            public void setCardname(String cardname) {
                this.cardname = cardname;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }
            private boolean isFlag;

            public boolean isFlag() {
                return isFlag;
            }

            public void setFlag(boolean flag) {
                isFlag = flag;
            }
        }

        public static class BanklistBean {
            /**
             * id : 1
             * name : 中国建设银行
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
