package com.hbag.seoulhang.model.retrofit;

import java.util.Date;

/**
 * Created by cwh62 on 2017-10-10.
 */

public class NoticeDTO {
    private String title;
    private String contents;
    private String date1;
    private String date2;


    public String getTitle() {
        return this.title;
    }

    public String getContents() {
        return this.contents;
    }

    public String getDate1() {
        return this.date1;
    }

    public String getDate2() {return  this.date2;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    public void setDate1(String date) {
        this.date1 = date1;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }
}
