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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import rohin.pers.rohinchat.R;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class Divider extends View {

    private int type;

    public Divider(Context context) {
        super(context);
        TypedArray ta = context.obtainStyledAttributes(R.styleable.Divider);
        type = ta.getInteger(R.styleable.Divider_boardType, 1);
        ta.recycle();
    }

    public Divider(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Divider(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Divider(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Divider);
        type = ta.getInteger(R.styleable.Divider_boardType, 1);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawLine(canvas, type);
    }

    private void drawLine(Canvas canvas, int type) {
        switch (type) {
            case 1:
                drawLineMargin0(canvas);
                break;
            case 2:
                drawLineMargin30(canvas);
                break;
            default:
                throw new IllegalArgumentException("Divider参数不合法");
        }
    }

    private void drawLineMargin0(Canvas canvas) {
        Paint paint = new Paint();
        // canvas的宽度和高度
        int lineWidth = getWidth();
        int lineHeight = getHeight();
        // 设置线的粗细为canvas的高度
        paint.setStrokeWidth(lineHeight);
        // 画中间的分割线
        paint.setColor(Color.BLACK);
        canvas.drawLine(0, lineHeight / 2, lineWidth, lineHeight / 2, paint);
    }

    private void drawLineMargin30(Canvas canvas) {
        Paint paint = new Paint();
        // canvas的宽度和高度
        int lineWidth = getWidth();
        int lineHeight = getHeight();
        // 设置线的粗细为canvas的高度
        paint.setStrokeWidth(lineHeight);
        // 画左端的白线，假设两端留白长度是30
        paint.setColor(Color.WHITE);
        canvas.drawLine(0, lineHeight / 2, 30, lineHeight / 2, paint);
        // 画中间的分割线
        paint.setColor(Color.BLACK);
        canvas.drawLine(30, lineHeight / 2, lineWidth - 30, lineHeight / 2, paint);
        // 画右端的白线，假设两端留白长度是30
        paint.setColor(Color.WHITE);
        canvas.drawLine(lineWidth - 30, lineHeight / 2, lineWidth, lineHeight / 2, paint);
    }
}
