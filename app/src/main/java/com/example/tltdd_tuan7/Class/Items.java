package com.example.tltdd_tuan7.Class;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class Items  implements Serializable {
    String ten,mota;
    int img;
    Bitmap bitmap;

    public Items(String ten, String mota, int img,Bitmap uri) {
        this.ten = ten;
        this.mota = mota;
        this.img = img;
        this.bitmap=uri;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setUri(Bitmap uri) {
        this.bitmap = uri;
    }

    public String getTen() {
        return ten;
    }

    public String getMota() {
        return mota;
    }

    public int getImg() {
        return img;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
