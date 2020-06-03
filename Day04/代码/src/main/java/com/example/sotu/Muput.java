package com.example.sotu;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Muput {
    public static Bitmap bitmapCompress(Bitmap bitmap) {
        ByteArrayInputStream bis = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            // 100KB 如果压缩完的结果100KB之内 那就一直压缩
            int opntions = 100; //压缩比例
            while (bos.toByteArray().length / 1024 > 100) {
                bos.reset();
                opntions -= 10;
                if (opntions >= 0) {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, opntions, bos);
                } else {
                    break;
                }
            }
            bis = new ByteArrayInputStream(bos.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaze(bos, null,bis);
        }
        return BitmapFactory.decodeStream(bis);
    }
    public static boolean bitmapCompressSave(Bitmap bitmap, File file) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //1.压缩后图片格式 2.压缩比例 0(压缩程度最大)---100(完全不压缩)  3.压缩完之后的图片保存的输出流对象
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        // 100KB 如果压缩完的结果100KB之内 那就一直压缩
        int opntions = 100; //压缩比例
        while (bos.toByteArray().length / 1024 > 100) {
            bos.reset();
            //调整压缩比例  每次减10
            opntions -= 10;
            if (opntions >= 0) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, opntions, bos);
                //占用的磁盘空间大小
                Log.e("TAG----000", "每次压缩完之后：" + bos.toByteArray().length / 1024 + "KB");
            } else {
                break;
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(bos.toByteArray());
            fos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            releaze(bos, fos, null);
        }
    }

    private static void releaze(ByteArrayOutputStream bos, FileOutputStream fos, ByteArrayInputStream bis) {
        try {
            if (fos != null)
                fos.close();
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
