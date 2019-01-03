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

package com.github.chnrohin.rohinchat.common.design.recyclerview.itemdecoration;

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
public class MultiItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "MultiItemDecoration";

    private static final boolean DEBUG = false;

    private static final int DIVIDEND = 2;

    /**
     * @hide
     */
    @IntDef({VERTICAL_TOP, VERTICAL_BOTTOM, HORIZONTAL, HORIZONTAL_LEFT, HORIZONTAL_RIGHT, AROUND})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Orientation {
    }

    /**
     * Start draw at recyclerView vertical top
     */
    public static final int VERTICAL_BOTTOM = 0;

    /**
     * Start draw at recyclerView vertical bottom
     */
    public static final int VERTICAL_TOP = 2;

    /**
     * Start draw at recyclerView horizontal left and right
     */
    public static final int HORIZONTAL = 1;

    /**
     * Start draw at recyclerView horizontal left
     */
    public static final int HORIZONTAL_LEFT = 3;

    /**
     * Start draw at recyclerView horizontal right
     */
    public static final int HORIZONTAL_RIGHT = 5;

    /**
     * Start draw at recyclerView vertical and horizontal at top、bottom、left、right
     */
    public static final int AROUND = -1;

    @IntDef({FILL_RV_INCLUDE_PADDING, FILL_LI_INCLUDE_PADDING,
            FILL_RV_WITHOUT_PADDING, FILL_LI_WITHOUT_PADDING})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Fill {
    }

    /**
     * Fill RecyclerView WithOut Padding
     */
    public static final int FILL_RV_INCLUDE_PADDING = 0;

    /**
     * Fill RecyclerView List Item
     */
    public static final int FILL_LI_INCLUDE_PADDING = 1;

    /**
     * Fill RecyclerView Include Padding
     */
    public static final int FILL_RV_WITHOUT_PADDING = 2;

    /**
     * Fill RecyclerView List Item Include Padding
     */
    public static final int FILL_LI_WITHOUT_PADDING = 3;

    /**
     * @see Fill
     * @see #FILL_RV_INCLUDE_PADDING
     * @see #FILL_LI_INCLUDE_PADDING
     * @see #FILL_RV_WITHOUT_PADDING
     * @see #FILL_LI_WITHOUT_PADDING
     */
    private int fill;

    /**
     * @see Orientation
     * @see #VERTICAL_TOP
     * @see #VERTICAL_BOTTOM
     * @see #HORIZONTAL
     * @see #HORIZONTAL_LEFT
     * @see #HORIZONTAL_RIGHT
     * @see #AROUND
     */
    private int orientation;

    /**
     * 图像分割
     */
    private Drawable draDivider;

    /**
     * 分割颜色
     */
    private int color;

    /**
     * Divider高度
     */
    private int height;

    /**
     * Divider宽度
     */
    private int width;

    /**
     * Drawable
     */
    private final Rect mBounds = new Rect();

    /**
     * 可自定义的画笔，用于给分割上色等设置图像方面参数，目前无法上色！！！{@link Paint#setColor(int)}
     * <p>
     * 用 Canvas顶着！！！{@link Canvas#drawColor(int)}
     */
    private Paint paint;

    @SuppressWarnings("unused")
    private MultiItemDecoration() {
    }

    private MultiItemDecoration(Builder builder) {
        orientation = builder.orientation;
        height = builder.height;
        width = builder.width;
        color = builder.color;
        // defValue = null
        draDivider = builder.draDivider;
        // defValue = null
        paint = builder.paint;
        // defValue = 0 = RecyclerView
        fill = builder.fill;

        validParams();
    }

    public static class Builder {
        private int orientation;
        private int height;
        private int width;
        private Drawable draDivider;
        private int color;
        private Paint paint;
        private int fill;

        public Builder() {
        }

        public Builder orientation(@Orientation int orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder color(@ColorInt int color) {
            this.color = color;
            return this;
        }

        public Builder drawable(Drawable draDivider) {
            this.draDivider = draDivider;
            return this;
        }

        public Builder paint(Paint paint) {
            this.paint = paint;
            return this;
        }

        public Builder fill(int fill) {
            this.fill = fill;
            return this;
        }

        public MultiItemDecoration build() {
            return new MultiItemDecoration(this);
        }

    }

    /**
     * 设置 divider布局方向， {@link RecyclerView.LayoutManager}改变 orientation后应该被调用。
     *
     * @param orientation {@link Orientation}
     */
    public void setOrientation(@Orientation int orientation) {
        this.orientation = orientation;
    }

    /**
     * 设置 {@link Drawable}为当前 Divider。
     *
     * @param drawable 将 Drawable作为一个 Divider。
     */
    public void setDrawable(@NonNull Drawable drawable) {
        draDivider = drawable;
    }

    /**
     * 图层等级低于{@link #onDrawOver} - 与RecyclerView 的 ListItem在同一图层。
     */
    @Override
    public void onDraw(@NonNull Canvas c,
                       @NonNull RecyclerView parent,
                       @NonNull RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            Log.e(TAG, "parent.getLayoutManager is null");
            return;
        }
        // 绘制自定义分割
        drawDivider(c, parent);
    }

    private void drawDivider(final Canvas canvas, final RecyclerView parent) {
        if (orientation == AROUND) {
            return;
        }
        // 垂直方向默认为2的倍数
        if (orientation % DIVIDEND == 0) {
            drawVertical(canvas, parent);
        } else {
            drawHorizontal(canvas, parent);
        }
    }

    private void drawVertical(final Canvas canvas, final RecyclerView parent) {
        canvas.save();

        final int left;
        final int right;

        boolean flag = fill % DIVIDEND == 0;
        final View view = flag ? parent : parent.getChildAt(0);

        switch (fill) {
            case FILL_RV_INCLUDE_PADDING:
                left = view.getLeft();
                right = view.getRight();
                break;
            case FILL_LI_INCLUDE_PADDING:
                left = view.getLeft();
                right = view.getRight();
                break;
            case FILL_RV_WITHOUT_PADDING:
                left = flag ? view.getPaddingStart() : view.getLeft() + view.getPaddingStart();
                right = view.getRight() - view.getPaddingEnd();
                break;
            case FILL_LI_WITHOUT_PADDING:
                left = flag ? view.getPaddingStart() : view.getLeft() + view.getPaddingStart();
                right = view.getRight() - view.getPaddingEnd();
                break;
            default:
                String e = String.format("Invalid Value!!!(fill:%1$s)", fill);
                throw new IllegalArgumentException(e);
        }

        if (DEBUG) {
            Log.d(TAG, "orientation: " + orientation + ", color: " + color +
                    ", paint Color: " + paint.getColor() + ", fill: " + this.fill +
                    ", height: " + height + ", width: " + width +
                    ", left: " + left + ", right: " + right);
        }

        final int childCount = parent.getChildCount();
        switch (orientation) {
            case VERTICAL_TOP:
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    if (draDivider == null) {
                        final int bottom = child.getTop() + Math.round(child.getTranslationY());
                        final int top = bottom - height;
                        canvas.drawRect(left, top, right, bottom, paint);
                    } else {
                        parent.getDecoratedBoundsWithMargins(child, mBounds);
                        final int bottom = mBounds.top + Math.round(child.getTranslationY());
                        final int top = bottom - draDivider.getIntrinsicHeight();
                        draDivider.setBounds(left, top, right, bottom);
                        draDivider.draw(canvas);
                    }
                }
                break;
            case VERTICAL_BOTTOM:
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    if (draDivider == null) {
                        final int top = child.getBottom();
                        final int bottom = top + this.height;
                        canvas.drawRect(left, top, right, bottom, paint);
                    } else {
                        parent.getDecoratedBoundsWithMargins(child, mBounds);
                        final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                        final int top = bottom - draDivider.getIntrinsicHeight();
                        draDivider.setBounds(left, top, right, bottom);
                        draDivider.draw(canvas);
                    }
                }
                break;
            default:
                // do nothing
                break;
        }

        canvas.restore();
    }

    private void drawHorizontal(final Canvas canvas, final RecyclerView parent) {
        canvas.save();
        parent.getChildAt(0);
        canvas.restore();
    }

    /**
     * 有效参数
     */
    private void validParams() {
        if (height <= 0 && width <= 0) {
            if (orientation % DIVIDEND == 0) {
                height = 1;
            } else {
                width = 1;
            }
        }

        if (paint == null) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(color == 0 ? Color.LTGRAY : color);
        } else {
            // 若已自定义paint且已自定义build.color，则以build.color设置的颜色为主。
            if (color != 0) {
                paint.setColor(color);
            }
        }
    }

    /**
     * 图层高于{@link #onDraw} - 在RecyclerView 的 ListItem的上一层。
     */
    @Override
    public void onDrawOver(@NonNull Canvas c,
                           @NonNull RecyclerView parent,
                           @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    /**
     * 设置偏移量。
     * Rect - 相当于Margin。
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect,
                               @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        if (DEBUG) {
            Log.d(TAG, "outRect: " + outRect.toString());
        }
        setItemOffsets(outRect);
    }

    /**
     * 根据 {@link Orientation}、{@link #height}以及{@link #width}设置绘制空间
     *
     * @param outRect RecyclerView ListItem外部绘制空间
     * @see #getItemOffsetsCommonRect(Rect)
     * @see #getItemOffsetsDrawableRect(Rect)
     */
    private void setItemOffsets(Rect outRect) {
        outRect = draDivider == null
                ? getItemOffsetsCommonRect(outRect)
                : getItemOffsetsDrawableRect(outRect);
        outRect.set(outRect);
    }

    private Rect getItemOffsetsCommonRect(Rect outRect) {
        if (orientation == VERTICAL_TOP) {
            outRect.top = height;
        } else if (orientation == VERTICAL_BOTTOM) {
            outRect.bottom = height;
        } else {
            throw new IllegalArgumentException(
                    "无效的mOrientation，必须是自定义注解Orientation中含有的参数。");
        }
        return outRect;
    }

    private Rect getItemOffsetsDrawableRect(Rect outRect) {
        if (orientation == VERTICAL_TOP) {
            outRect.top = draDivider.getIntrinsicHeight();
        } else if (orientation == VERTICAL_BOTTOM) {
            outRect.bottom = draDivider.getIntrinsicHeight();
        } else {
            throw new IllegalArgumentException(
                    "无效的mOrientation，必须是自定义注解Orientation中含有的参数。");
        }
        return outRect;
    }
}
