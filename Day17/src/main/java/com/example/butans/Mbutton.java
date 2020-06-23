package com.example.butans;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;


@SuppressLint("AppCompatCustomView")
public class Mbutton extends Button {

    public Mbutton(Context context){
        super(context);
    }

    public Mbutton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Mbutton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mDowX;
    private float mDowY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDowX = event.getX();
                mDowY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();

                float vX = moveX - mDowX;
                float vY = moveY - mDowY;

                float x = getX();
                float y = getY();

                setTranslationX(x + vX);
                setTranslationY(y + vY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
    public  float getlX(){
        return getX();
    } public  float getlY(){
        return getY();
    }


}
