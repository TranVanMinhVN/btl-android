package com.mtv.news.entity;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private int categoryId;
    private String title;
    private boolean selected;

    public Category() {
    }
    public Category(int id, String title, boolean selected) {
        this.categoryId = id;
        this.title = title;
        this.selected = selected;
    }
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
