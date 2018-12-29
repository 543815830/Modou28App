package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

import java.util.List;

/**
 * Created by 李丽 on 2018/6/26.
 */

public class LastBetDb {

    /**
     * status : 1
     * moneyPoints : ["0","3","0","10","0","21","0","36","0","55","0","69","0","75","0","73","0","63","0","45","0","28","0","15","0","6","0","1"]
     */

    private int status;
    private List<String> moneyPoints;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getMoneyPoints() {
        return moneyPoints;
    }

    public void setMoneyPoints(List<String> moneyPoints) {
        this.moneyPoints = moneyPoints;
    }
}
