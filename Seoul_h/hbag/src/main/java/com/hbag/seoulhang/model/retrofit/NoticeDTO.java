package com.hbag.seoulhang.model.retrofit;

import java.util.Date;

/**
 * Created by cwh62 on 2017-10-10.
 */

public class NoticeDTO {
    private String title;
    private String contents;
    private String date;


    public String getTitle() {
        return this.title;
    }

    public String getContents() {
        return this.contents;
    }

    public String getDate() {
        return this.date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
