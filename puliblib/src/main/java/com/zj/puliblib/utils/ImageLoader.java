package com.zj.puliblib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.zhongjia.puliblib.R;

import java.io.File;


/**
 * Created by Administrator on 2016-07-20.
 */
public class ImageLoader {

    /**
     * 图像显示
     *
     * @param context
     * @param picUrl
     * @param image_iv
     */
    public static void display(Context context, String picUrl, ImageView image_iv) {
        Logutil.e("显示图片=" + picUrl);
        Glide.with(context).load(picUrl)
                .dontAnimate()
                .placeholder(R.drawable.empty_default)
                .into(image_iv);
    }

    /**
     * 图像显示
     *
     * @param context
     * @param picUrl
     * @param image_iv
     */
    public static void displayBanner(Context context, String picUrl, ImageView image_iv) {
        if (TextUtils.isEmpty(picUrl)) {
            image_iv.setImageResource(R.drawable.empty_default);
            return;
        }
        Glide.with(context).load(picUrl)
                .dontAnimate()
                .placeholder(R.drawable.empty_banner)
                .error(R.drawable.empty_banner)
                .crossFade()
                .into(image_iv);
    }


    /**
     * 展示本地图片文件
     *
     * @param context
     * @param picPath
     * @param image_iv
     */
    public static void displayFilePath(Context context, String picPath, ImageView image_iv) {
        if (TextUtils.isEmpty(picPath)) {
            image_iv.setImageResource(R.drawable.empty_default);
            return;
        }
        Glide.with(context).load(new File(picPath))
                .placeholder(R.drawable.empty_default)
                .error(R.drawable.empty_default).into(image_iv);
    }


    public static void disPlayCircleImage(Context context, String picUrl, ImageView imageview) {
        Glide.with(context).load(picUrl).transform(new CircleTransform(context)).into(imageview);
    }

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }
}
