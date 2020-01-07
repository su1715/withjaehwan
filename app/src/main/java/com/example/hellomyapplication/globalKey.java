package com.example.hellomyapplication;

import android.app.Application;

public class globalKey extends Application {
    private String globalkey;
    public String getGlobalkey(){
        return globalkey;
    }
    public void setGlobalkey(String globalkey1){
        this.globalkey = globalkey1;
    }
}
