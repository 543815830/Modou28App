package com.ruixin.administrator.ruixinapplication.usercenter.databean;

/**
 * 作者：Created by ${李丽} on 2018/4/11.
 * 邮箱：543815830@qq.com
 * 用户信息属性
 */

public class User {

    /**
     * status : 1
     * msg : 登录成功
     * data : {"id":"19146","name":"QQ123","points":"1100","back":"21475122","experience":"21722","maxexperience":"500","time":"2018-04-11 09:18:50","sex":"M","head":"images/head/52.jpg","qq":null,"birthday":null,"sjNum":"+8613535053","alipay":null,"cardtime":null,"udapoints":"0","udaexperience":"0","ldcardtime":null}
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
         * id : 19146
         * name : QQ123
         * points : 1100
         * back : 21475122
         * experience : 21722
         * maxexperience : 500
         * login_sj : 0
         * time : 2018-04-11 09:18:50
         * sex : M
         * head : images/head/52.jpg
         * qq : null
         * birthday : null
         * sjNum : +8613535053
         * alipay : null
         * cardtime : null
         * udapoints : 0
         * udaexperience : 0
         * ldcardtime : null
         */

        private String id;
        private String name;
        private String points;
        private String usertoken;
        private int tokentime;
        private String back;
        private String experience;
        private String eggjf;
        private String maxexperience;
        private String login_sj;
        private String qc_allow;
        private String qc_close;
        private String secques;
        private String gongzi;
        private String emailyz;
        private String is_bdCard;
        private int msgnum;
        private int notenum;
        private String time;
        private String email;
        private String sex;
        private String head;
        private Object qq;
        private Object birthday;
        private String sjNum;
        private String lxqd;
        private Object alipay;
        private Object cardtime;
        private String udapoints;
        private String udaexperience;
        private Object ldcardtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getUsertoken() {
            return usertoken;
        }

        public void setUsertoken(String usertoken) {
            this.usertoken = usertoken;
        }
  public int getTokentime() {
            return tokentime;
        }

        public void setTokentime(int tokentime) {
            this.tokentime = tokentime;
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

        public String getBack() {
            return back;
        }

        public void setBack(String back) {
            this.back = back;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }
        public void setLogin_sj(String login_sj) {
            this.login_sj = login_sj;
        }  public void setQc_allow(String qc_allow) {
            this.qc_allow = qc_allow;
        }  public void setQc_close(String qc_close) {
            this.qc_close = qc_close;
        }
        public String getEggjf() {
            return eggjf;
        }

        public void setEggjf(String eggjf) {
            this.eggjf = eggjf;
        }

        public String getMaxexperience() {
            return maxexperience;
        }
        public String getLogin_sj() {
            return login_sj;
        } public String getQc_allow() {
            return qc_allow;
        } public String getQc_close() {
            return qc_close;
        }
        public String getSecques() {
            return secques;
        }
        public void setSecques(String secques) {
            this.secques = secques;
        }
        public String getGongzi() {
            return gongzi;
        }
        public String getEmailyz() {
            return emailyz;
        }
        public void setGongzi(String gongzi) {
            this.gongzi = gongzi;
        }
        public void setEmailyz(String emailyz) {
            this.emailyz = emailyz;
        }
        public String getIs_bdCard() {
            return is_bdCard;
        }
        public void setIs_bdCard(String is_bdCard) {
            this.is_bdCard = is_bdCard;
        }
        public int getMsgnum() {
            return msgnum;
        }
        public void setMsgnum(int msgnum) {
            this.msgnum = msgnum;
        }
        public int getNotenum() {
            return notenum;
        }
        public void setNotenum(int notenum) {
            this.notenum = notenum;
        }
        public void setMaxexperience(String maxexperience) {
            this.maxexperience = maxexperience;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public Object getQq() {
            return qq;
        }

        public void setQq(Object qq) {
            this.qq = qq;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public String getSjNum() {
            return sjNum;
        }

        public void setSjNum(String sjNum) {
            this.sjNum = sjNum;
        }
        public void setLxqd(String lxqd) {
            this.lxqd = lxqd;
        }
        public String getLxqd() {
            return lxqd;
        }

        public Object getAlipay() {
            return alipay;
        }

        public void setAlipay(Object alipay) {
            this.alipay = alipay;
        }

        public Object getCardtime() {
            return cardtime;
        }

        public void setCardtime(Object cardtime) {
            this.cardtime = cardtime;
        }

        public String getUdapoints() {
            return udapoints;
        }

        public void setUdapoints(String udapoints) {
            this.udapoints = udapoints;
        }

        public String getUdaexperience() {
            return udaexperience;
        }

        public void setUdaexperience(String udaexperience) {
            this.udaexperience = udaexperience;
        }

        public Object getLdcardtime() {
            return ldcardtime;
        }

        public void setLdcardtime(Object ldcardtime) {
            this.ldcardtime = ldcardtime;
        }
    }
}
