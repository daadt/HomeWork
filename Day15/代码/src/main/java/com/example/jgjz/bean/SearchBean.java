package com.example.jgjz.bean;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class SearchBean {
    @Id
    private String kerword;
    private Long time;
    @Generated(hash = 1762753242)
    public SearchBean(String kerword, Long time) {
        this.kerword = kerword;
        this.time = time;
    }
    @Generated(hash = 562045751)
    public SearchBean() {
    }
    public String getKerword() {
        return this.kerword;
    }
    public void setKerword(String kerword) {
        this.kerword = kerword;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    
}