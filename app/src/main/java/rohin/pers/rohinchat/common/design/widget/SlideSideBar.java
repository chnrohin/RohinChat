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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import rohin.pers.rohinchat.common.util.LogUtils;

/**
 * @author Rohin
 * @date 2018/7/18
 */
public class SlideSideBar extends View {

    /**
     * 要绘制的字符列表
     */
    public static final char[] CODES = {
            '↑',
            '☆',
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            '#'
    };

    private OnTouchCodesChangeListener mListener;

    private TextView mCodePrompt;

    private Paint mPaint = new Paint();

    private int choose = -1;

    public SlideSideBar(Context context) {
        super(context);
    }

    public SlideSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SlideSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int height = getHeight();
        final int width = getWidth();
        final int singleCodeHeight = height / CODES.length;

        mPaint.setAntiAlias(true);
        mPaint.setTextSize(singleCodeHeight - singleCodeHeight / 5);
        // 默认Text颜色
        mPaint.setColor(Color.BLACK);

        for (int i = 0; i < CODES.length; i++) {
            String text = String.valueOf(CODES[i]);
            // 控件中间(x/2) - 字符宽度
            float x = width / 2 - mPaint.measureText(text) / 2;
            // 单个字符高度 * 第(i)个，因下标从0开始，所以需要 + 默认第一个字符高度(singleCodeHeight)
            float y = singleCodeHeight * i + singleCodeHeight;
            canvas.drawText(text, x, y, mPaint);
        }

        mPaint.reset();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        final int oldChoose = choose;

        final float y = event.getY();
        final int c = (int)(y / getHeight() * CODES.length);

        final OnTouchCodesChangeListener listener = mListener;

        switch (action) {
            case MotionEvent.ACTION_UP:
                // ffffff 白色，透明度20
                setBackgroundColor(Color.parseColor("#80ffffff"));

                choose = -1;

                // 若用以显示Touch的某个字符的TextView未被指定，则重绘背景。
                // setVisibility方法会间接调用invalidate方法。
                if (mCodePrompt != null) {
                    mCodePrompt.setVisibility(INVISIBLE);
                } else {
                    invalidate();
                }
                break;
            default:
                // 999999 暗灰色，透明度20
                setBackgroundColor(Color.parseColor("#80999999"));

                if (oldChoose != c) {
                    // 验证有效值
                    if (c >= 0 && c < CODES.length) {
                        choose = c;

                        if (listener != null) {
                            // 传CODES[c]给调用此方法的对象
                            listener.onTouchCodesChange(CODES[c]);
                        }

                        // 若显示Touch某个字符的TextView未被指定，则重绘背景。
                        // setVisibility方法会间接调用invalidate方法。
                        if (mCodePrompt != null) {
                            mCodePrompt.setText(String.valueOf(CODES[c]));
                            mCodePrompt.setVisibility(VISIBLE);
                        } else {
                            invalidate();
                        }
                    }
                }
                break;
        }

        LogUtils.d("y：" + y + " | oldChoose：" + oldChoose + " | c：" + c);

        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mListener = null;
        mPaint = null;
    }

    public void setCodePrompt(TextView codePrompt) {
        mCodePrompt = codePrompt;
    }

    public void setOnTouchCodesChangeListener(OnTouchCodesChangeListener listener) {
        mListener = listener;
    }

    public interface OnTouchCodesChangeListener {
        /**
         * 传递Touch的字符给调用此方法的对象
         * @param code 当前Touch的字符
         */
        void onTouchCodesChange(char code);
    }

}
