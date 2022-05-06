package com.example.readnews.ui.news;

import java.io.Serializable;

public class News implements Serializable {
    private String title;
    private String desc;
    private int photoId;

    /**
     * Constructs a new instance of {@code Object}.
     */
    public News(String title, String desc, int photoId) {
        this.title=title;
        this.desc=desc;
        this.photoId=photoId;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getDesc() {
        return desc;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getTitle() {
        return title;
    }
}
