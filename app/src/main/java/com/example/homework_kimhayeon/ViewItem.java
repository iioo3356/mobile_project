package com.example.homework_kimhayeon;

import android.net.Uri;

public class ViewItem {
    int num;
    boolean isSelected;
    String name;
    Uri srcUri;
    int price;

    public ViewItem(int num, String name, Uri srcUri, int price, boolean isSelected){
        this.num = num;
        this.name = name;
        this.srcUri = srcUri;
        this.price = price;
        this.isSelected = isSelected;
    }
    public boolean getSelected(){
        return isSelected;
    }
    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num){
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    public Uri getSrcUri(){
        return srcUri;
    }
    public void setSrcUri(Uri srcUri){
        this.srcUri = srcUri;
    }
    public int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price = price;
    }
}
