package edu.uw.jiawang;


public class Researcher extends User {

    private int resId;

    public Researcher(){
    }

    public void setResId(int resId){
        this.resId = resId;
    }

    public int getResId(){
        return resId;
    }
}