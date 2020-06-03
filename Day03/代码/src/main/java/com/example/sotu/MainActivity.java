package com.example.sotu;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private ImageView mIv;
    private Display mDefaultDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIv = (ImageView) findViewById(R.id.iv);
        WindowManager windowManager = getWindowManager();
        mDefaultDisplay = windowManager.getDefaultDisplay();
    }

    public void load_img(View view) {
        switch (view.getId()) {
            case R.id.btn_load_img1:
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), R.mipmap.iy, options);

                //高宽
                int outWidth = options.outWidth;
                int outHeight = options.outHeight;
                Log.e("TAG", "屏宽" + outHeight + "屏高" + outHeight);

                //手机屏幕高宽
                int height = mDefaultDisplay.getHeight();
                int width = mDefaultDisplay.getWidth();

                int scaleSize = 1;
                if (outWidth > width && outHeight < height) {
                    scaleSize = outWidth / width;
                } else if (outWidth > height && outWidth < width) {
                    scaleSize = outHeight / height;
                } else if (outWidth > width && outHeight > height) {
                    int scaleX = outWidth / width;
                    int scaleY = outHeight / height;
                    scaleSize = scaleX > scaleY ? scaleX : scaleY;
                }

                options.inSampleSize = scaleSize;
                //不再解析整个边界 而是真正解析整张图片
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.iy, options);
                mIv.setImageBitmap(bitmap);

                break;

            case R.id.btn_load_img2:
                BitmapFactory.Options options1 = new BitmapFactory.Options();
                options1.inSampleSize = 15;
                Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.iy, options1);


                Matrix matrix = new Matrix();
                float wscal = (float) (bitmap2.getWidth() * 1.0 / (bitmap2.getWidth() - 25));
                float hscal = (float) (bitmap2.getHeight() * 1.0 / (bitmap2.getHeight() - 25));

                matrix.postScale(wscal, hscal);


                Bitmap bitmap11 = Bitmap.createBitmap(bitmap2, 12, 12, bitmap2.getWidth() - 25, bitmap2.getHeight() - 25, matrix, true);

                mIv.setImageBitmap(bitmap11);
                break;
            case R.id.btn_load_img3:
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inSampleSize = 15;
                Bitmap bitmap1= BitmapFactory.decodeResource(getResources(),R.mipmap.yt,options2);
                //Bitmap bitmap3 = BitMapUtil.bitmapCompress(bitmap1);
                Bitmap bitmap3 = Muput.bitmapCompress(bitmap1);
                mIv.setImageBitmap(bitmap3);
                break;
            case R.id.btn_load_img4:
                BitmapFactory.Options options3 = new BitmapFactory.Options();
                options3.inSampleSize = 15;
                Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.mipmap.yt, options3);
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    File file = new File(rootPath, "wwwwwwwwwwwwwwwwww.jpg");
                    boolean b = Muput.bitmapCompressSave(b1, file);
                    if (b) {
                        Log.e("ATG", "保存成功");
                    } else {
                        Log.e("ATG", "保存失败");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "不可用", Toast.LENGTH_LONG).show();
                }

                break;

        }
    }


}
