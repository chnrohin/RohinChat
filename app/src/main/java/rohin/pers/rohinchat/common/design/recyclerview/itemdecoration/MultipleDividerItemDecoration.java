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

package rohin.pers.rohinchat.common.design.recyclerview.itemdecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class MultipleDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "MultipleDivider";

    private static final boolean DEBUG = false;

    /**
     * @hide
     */
    @IntDef({VERTICAL_TOP, VERTICAL_BOTTOM, HORIZONTAL, HORIZONTAL_LEFT, HORIZONTAL_RIGHT, AROUND})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    /**
     * 上 - 垂直方向 - Start draw at recyclerView top
     */
    public static final int VERTICAL_TOP = 1;

    /**
     * 下 - 垂直方向 - Start draw at recyclerView bottom
     */
    public static final int VERTICAL_BOTTOM = 3;

    /**
     * 水平方向
     */
    public static final int HORIZONTAL = 2;

    /**
     * 左 - 水平方向
     */
    public static final int HORIZONTAL_LEFT = 4;

    /**
     * 右 - 水平方向
     */
    public static final int HORIZONTAL_RIGHT = 6;

    /**
     * 四周
     */
    public static final int AROUND = -1;

    @IntDef({RECYCLER_VIEW, LIST_ITEM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Item {
    }

    /**
     * RecyclerView
     */
    public static final int RECYCLER_VIEW = 1;

    /**
     * RecyclerView List Item
     */
    public static final int LIST_ITEM = 2;

    /**
     * 对象
     *
     * @see Item
     */
    private int mItem;

    /**
     * divider是否响应padding
     */
    private boolean mMatchPadding;

    /**
     * 当前方向
     *
     * @see Orientation
     */
    private int mOrientation;

    /**
     * 图像分割
     */
    private Drawable mDivider;

    /**
     * 分割颜色
     */
    private int mColor;

    /**
     * Divider高度
     */
    private int mHeight;

    /**
     * Divider宽度
     */
    private int mWidth;

    /**
     * Drawable
     */
    private final Rect mBounds = new Rect();

    /**
     * 可自定义的画笔，用于给分割上色等设置图像方面参数，目前无法上色！！！{@link Paint#setColor(int)}
     * <p>
     * 用 Canvas顶着！！！{@link Canvas#drawColor(int)}
     */
    private Paint mPaint;

    @SuppressWarnings("unused")
    private MultipleDividerItemDecoration() {

    }

    private MultipleDividerItemDecoration(Builder builder) {
        mOrientation = builder.mOrientation;
        mItem = builder.mItem;
        mHeight = builder.mHeight;
        mWidth = builder.mWidth;
        mColor = builder.mColor;
        mDivider = builder.mDivider;
        mPaint = builder.mPaint;
        mMatchPadding = builder.mMatchPadding;
    }

    public static class Builder {
        private int mOrientation;
        private int mItem;
        private Drawable mDivider;
        private Paint mPaint;
        private boolean mMatchPadding;
        private int mHeight;
        private int mWidth;
        private int mColor;

        {
            mItem = RECYCLER_VIEW;
            mHeight = 1;
            mWidth = 0;
            mColor = Color.LTGRAY;
        }

        public Builder(@Orientation int orientation) {
            this.mOrientation = orientation;
        }

        public Builder height(int height) {
            mHeight = height;
            return this;
        }

        public Builder width(int width) {
            mWidth = width;
            return this;
        }

        public Builder color(@ColorInt int color) {
            mColor = color;
            return this;
        }

        public Builder drawable(Drawable divider) {
            mDivider = divider;
            return this;
        }

        public Builder paint(Paint paint) {
            mPaint = paint;
            return this;
        }

        public Builder matchPadding(boolean matchPadding) {
            mMatchPadding = matchPadding;
            return this;
        }

        public Builder item(int item) {
            mItem = item;
            return this;
        }

        public MultipleDividerItemDecoration build() {
            return new MultipleDividerItemDecoration(this);
        }

    }

    /**
     * 设置 divider布局方向， {@link RecyclerView.LayoutManager}改变 orientation后应该被调用。
     *
     * @param orientation {@link Orientation}
     */
    public void setOrientation(@Orientation int orientation) {
        mOrientation = orientation;
    }

    /**
     * 设置 {@link Drawable}为当前 Divider。
     *
     * @param drawable 将 Drawable作为一个 Divider。
     */
    public void setDrawable(@NonNull Drawable drawable) {
        mDivider = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }
        // 绘制自定义分割
        drawMultipleDivider(c, parent);
    }

    private void drawMultipleDivider(final Canvas canvas, final RecyclerView parent) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mColor);
        }

        int modDividend = 2;
        if (mOrientation % modDividend == 0) {
            drawHorizontal(canvas, parent);
        } else {
            if (mOrientation == AROUND) {
                drawVertical(canvas, parent);
                drawHorizontal(canvas, parent);
            } else {
                drawVertical(canvas, parent);
            }
        }
    }

    private void drawVertical(final Canvas canvas, final RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;

        if (mItem == LIST_ITEM) {
            final View child = parent.getChildAt(0);
            if (mMatchPadding) {
                left = child.getLeft() + child.getPaddingStart();
                right = child.getRight() - child.getPaddingEnd();
            } else {
                left = child.getLeft();
                right = child.getRight();
            }
        } else {
            if (mMatchPadding) {
                left = parent.getPaddingStart();
                right = parent.getRight() - parent.getPaddingEnd();
            } else {
                left = parent.getLeft();
                right = parent.getRight();
            }
        }

        if (DEBUG) {
            Log.d(TAG, "mOrientation: " + mOrientation
                    + ", mColor: " + mColor + ", mPaint Color: " + mPaint.getColor()
                    + ", mItem: " + mItem + ", mMatchPadding: " + mMatchPadding
                    + ", mHeight: " + mHeight + ", mWidth: " + mWidth
                    + ", left: " + left + ", right: " + right);
        }

        final int childCount = parent.getChildCount();
        if (mOrientation == VERTICAL_TOP) {
            if (mDivider == null) {
                // DEBUG PASS
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    final int bottom = child.getTop() + Math.round(child.getTranslationY());
                    final int top = bottom - mHeight;
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            } else {
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    parent.getDecoratedBoundsWithMargins(child, mBounds);
                    final int bottom = mBounds.top + Math.round(child.getTranslationY());
                    final int top = bottom - mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
            }
        } else {
            if (mDivider == null) {
                // DEBUG PASS
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    final int top = child.getBottom();
                    final int bottom = top + this.mHeight;
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            } else {
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    parent.getDecoratedBoundsWithMargins(child, mBounds);
                    final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                    final int top = bottom - mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
            }
        }

        canvas.restore();
    }

    private void drawHorizontal(final Canvas canvas, final RecyclerView parent) {
        canvas.save();

        canvas.restore();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        setItemOffsets(outRect);
        if (DEBUG) {
            Log.d(TAG, "outRect: " + outRect.toString());
        }
    }

    /**
     * 根据 {@link Orientation}、{@link #mHeight}以及{@link #mWidth}设置绘制空间
     *
     * @see #getItemOffsetsCommonRect(Rect)
     * @see #getItemOffsetsDrawableRect(Rect)
     * @param outRect RecyclerView ListItem外部绘制空间
     */
    private void setItemOffsets(Rect outRect) {
        if (mDivider == null) {
            outRect = getItemOffsetsCommonRect(outRect);
        } else {
            outRect = getItemOffsetsDrawableRect(outRect);
        }
        outRect.set(outRect);
    }

    private Rect getItemOffsetsCommonRect(Rect outRect) {
        if (mOrientation == VERTICAL_TOP) {
            outRect.top = mHeight;
        } else if (mOrientation == VERTICAL_BOTTOM) {
            outRect.bottom = mHeight;
        } else {
            throw new IllegalArgumentException(
                    "无效的mOrientation，必须是自定义注解Orientation中含有的参数。");
        }
        return outRect;
    }

    private Rect getItemOffsetsDrawableRect(Rect outRect) {
        if (mOrientation == VERTICAL_TOP) {
            outRect.top = mDivider.getIntrinsicHeight();
        } else if (mOrientation == VERTICAL_BOTTOM) {
            outRect.bottom = mDivider.getIntrinsicHeight();
        } else {
            throw new IllegalArgumentException(
                    "无效的mOrientation，必须是自定义注解Orientation中含有的参数。");
        }
        return outRect;
    }
}
