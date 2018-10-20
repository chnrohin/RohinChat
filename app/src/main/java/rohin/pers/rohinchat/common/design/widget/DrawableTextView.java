/*
 * Copyright © Rohin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rohin.pers.rohinchat.common.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import rohin.pers.rohinchat.R;
import rohin.pers.rohinchat.common.util.LogUtils;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class DrawableTextView extends AppCompatTextView {

    /** @hide */
    @IntDef({LEFT, TOP, RIGHT, BOTTOM})
    @IntRange(from = 1)
    @Retention(RetentionPolicy.SOURCE)
    private @interface Position {}

    // Where are the drawable show in View.
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;

    // Default value
    private static final int DEF_DRAWABLE_WIDTH = 24;
    private static final int DEF_DRAWABLE_HEIGHT = 24;
    private static final int DEF_DRAWABLE_PADDING = 16;

    // AttributeSet
    private Drawable mXMLDrawable;
    private Bitmap mBitmap;
    private int mDrawableWidth;
    private int mDrawableHeight;
    private int mPosition;
    private int mDrawablePadding;

    public DrawableTextView(Context context) {
        this(context, null);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 获取XML布局文件中的相关参数初始化控件。
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        // 对指定XML中的View进行解析，获取参数
        final TypedArray a = context.getTheme().obtainStyledAttributes(attrs
                , R.styleable.DrawableTextView, defStyleAttr, 0);

        mXMLDrawable = a.getDrawable(R.styleable.DrawableTextView_drawableSrc);

        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.styleable.DrawableTextView_bitmapSrc);

        mDrawableWidth = a.getDimensionPixelSize(R.styleable.DrawableTextView_drawableWidth,
                DEF_DRAWABLE_WIDTH);

        mDrawableHeight = a.getDimensionPixelSize(R.styleable.DrawableTextView_drawableHeight,
                DEF_DRAWABLE_HEIGHT);

        mPosition = a.getInt(R.styleable.DrawableTextView_drawablePosition, LEFT);

        mDrawablePadding = a.getDimensionPixelSize(R.styleable.DrawableTextView_drawablePadding,
                DEF_DRAWABLE_PADDING);

        // 父类方法，回收TypeArray，使用前禁止重组，否则抛出RuntimeException("recycle twice")异常。
        a.recycle();

        // 至少需要绑定一种图像
        if (null == mXMLDrawable && null == mBitmap) {
            String e = "User 'TextView' rather then 'DrawableTextView' "
                    + "if you don't need to set a drawable or bitmap!";
            LogUtils.e(e);
            throw new NullPointerException(e);
        }

        // 禁止同时绑定两种不同类型的图像
        if (null != mXMLDrawable && null != mBitmap) {
            String e = "DrawableTextView can only set one of between drawable and bitmap!";
            LogUtils.e(e);
            throw new IllegalArgumentException(e);
        }

        // 将Bitmap转换为Drawable
        if (null != mBitmap) {
            mXMLDrawable = new BitmapDrawable(getResources(),
                    Bitmap.createScaledBitmap(mBitmap, mDrawableWidth, mDrawableHeight, true));
        }

        setDrawable(mXMLDrawable, mPosition, mDrawablePadding, mDrawableWidth, mDrawableHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * @see #setDrawable(Drawable, int, int)
     */
    public void setDrawable(final Drawable drawable) {
        setDrawable(drawable, 0, 0);
    }

    /**
     * @see #setDrawable(Drawable, int, int, int)
     */
    public void setDrawable(final Drawable drawable, int width, int height) {
        setDrawable(drawable, 0, width, height);
    }

    /**
     * @see #setDrawable(Drawable, int, int, int, int)
     */
    public void setDrawable(final Drawable drawable, int padding, int width, int height) {
        setDrawable(drawable, LEFT, padding, width, height);
    }

    /**
     * 调用父类方法根据{@link #mPosition}获将图像绑定在控件指定位置。
     * 可能抛的异常 IllegalAccessException -> Drawable暂未做类型判断
     *
     * @param drawable 图像
     * @param position 图像位置 {@link #LEFT} {@link #TOP} {@link #RIGHT} {@link #BOTTOM}
     * @param padding  图像与文字内间距 {@link #DEF_DRAWABLE_PADDING}
     * @param width    宽 {@link #DEF_DRAWABLE_WIDTH}
     * @param height   高 {@link #DEF_DRAWABLE_HEIGHT}
     */
    public void setDrawable(final Drawable drawable,
                            @Position int position, int padding, int width, int height) {

        if (drawable != null) {
            // 参数不合法将重置为默认值
            if (padding <= 0)   padding = DEF_DRAWABLE_PADDING;
            if (width <= 0)     width   = DEF_DRAWABLE_WIDTH;
            if (height <= 0)    height  = DEF_DRAWABLE_HEIGHT;

            drawable.setBounds(0, 0, width, height);

            setCompoundDrawables(position, drawable);
            // 控件内图片与文字间距
            setCompoundDrawablePadding(padding);
        }
    }

    /**
     * @see #setBitmap(Bitmap, int, int)
     */
    public void setBitmap(final Bitmap bitmap) {
        if (bitmap != null) {
            setBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    /**
     * @see #setBitmap(Bitmap, int, int, int)
     */
    public void setBitmap(final Bitmap bitmap, int width, int height) {
        if (bitmap != null) {
            if (width <= 0)     width   = bitmap.getWidth();
            if (height <= 0)    height  = bitmap.getHeight();

            setBitmap(bitmap, 0, width, height);
        }
    }

    /**
     * @see #setBitmap(Bitmap, int, int, int, int)
     */
    public void setBitmap(final Bitmap bitmap, int padding, int width, int height) {
        if (bitmap != null) {
            if (padding < 0)    padding    = DEF_DRAWABLE_PADDING;
            if (width <= 0)     width      = bitmap.getWidth();
            if (height <= 0)    height     = bitmap.getHeight();

            setBitmap(bitmap, padding, width, height);
        }
    }

    /**
     * 调用父类方法根据{@link #mPosition}获将图像绑定在控件指定位置。
     * 可能抛的异常 IllegalAccessException -> Drawable暂未做类型判断
     *
     * @param bitmap   图像
     * @param position 图像位置 {@link #LEFT} {@link #TOP} {@link #RIGHT} {@link #BOTTOM}
     * @param padding  图像与文字内间距 {@link #DEF_DRAWABLE_PADDING}
     * @param width    宽 {@link #DEF_DRAWABLE_WIDTH}
     * @param height   高 {@link #DEF_DRAWABLE_HEIGHT}
     */
    @SuppressWarnings("unused")
    public void setBitmap(final Bitmap bitmap,
                          @Position int position, int padding, int width, int height) {
        if (bitmap != null) {
            if (padding < 0)    padding = DEF_DRAWABLE_PADDING;
            if (width <= 0)     width   = bitmap.getWidth();
            if (height <= 0)    height  = bitmap.getHeight();

            BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);

            drawable.setBounds(0, 0, width, height);

            setCompoundDrawables(position, drawable);
            // 控件内图片与文字间距
            setCompoundDrawablePadding(padding);
        }
    }

    /**
     * 调用父类方法设置对应位置的图像
     *
     * @see android.widget.TextView#setCompoundDrawables(Drawable, Drawable, Drawable, Drawable)
     */
    private void setCompoundDrawables(final @Position int position, final Drawable drawable) {
        switch (position) {
            case LEFT:
                setCompoundDrawables(
                        drawable, null, null, null);
                break;
            case TOP:
                setCompoundDrawables(
                        null, drawable, null, null);
                break;
            case RIGHT:
                setCompoundDrawables(
                        null, null, drawable, null);
                break;
            case BOTTOM:
                setCompoundDrawables(
                        null, null, null, drawable);
                break;
            default:
                break;
        }
    }

    /**
     * 缩放原图像
     */
    @SuppressWarnings("unused")
    private Bitmap getScaleBitmap() {
        int width   = mBitmap.getWidth();
        int height  = mBitmap.getHeight();

        float scaleWidth    = (float) mDrawableWidth / width;
        float scaleHeight   = (float) mDrawableHeight / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);
    }

    @SuppressWarnings("unused")
    public Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width   = drawable.getIntrinsicWidth();
        int height  = drawable.getIntrinsicHeight();

        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        // 计算、设置缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        matrix.postScale(sx, sy);
        // 对原bitmap的缩放后建立新的bitmap
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(getResources(), newbmp);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

}
