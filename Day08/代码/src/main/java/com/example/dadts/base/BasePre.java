package com.example.dadts.base;

public class BasePre<V extends BaseView>  {
   public V view;
    public void bindView(V v){
        this.view= v;
    }
}
