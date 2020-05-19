package com.mtv.news.entity;

public class New {

    private int newId;
    private  int authorId;
    private int categoryId;
    private String name;
    private String note;
    private String content;
    private String urlImg;
    private String urlImg2;
    private String urlImg3;
    private String time;
    private String urlVideo;

    public New(int newId, int authorId, int categoryId, String name, String note, String content, String urlImg, String urlImg2, String urlImg3, String time, String urlVideo) {
        this.newId = newId;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.name = name;
        this.note = note;
        this.content = content;
        this.urlImg = urlImg;
        this.urlImg2 = urlImg2;
        this.urlImg3 = urlImg3;
        this.time = time;
        this.urlVideo = urlVideo;
    }

    public New(int newId, int authorId, int categoryId, String name, String note, String content, String urlImg, String time, String urlVideo) {
        this.newId = newId;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.name = name;
        this.note = note;
        this.content = content;
        this.urlImg = urlImg;
        this.time = time;
        this.urlVideo = urlVideo;
    }

    public String getUrlVideo(){
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getUrlImg2() {
        return urlImg2;
    }

    public void setUrlImg2(String urlImg2) {
        this.urlImg2 = urlImg2;
    }

    public String getUrlImg3() {
        return urlImg3;
    }

    public void setUrlImg3(String urlImg3) {
        this.urlImg3 = urlImg3;
    }
}
