package com.nguyentien.hai.ecomerce.model;

public class Cate {
    private int cateId;
    private String cateName;
    private String cateImage;

    public Cate(int cateId, String cateName, String cateImage) {
        this.cateId = cateId;
        this.cateName = cateName;
        this.cateImage = cateImage;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateImage() {
        return cateImage;
    }

    public void setCateImage(String cateImage) {
        this.cateImage = cateImage;
    }

    @Override
    public String toString() {
        return "Cate{" +
                "cateId=" + cateId +
                ", cateName='" + cateName + '\'' +
                ", cateImage='" + cateImage + '\'' +
                '}';
    }
}
