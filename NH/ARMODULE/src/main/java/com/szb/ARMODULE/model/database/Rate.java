package com.szb.ARMODULE.model.database;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cwh62 on 2017-05-11.
 */

public class Rate {
    private int rate;
    private String explain;
    private String regionname;

    public float getRate() { return this.rate;}
    public String getExplain() {return this.explain;}
    private String getRegionname() {return this.regionname;}

    public void setRate(int rate) {
        this.rate = rate;
    }
    public void setExplain(String explain) {this.explain = explain;}
    public void setRegionname(String regionname) {this.regionname = regionname;}
}
