package com.ruixin.administrator.ruixinapplication.notice;

/**
 * Created by 李丽 on 2018/7/13.
 */

public class UnReadNoDb {

    /**
     * status : 1
     * data : {"notenum":"0"}
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
         * notenum : 0
         */

        private String notenum;

        public String getNotenum() {
            return notenum;
        }

        public void setNotenum(String notenum) {
            this.notenum = notenum;
        }
    }
}
