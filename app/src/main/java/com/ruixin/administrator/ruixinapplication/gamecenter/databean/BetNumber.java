package com.ruixin.administrator.ruixinapplication.gamecenter.databean;

/**
 * Created by 李丽 on 2018/11/20.
 */

public class BetNumber {
    int position;
    String id;

    public BetNumber(int position, String id) {
        this.position = position;
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
