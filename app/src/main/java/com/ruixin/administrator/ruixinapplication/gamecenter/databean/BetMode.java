package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

/**
 * 作者：Created by ${李丽} on 2018/5/25.
 * 邮箱：543815830@qq.com
 */
public class BetMode {
    private String modename;

    public BetMode(String modename) {
        this.modename = modename;
    }

    public String getModename() {
        return modename;
    }

    public void setModename(String modename) {
        this.modename = modename;
    }
    private boolean isFlag;

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }
}
