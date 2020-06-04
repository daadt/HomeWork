package com.example.web;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView mWeb;
    private Button mWebLoad;
    private ProgressBar mBarProgress;

    private Button mHtmlLoad;
    private TextView mProgress;
    private final int CODE_PERMISSION = 4;
    private final int CODE_CROP = 3;
    private boolean FLAG_PERMISSION = false;
    private List<String> list;
    private File file;
    private Uri cropUri;
    private ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mWebLoad = (Button) findViewById(R.id.But_web);
        mWebLoad.setOnClickListener(this);
        mBarProgress = (ProgressBar) findViewById(R.id.progress_bar);
        mWeb = (WebView) findViewById(R.id.Web_tet);
        mProgress = (TextView) findViewById(R.id.progress);
        mHtmlLoad = (Button) findViewById(R.id.But_web_js);
        mHtmlLoad.setOnClickListener(this);
        //6.0以后需要获取权限
        obtainPermission();
        setWeb();

        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //开始加载，显示进度条
                mProgress.setVisibility(View.VISIBLE);
                mBarProgress.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }
        });
        mWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //进度变化
                //如果是100，代表加载完成停止显示进度条
                if (newProgress == 100) {
                    mBarProgress.setVisibility(View.GONE);
                    mProgress.setVisibility(View.GONE);
                }
                mProgress.setText(newProgress + "%");
                Log.i("进度", "onProgressChanged: " + newProgress);
                mBarProgress.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }


        });
        mWeb.addJavascriptInterface(new Scripja(this), "test");

        mImg = (ImageView) findViewById(R.id.img);
    }

    //获取权限
    private void obtainPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            list = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                list.add(Manifest.permission.CAMERA);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (list.size() != 0)
                requestPermissions(list.toArray(new String[list.size()]), CODE_PERMISSION);
        } else
            FLAG_PERMISSION = true;
    }

    //Webview设置属性
    private void setWeb() {
        mWeb.setWebViewClient(new WebViewClient());

        WebSettings settings = mWeb.getSettings();

//支持js
        settings.setJavaScriptEnabled(true);

//适配屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

//缩放
        settings.setSupportZoom(true);

        settings.setBuiltInZoomControls(true);

        settings.setDisplayZoomControls(false);


//无图模式
        settings.setLoadsImagesAutomatically(true);
//设置编码格式
        settings.setDefaultTextEncodingName("GBK");
/*
        if (NetWeb.isConnected(getApplicationContext())) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }*/

        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);

        //  String cache = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//settings.cacheDirPath(cache);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("test", Arrays.toString(grantResults));
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == -1) {
                FLAG_PERMISSION = false;
                break;
            }
        }
        FLAG_PERMISSION = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.But_web:
                // TODO 20/06/01
                mWeb.loadUrl("https://www.cdstm.cn/");
                break;
            case R.id.But_web_js:// TODO 20/06/01

                mWeb.loadUrl("file:///android_asset/demo2.html");
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mWeb != null && mWeb.canGoBack()) {
            mWeb.goBack();
        } else {
            super.onBackPressed();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String imagePath = c.getString(columnIndex);
                    c.close();
                    cropPhoto(selectedImage);
                    break;
                case CODE_CROP:
                    mWeb.evaluateJavascript("javascript:logImgPath(\"" + cropUri.toString() + "\")", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                            mImg.setImageURI(cropUri);
                        }
                    });


            }
        }
    }

    //剪切图片
    private void cropPhoto(Uri uri) {
        Log.d("test", "uri:" + uri.toString());
        file = new File(Environment.getExternalStorageDirectory(), "cropImage" + System.currentTimeMillis() + ".jpg");

        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        cropUri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        //裁剪图片的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("crop", "true");
        // 裁剪后输出

        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CODE_CROP);
    }

}
